package com.maxwell_dev.pixel_engine.render.opengl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public abstract class Pipeline {

    /**
     * the vertex array object
     */
    public final int vao;

    /**
     * the vertex buffer object
     */
    public final int vbo;

    /**
     * the element buffer object
     */
    public final int ebo = 0;

    /**
     * the uniform buffers
     */
    public final Map<Integer,Integer> uniformBuffers = new HashMap<>();

    /**
     * the shader storage buffers
     */
    public final Map<Integer,Integer> shaderStorageBuffers = new HashMap<>();

    /**
     * the shader program
     */
    public final Program program;

    /**
     * create a new pipeline
     *
     * @param program the shader program
     * @param vboSize the size of the vertex buffer object
     */
    public Pipeline(Program program, long vboSize) {
        this.program = program;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glNamedBufferStorage(vbo, vboSize, GL_DYNAMIC_STORAGE_BIT);
        init();
    }

    /**
     * create a new pipeline
     *
     * @param program the shader program
     */
    public Pipeline(Program program) {
        this.program = program;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        init();
    }

    /**
     * create a new pipeline
     *
     * @param vboSize     the size of the vertex buffer object
     */
    public Pipeline(long vboSize) {
        this.program = new Program();
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glNamedBufferStorage(vbo, vboSize, GL_DYNAMIC_STORAGE_BIT);
        init();
    }

    /**
     * create a new pipeline
     */
    public Pipeline() {
        this.program = new Program();
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        init();
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    public abstract void init();

    /**
     * set the vertex attribute pointer
     *
     * @param index the index of the vertex attribute
     * @param size the size of this attribute in bytes
     * @param type the type of this attribute
     * @param stride the stride of this attribute in bytes
     * @param offset the offset of this attribute in bytes
     */
    public void vertexAttribPointer(int index, int size, int type, int stride, int offset) {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(index, size, type, false, stride, offset);
        glEnableVertexAttribArray(index);
    }

    /**
     * map the vbo's data store
     *
     * @param access the access policy, indicating whether it will be possible to read from, write to, or both read from and write to the buffer object's mapped data store. One of:<br><table><tr><td>{@link GL15#GL_READ_ONLY READ_ONLY}</td><td>{@link GL15#GL_WRITE_ONLY WRITE_ONLY}</td><td>{@link GL15#GL_READ_WRITE READ_WRITE}</td></tr></table>
     * @return the mapped buffer
     */
    public ByteBuffer mapBuffer(int access) {
        return glMapNamedBuffer(vbo, access);
    }

    /**
     * map the vbo's data store
     *
     * @param offset the starting offset within the buffer of the range to be mapped
     * @param length the length of the range to be mapped
     * @param access a combination of access flags indicating the desired access to the range. One or more of:<br><table><tr><td>{@link GL30#GL_MAP_READ_BIT MAP_READ_BIT}</td><td>{@link GL30#GL_MAP_WRITE_BIT MAP_WRITE_BIT}</td><td>{@link GL30#GL_MAP_INVALIDATE_RANGE_BIT MAP_INVALIDATE_RANGE_BIT}</td><td>{@link GL30#GL_MAP_INVALIDATE_BUFFER_BIT MAP_INVALIDATE_BUFFER_BIT}</td></tr><tr><td>{@link GL30#GL_MAP_FLUSH_EXPLICIT_BIT MAP_FLUSH_EXPLICIT_BIT}</td><td>{@link GL30#GL_MAP_UNSYNCHRONIZED_BIT MAP_UNSYNCHRONIZED_BIT}</td></tr></table>
     *
     * @return the mapped buffer
     */
    public ByteBuffer mapBuffer(int offset, int length, int access) {
        return glMapNamedBufferRange(vbo, offset, length, access);
    }

    /**
     * unmap the vbo's data store
     */
    public void unmapBuffer() {
        glUnmapNamedBuffer(vbo);
    }

    /**
     * use the pipeline
     */
    public void use() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        for(Map.Entry<Integer,Integer> entry : uniformBuffers.entrySet())
            glBindBufferBase(GL_UNIFORM_BUFFER, entry.getKey(), entry.getValue());
        for(Map.Entry<Integer,Integer> entry : shaderStorageBuffers.entrySet())
            glBindBufferBase(GL_SHADER_STORAGE_BUFFER, entry.getKey(), entry.getValue());
        program.use();
    }

    /**
     * set the uniform buffer this pipeline uses
     * @param index the index of the uniform buffer
     * @param buffer the buffer
     */
    public void uniformBuffer(int index, int buffer){
        uniformBuffers.put(index, buffer);
    }

    /**
     * remove the uniform buffer at index this pipeline don't use
     * @param index the index of the uniform buffer
     */
    public void noUniformBuffer(int index){
        uniformBuffers.remove(index);
    }

    /**
     * set the shader storage buffer at index this pipeline uses
     * @param index the index of the shader storage buffer
     * @param buffer the buffer
     */
    public void shaderStorageBuffer(int index, int buffer){
        shaderStorageBuffers.put(index, buffer);
    }

    /**
     * remove the shader storage buffer this pipeline don't use
     * @param index the index of the shader storage buffer
     */
    public void noShaderStorageBuffer(int index){
        shaderStorageBuffers.remove(index);
    }

    /**
     * dispose of the pipeline
     */
    public void dispose() {
        program.dispose();
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }
}
