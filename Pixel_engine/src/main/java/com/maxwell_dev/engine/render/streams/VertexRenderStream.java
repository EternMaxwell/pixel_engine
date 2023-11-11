package com.maxwell_dev.engine.render.streams;

import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.globj.Buffer;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Sampler;
import com.maxwell_dev.globj.Texture;
import com.maxwell_dev.engine.render.DrawPipeline;

import static org.lwjgl.opengl.GL46.*;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.maxwell_dev.engine.render.Stream;

public class VertexRenderStream extends RenderStream {
    private int mode;
    private int vertexCount;
    private Buffer vertexBuffer;

    private Context context;
    private DrawPipeline pipeline;

    private Buffer uniformBuffer;
    private Buffer storageBuffer;
    private Buffer[] uniformBuffers;
    private Buffer[] storageBuffers;

    private List<Integer> freeIndices;
    private int maxEntityIndex;
    private int limit;

    private List<TextureUnit> textureUnits;

    public Buffer vertexBuffer() {
        return vertexBuffer;
    }

    public static class TextureUnit {
        private Texture texture;
        private Sampler sampler;

        public TextureUnit() {
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public void setSampler(Sampler sampler) {
            this.sampler = sampler;
        }

        public void set(Texture texture, Sampler sampler){
            this.texture = texture;
            this.sampler = sampler;
        }

        public Texture getTexture() {
            return texture;
        }

        public Sampler getSampler() {
            return sampler;
        }
    }

    /**
     * create a new vertex render stream
     * @param pipeline the pipeline to use
     * @param instance the construction takes an any instance to initialize the stream
     * @param limit the maximum number of entities to draw
     * @param vertexCountMax the maximum number of vertices to draw
     */
    public VertexRenderStream(DrawPipeline pipeline, Visible instance, int limit, long vertexCountMax) {
        this.pipeline = pipeline;
        this.context = pipeline.context();
        this.limit = limit;
        this.mode = instance.mode();
        this.vertexCount = 0;
        this.vertexBuffer = new Buffer();
        vertexBuffer.bufferData().load(vertexCountMax * instance.vertexStride(), GL_DYNAMIC_DRAW);
        if (instance.uniformBufferBytes() > 0) {
            this.uniformBuffer = new Buffer();
            uniformBuffer.bufferData().load(limit * instance.uniformBufferBytes(), GL_DYNAMIC_DRAW);
        }
        if (instance.storageBufferBytes() > 0) {
            this.storageBuffer = new Buffer();
            storageBuffer.bufferData().load(limit * instance.storageBufferBytes(), GL_DYNAMIC_DRAW);
        }
        if (instance.uniformBuffersBytes() != null) {
            this.uniformBuffers = new Buffer[instance.uniformBuffersBytes().length];
            for (int i = 0; i < uniformBuffers.length; i++) {
                if (instance.uniformBuffersBytes()[i] == 0) {
                    uniformBuffers[i] = null;
                    continue;
                }
                uniformBuffers[i] = new Buffer();
                uniformBuffers[i].bufferData().load(limit * instance.uniformBuffersBytes()[i], GL_DYNAMIC_DRAW);
            }
        }
        if (instance.storageBuffersBytes() != null) {
            this.storageBuffers = new Buffer[instance.storageBuffersBytes().length];
            for (int i = 0; i < storageBuffers.length; i++) {
                if (instance.storageBuffersBytes()[i] == 0) {
                    storageBuffers[i] = null;
                    continue;
                }
                storageBuffers[i] = new Buffer();
                storageBuffers[i].bufferData().load(limit * instance.storageBuffersBytes()[i], GL_DYNAMIC_DRAW);
            }
        }
        textureUnits = new LinkedList<>();
        freeIndices = new LinkedList<>();
    }

