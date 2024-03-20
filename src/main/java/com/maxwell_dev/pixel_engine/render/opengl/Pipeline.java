package com.maxwell_dev.pixel_engine.render.opengl;

import static org.lwjgl.opengl.GL46.*;

public abstract class Pipeline {
    public final int vao;
    public final int vbo;
    public final FrameBuffer framebuffer;
    public final Program program;

    /**
     * create a new pipeline
     *
     * @param program     the shader program
     * @param framebuffer the framebuffer, 0 for default
     */
    public Pipeline(Program program, FrameBuffer framebuffer) {
        this.program = program;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        this.framebuffer = framebuffer;
        init();
    }

    public Pipeline(Program program) {
        this.program = program;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        this.framebuffer = null;
        init();
    }

    public Pipeline() {
        this.program = new Program();
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        this.framebuffer = null;
        init();
    }

    public abstract void init();

    public void vertexAttribPointer(int index, int size, int type, int stride, int offset) {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(index, size, type, false, stride, offset);
        glEnableVertexAttribArray(index);
    }

    /**
     * use the pipeline
     */
    public void use() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        if (framebuffer == null) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        } else {
            framebuffer.bindAsDraw();
        }
        program.use();
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
