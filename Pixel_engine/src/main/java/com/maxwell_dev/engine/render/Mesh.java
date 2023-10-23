package com.maxwell_dev.engine.render;

import com.maxwell_dev.globj.Buffer;

public class Mesh {
    private java.nio.ByteBuffer vertexBufferData;
    private java.nio.ByteBuffer indexBufferData;
    private Buffer vertexBuffer;
    private Buffer indexBuffer;

    /**
     * create a new empty mesh
     */
    public Mesh() {
    }

    /**
     * set the index buffer that is used for opengl. The buffer should have been initialized.
     *
     * @param indexBuffer the index buffer
     */
    public void setIndexBuffer(Buffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    /**
     * set the vertex buffer that is used for opengl. The buffer should have been initialized.
     *
     * @param vertexBuffer the vertex buffer
     */
    public void setVertexBuffer(Buffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    /**
     * set the vertex data buffer
     *
     * @param buffer the vertex data buffer
     */
    public void setVertexDataBuffer(java.nio.ByteBuffer buffer) {
        vertexBufferData = buffer;
    }

    /**
     * set the index data buffer
     *
     * @param buffer the index data buffer
     */
    public void setIndexDataBuffer(java.nio.ByteBuffer buffer) {
        indexBufferData = buffer;
    }

    /**
     * load the data from data buffer to buffer
     */
    public void loadData() {
        vertexBuffer.bufferSubData().load(0, vertexBufferData);
        indexBuffer.bufferSubData().load(0, indexBufferData);
    }
}