    /**
     * load the entity data to the stream buffer
     * @param entity the entity to load
     * @return true if the entity is loaded successfully
     */
    public boolean loadEntity(Visible entity) {
        boolean loaded = true;
        if (entity.indexInStream() <= 0) {
            loaded = false;
            if (!freeIndices.isEmpty()) {
                int index = freeIndices.get(0);
                freeIndices.remove(0);
                entity.indexInStream(index);
            } else {
                entity.indexInStream(maxEntityIndex);
                maxEntityIndex++;
            }
        }
        int index = entity.indexInStream();
        if (index>=limit) {
            return false;
        }
        if (entity.differUniformBuffer() && (entity.uniformBufferChanged() || !loaded) && uniformBuffer != null) {
            uniformBuffer.mapBuffer()
                    .range(index * entity.uniformBufferBytes(), entity.uniformBufferBytes(), GL_WRITE_ONLY)
                    .put(entity.uniformBuffer());
            uniformBuffer.mapBuffer().unmap();
        }
        if (entity.differStorageBuffer() && (entity.storageBufferChanged() || !loaded) && storageBuffer != null) {
            storageBuffer.mapBuffer()
                    .range(index * entity.storageBufferBytes(), entity.storageBufferBytes(), GL_WRITE_ONLY)
                    .put(entity.shaderStorageBuffer());
            storageBuffer.mapBuffer().unmap();
        }
        if (entity.differUniformBuffers() && uniformBuffers != null) {
            for (int i = 0; i < entity.uniformBuffersBytes().length; i++) {
                if ((entity.uniformBufferChanged(i) || !loaded) && uniformBuffers[i] != null) {
                    uniformBuffers[i].mapBuffer()
                            .range(index * entity.uniformBuffersBytes()[i], entity.uniformBuffersBytes()[i],
                                    GL_WRITE_ONLY)
                            .put(entity.uniformBuffers()[i]);
                    uniformBuffers[i].mapBuffer().unmap();
                }
            }
        }
        if (entity.differStorageBuffers() && storageBuffers != null) {
            for (int i = 0; i < entity.storageBuffersBytes().length; i++) {
                if ((entity.storageBufferChanged(i) || !loaded) && storageBuffers[i] != null) {
                    storageBuffers[i].mapBuffer()
                            .range(index * entity.storageBuffersBytes()[i], entity.storageBuffersBytes()[i],
                                    GL_WRITE_ONLY)
                            .put(entity.shaderStorageBuffers()[i]);
                    storageBuffers[i].mapBuffer().unmap();
                }
            }
        }
        return true;
    }

    /**
     * remove the entity from the stream buffer
     * @param entity the entity to remove
     */
    public void removeEntity(Visible entity) {
        if(entity.indexInStream() < 0)
            return;
        freeIndices.add(entity.indexInStream());
        entity.indexInStream(-1);
    }

    /**
     * use the stream
     * <p>e.g. set the bindings to the opengl context</p>
     */
    public void useStream() {
        context.setContextCurrent();

        pipeline.usePipeline();

        context.arrayBuffer().bind(vertexBuffer);

        if (textureUnits != null)
            for (int index = 0; index < textureUnits.size(); index++) {
                TextureUnit textureUnit = textureUnits.get(index);
                if (textureUnit != null) {
                    if (textureUnit.getTexture() != null)
                        context.textureUnit(index).bindTexture(textureUnit.getTexture());
                    if (textureUnit.getSampler() != null)
                        context.textureUnit(index).bindSampler(textureUnit.getSampler());
                }
            }

        if (uniformBuffer != null)
            context.uniformBuffer(0).bind(uniformBuffer);
        if (storageBuffer != null)
            context.shaderStorageBuffer(0).bind(storageBuffer);
        if (uniformBuffers != null)
            for (int index = 0; index < uniformBuffers.length; index++)
                context.uniformBuffer(index).bind(uniformBuffers[index]);
        if (storageBuffers != null)
            for (int index = 0; index < storageBuffers.length; index++)
                context.shaderStorageBuffer(index).bind(storageBuffers[index]);
    }

    /**
     * add the entity to the draw command list
     * @param entity the entity to draw
     */
    public boolean drawEntity(Visible entity) {
        int index = entity.indexInStream();
        if (index < 0) {
            return false;
        }
        ByteBuffer vertexData = entity.vertexBuffer();
        vertexBuffer.mapBuffer()
                .range(vertexCount * entity.vertexStride(), vertexData.limit(), GL_MAP_WRITE_BIT)
                .put(vertexData);
        vertexData.flip();
        vertexBuffer.mapBuffer().unmap();
        vertexCount += entity.vertexCount();
        return true;
    }

    /**
     * get the texture unit at the specified index
     * @param index the index of the texture unit
     * @return the texture unit at the specified index
     */
    public TextureUnit textureUnit(int index){
        if(index >= textureUnits.size()){
            for(int i = textureUnits.size(); i <= index; i++){
                textureUnits.add(null);
            }
            textureUnits.add(index, new TextureUnit());
        }
        return textureUnits.get(index);
    }

    /**
     * get the draw pipeline
     * @return the draw pipeline
     */
    public DrawPipeline pipeline() {
        return pipeline;
    }

    /**
     * pass the stream
     * <p>e.g. draw the stream</p>
     */
    public void pass() {
        useStream();
        glDrawArrays(mode, 0, vertexCount);
        vertexCount = 0;
    }
}
    