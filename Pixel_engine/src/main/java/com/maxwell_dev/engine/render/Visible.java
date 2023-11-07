package com.maxwell_dev.engine.render;

import java.nio.ByteBuffer;

public interface Visible{

    /**
     * @return The index buffer
     */
    public default ByteBuffer indexBuffer() {
        return null;
    }

    /**
     * @return The index buffer type
     */
    public default int indexBufferType(){
        return 0;
    }

    /**
     * @return The index buffer bytes
     */
    public default int indexBufferBytes() {
        return 0;
    }

    /**
     * @return The vertex buffer
     */
    public ByteBuffer vertexBuffer();

    /**
     * @return The vertex stride in bytes
     */
    public int vertexStride();

    /**
     * get the render stream this visible object uses
     * @return the render stream
     */
    public RenderStream renderStream();

    /**
     * get if the storage buffer is different in different instances of this visible
     * @return if the storage buffer is different
     */
    public default boolean differStorageBuffer() {
        return false;
    }

    /**
     * get the storage buffer bytes, the length of the storage buffer per instance of this visible uses
     * @return the storage buffer bytes
     */
    public default int storageBufferBytes() {
        return 0;
    }

    /**
     * get the storage buffers bytes, the lengths of the storage buffers per instance of this visible uses
     * @return the storage buffers bytes
     */
    public default int[] storageBuffersBytes() {
        return null;
    }

    /**
     * get the storage buffer
     * @return the storage buffer
     */
    public default ByteBuffer shaderStorageBuffer() {
        return null;
    }

    /**
     * get the storage buffers
     * @return the storage buffers
     */
    public default ByteBuffer[] shaderStorageBuffers() {
        return null;
    }

    /**
     * get if the uniform buffer is different in different instances of this visible
     * @return if the uniform buffer is different
     */
    public default boolean differUniformBuffer() {
        return false;
    }

    /**
     * get the uniform buffer bytes, the length of the uniform buffer per instance of this visible uses
     * @return the uniform buffer bytes
     */
    public default int uniformBufferBytes() {
        return 0;
    }

    /**
     * get the uniform buffers bytes, the lengths of the uniform buffers per instance of this visible uses
     * @return the uniform buffers bytes
     */
    public default int[] uniformBuffersBytes() {
        return null;
    }

    /**
     * get the uniform buffer
     * @return the uniform buffer
     */
    public default ByteBuffer uniformBuffer() {
        return null;
    }

    /**
     * get the uniform buffers
     * @return the uniform buffers
     */ 
    public default ByteBuffer[] uniformBuffers() {
        return null;
    }

    /**
     * get the index of this instance in the render stream
     * @return the index of this instance in the render stream
     */
    public int indexInStream();
}
