package com.maxwell_dev.engine.render;

import com.maxwell_dev.globj.Buffer;
import com.maxwell_dev.globj.Context;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

//TODO change the stucture to suit the use of preload visible entity - instance rendering

/**
 * a render stream class contains a pipeline this render stream uses
 * and stores the references to the buffer data , texture , frame 
 * buffer , etc. that used in this render pass
 */
public class RenderStream extends Stream{
    private DrawPipeline pipeline;
    private Context context;
    private Buffer vertexBuffer;
    private Buffer indexBuffer;
    private final Buffer indirectBuffer;
    private ByteBuffer indirectData;
    private Buffer[] uniformBuffers;
    private Buffer[] storageBuffers;

    private int mode;
    private int vertexStride;
    private int baseIndex;
    private int baseVertex;
    private int indexBufferType;
    private int drawCount;

    /**
     * construct a new render stream
     *
     * @param pipeline the pipeline to use
     * @param context  the context to use
     * @param vertexBufferSize the size of the vertex buffer
     * @param indexBufferSize the size of the index buffer
     * @param indirectBufferSize the size of the indirect buffer
     */
    public RenderStream(DrawPipeline pipeline, Context context, long vertexBufferSize, long indexBufferSize, long indirectBufferSize){
        vertexBuffer = new Buffer();
        vertexBuffer.bufferData().load(vertexBufferSize, GL_DYNAMIC_DRAW);
        indexBuffer = new Buffer();
        indexBuffer.bufferData().load(indexBufferSize, GL_DYNAMIC_DRAW);
        indirectBuffer = new Buffer();
        indirectBuffer.bufferData().load(indirectBufferSize, GL_DYNAMIC_DRAW);
        this.pipeline = pipeline;
        this.context = context;
    }

    public void addEntity(VisibleEntity entity){
        ByteBuffer entityVertexBuffer = entity.vertexBuffer();
        ByteBuffer entityIndexBuffer = entity.indexBuffer();
        indirectData.putInt(entityIndexBuffer.limit());
        indirectData.putInt(1);
        indirectData.putInt(baseIndex);
        indirectData.putInt(baseVertex);
        indirectData.putInt(0);
        vertexBuffer.mapBuffer().range(baseVertex * vertexStride, entityVertexBuffer.limit(), GL_WRITE_ONLY)
                .put(entityVertexBuffer);
        int indexBytes;
        if(indexBufferType == GL_INT)
            indexBytes = 4;
        else if(indexBufferType == GL_SHORT)
            indexBytes = 2;
        else
            indexBytes = 1;
        indexBuffer.mapBuffer().range(baseIndex * indexBytes, entityIndexBuffer.limit(), GL_WRITE_ONLY)
                .put(entityIndexBuffer);
        baseIndex+=entityIndexBuffer.limit()/indexBytes;
        baseVertex+=entityVertexBuffer.limit()/vertexStride;
        drawCount++;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    
    public void setIndexBufferType(int indexBufferType) {
        this.indexBufferType = indexBufferType;
    }
    
    public int getMode() {
        return mode;
    }
    
    public int getIndexBufferType() {
        return indexBufferType;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * set the uniform buffer used in this render pass
     *
     * @param uniformBufferList the uniform buffers,better to be set already
     */
    public void setUniformBuffer(Buffer[] uniformBufferList) {
        uniformBuffers = uniformBufferList;
    }

    /**
     * get the uniform buffers used in the render stream
     *
     * @return the uniform buffers used
     */
    public Buffer[] getUniformBuffers() {
        return uniformBuffers;
    }

    /**
     * set the storage buffer used in the render pass
     *
     * @param storageBufferList the storage buffers, better to be set already
     */
    public void setStorageBuffers(Buffer[] storageBufferList) {
        storageBuffers = storageBufferList;
    }

    /**
     * get the shader storage buffers used in this render stream
     * @return the shader storage buffers used
     */
    public Buffer[] getStorageBuffers() {
        return storageBuffers;
    }

    /**
     * set the pipeline this render stream uses
     *
     * @param pipeline the pipeline to use
     */
    public void setPipeline(DrawPipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * get the pipeline this render stream used
     *
     * @return the pipeline used
     */
    public DrawPipeline getPipeline() {
        return pipeline;
    }

    /**
     * set the vertex buffer this render stream used
     *
     * @param vertexBuffer the vertex buffer to use
     */
    public void setVertexBuffer(Buffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    /**
     * get the vertex buffer the render stream used
     *
     * @return the vertex buffer used
     */
    public Buffer getVertexBuffer() {
        return vertexBuffer;
    }

    /**
     * set the index buffer this render stream used
     *
     * @param indexBuffer the index buffer to use
     */
    public void setIndexBuffer(Buffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    /**
     * get the index buffer the render stream used
     *
     * @return the index buffer used
     */
    public Buffer getIndexBuffer() {
        return indexBuffer;
    }

    /**
     * use this render stream
     * deploy the setting to the opengl context
     */
    public void useRenderStream() {
        context.setContextCurrent();
        pipeline.usePipeline();

        context.arrayBuffer().bind(vertexBuffer);
        context.elementArrayBuffer().bind(indexBuffer);
        context.drawIndirectBuffer().bind(indirectBuffer);

        if (uniformBuffers != null)
            for (int index = 0; index < uniformBuffers.length; index++)
                context.uniformBuffer(index).bind(uniformBuffers[index]);
        if (storageBuffers != null)
            for (int index = 0; index < storageBuffers.length; index++)
                context.shaderStorageBuffer(index).bind(storageBuffers[index]);
    }

    public void pass(){
        useRenderStream();
        draw();
    }

    public void draw(){
        indirectData.flip();
        indirectBuffer.mapBuffer().unmap();
        context.multiDrawElementsIndirect(mode, indexBufferType, 0, drawCount, 0);
        indirectBuffer.clearData().clear(GL_R32UI, GL_RED_INTEGER, GL_UNSIGNED_INT, (ByteBuffer) null);
        indirectData = indirectBuffer.mapBuffer().map(GL_WRITE_ONLY, indirectData);
        baseIndex=0;
        baseVertex=0;
        drawCount=0;
    }
}