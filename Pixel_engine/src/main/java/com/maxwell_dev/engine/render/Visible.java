package com.maxwell_dev.engine.render;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public interface Visible{

    /**
     * @return The mode to render in
     */
    public default int mode() {
        return GL_TRIANGLES;
    }

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
    public long vertexStride();

    /**
     * @return The vertex count
     */
    public int vertexCount();

    /**
     * get the render stream this visible object uses
     * @return the render stream
     */
    public RenderStream renderStream();

    /**
     * get the pipeline this visible object uses
     * @return the pipeline
     */
    public DrawPipeline pipeline();

    /**
     * get if the storage buffer is different in different instances of this visible
     * @return if the storage buffer is different
     */
    public default boolean differStorageBuffer() {
        return false;
    }

    /**
     * get if the storage buffer has changed since last load
     * @return if the storage buffer is changed
     */
    public default boolean storageBufferChanged(){
        return true;
    }

    /**
     * get if the storage buffer has changed since last load
     * @param index the index of the storage buffer
     * @return if the storage buffer is changed
     */
    public default boolean storageBufferChanged(int index){
        return true;
    }

    /**
     * get if the storage buffers are different in different instances of this visible
     * @return if the storage buffers are different
     */
    public default boolean differStorageBuffers() {
        return false;
    }

    /**
     * get the storage buffer bytes, the length of the storage buffer per instance of this visible uses
     * @return the storage buffer bytes
     */
    public default long storageBufferBytes() {
        return 0;
    }

    /**
     * get the storage buffers bytes, the lengths of the storage buffers per instance of this visible uses
     * @return the storage buffers bytes
     */
    public default long[] storageBuffersBytes() {
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
     * get if the uniform buffers are different in different instances of this visible
     * @return if the uniform buffers are different
     */
    public default boolean differUniformBuffers() {
        return false;
    }

    /**
     * get if the uniform buffer has changed since last load
     * @return if the uniform buffer is changed
     */
    public default boolean uniformBufferChanged(){
        return true;
    }

    /**
     * get if the uniform buffer has changed since last load
     * @param index the index of the uniform buffer
     * @return if the uniform buffer is changed
     */
    public default boolean uniformBufferChanged(int index){
        return true;
    }

    /**
     * get the uniform buffer bytes, the length of the uniform buffer per instance of this visible uses
     * @return the uniform buffer bytes
     */
    public default long uniformBufferBytes() {
        return 0;
    }

    /**
     * get the uniform buffers bytes, the lengths of the uniform buffers per instance of this visible uses
     * @return the uniform buffers bytes
     */
    public default long[] uniformBuffersBytes() {
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

    /**
     * set the index of this instance in the render stream
     * @param index the index of this instance in the render stream
     */
    public void indexInStream(int index);

    /**
     * dispose of this visible
     */
    public void dispose();
}
