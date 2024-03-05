package com.maxwell_dev.pixel_engine.render.opengl;

import static org.lwjgl.opengl.GL46.*;

public class Pipeline {
    public final int vao;
    public final int vbo;
    public final int framebuffer;
    public final ShaderProgram program;

    /**
     * create a new pipeline
     * @param program the shader program
     * @param framebuffer the framebuffer, 0 for default
     */
    public Pipeline(ShaderProgram program, int framebuffer){
        this.program = program;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        this.framebuffer = framebuffer;
    }

    /**
     * use the pipeline
     */
    public void use(){
        program.use();
    }

    /**
     * dispose of the pipeline
     */
    public void dispose(){
        program.dispose();
    }
}
