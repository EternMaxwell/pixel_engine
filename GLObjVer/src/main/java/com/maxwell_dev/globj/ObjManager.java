package com.maxwell_dev.globj;

import com.maxwell_dev.globj.texture.*;

/**
 * this class is created to make the obj generation more easy
 * and quick
 */
public class ObjManager {
    /**
     * construct a shader.
     *
     * @param type the shader type to create
     */
    public Shader createShader(int type) {
        return new Shader(type);
    }

    /**
     * create a shader from the source file
     *
     * @param type the shader type
     * @param path the source file path
     */
    public Shader createShader(int type, String path) {
        return new Shader(type, path);
    }

    /**
     * create a new program
     *
     * @return the created program
     */
    public Program createProgram() {
        return new Program();
    }

    /**
     * create a new vertex array
     *
     * @return the created vertex array
     */
    public VertexArray createVertexArray() {
        return new VertexArray();
    }

    /**
     * create a new buffer object
     *
     * @return the created buffer obj
     */
    public Buffer createBuffer() {
        return new Buffer();
    }

    /**
     * create a new frame buffer obj
     * @return the created frame buffer obj
     */
    public Framebuffer createFramebuffer() {
        return new Framebuffer();
    }

    /**
     * create a new texture obj
     * @param target the texture type or where it is going to be bound 
     * @return the created texture obj
     */
    public Texture createTexture(int target) {
        return new Texture(target);
    }

    /**
     * create a new Texture_1D obj
     * @return the created Texture_1D obj
     */
    public Texture_1D createTexture_1D() {
        return new Texture_1D();

    }

    /**
     * create a new Texture_1D_Array obj
     * @return the created Texture_1D_Array obj
     */
    public Texture_1D_Array createTexture_1D_Array() {
        return new Texture_1D_Array();
    }

    /**
     * created a new Texture_2D obj
     * @return the created Texture_2D obj
     */
    public Texture_2D createTexture_2D() {
        return new Texture_2D();
    }

    /**
     * create a new Texture_2D_Array obj
     * @return the created Texture_2D_Array obj
     */
    public Texture_2D_Array createTexture_2D_Array() {
        return new Texture_2D_Array();
    }

    /**
     * create a new Texture_2D_Multisample obj
     * @return the created Texture_2D_Multisample obj
     */
    public Texture_2D_Multisample createTexture_2D_Multisample() {
        return new Texture_2D_Multisample();
    }

    /**
     * create a new Texture_2D_Multisample_Array obj
     * @return the created Texture_2D_Multisample_Array obj
     */
    public Texture_2D_Multisample_Array createTexture_2D_Multisample_Array() {
        return new Texture_2D_Multisample_Array();
    }

    /**
     * create a new Texture_3D obj
     * @return the created Texture_3D obj
     */
    public Texture_3D createTexture_3D() {
        return new Texture_3D();
    }

    /**
     * create a new Texture_Cube_Map obj
     * @return the created Texture_Cube_Map obj
     */
    public Texture_Cube_Map createTexture_Cube_Map() {
        return new Texture_Cube_Map();
    }

    /**
     * create a new Texture_Cube_Map_Array obj
     * @return the created Texture_Cube_Map_Array obj
     */
    public Texture_Cube_Map_Array createTexture_Cube_Map_Array() {
        return new Texture_Cube_Map_Array();
    }

    /**
     * create a new Texture_Rectangle obj
     * @return the created Texture_Rectangle obj
     */
    public Texture_Rectangle createTexture_Rectangle() {
        return new Texture_Rectangle();
    }
}
