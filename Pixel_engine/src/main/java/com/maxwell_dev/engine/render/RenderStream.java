package com.maxwell_dev.engine.render;

import com.maxwell_dev.globj.Buffer;
import com.maxwell_dev.globj.Context;

/**
 * a render stream class contains a pipeline this render stream uses
 * and stores the references to the buffer data , texture , frame 
 * buffer , etc. that used in this render pass
 */
public class RenderStream {
    private DrawPipeline pipeline;
    private Context context;
    private Buffer vertexBuffer;
    private Buffer indexBuffer;
    private Buffer[] uniformBuffers;
    private Buffer[] storageBuffers;

    /**
     * create a new render stream
     */
    public RenderStream() {
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

        if (uniformBuffers != null)
            for (int index = 0; index < uniformBuffers.length; index++)
                context.uniformBuffer(index).bind(uniformBuffers[index]);
        if (storageBuffers != null)
            for (int index = 0; index < storageBuffers.length; index++)
                context.shaderStorageBuffer(index).bind(storageBuffers[index]);
    }
}