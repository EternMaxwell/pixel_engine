package com.maxwell_dev.pixel_engine.render.opengl.sample;

import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class PixelDrawer extends Pipeline {

    private int uniformBuffer;
    private ByteBuffer vertices;
    private int count = 0;

    public PixelDrawer() {
        super();
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/pixel/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/pixel/shader.fsh");
        program.attach(vertex);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 6 * 4, 0);
        vertexAttribPointer(1, 4, GL_FLOAT, 6 * 4, 2 * 4);
        uniformBuffer = glGenBuffers();
        glBindBuffer(GL_UNIFORM_BUFFER, uniformBuffer);
        glBufferData(GL_UNIFORM_BUFFER, 16 * 4 * 3, GL_DYNAMIC_DRAW);
        vertices = MemoryUtil.memAlloc(1024);
        glNamedBufferData(vbo, 1024, GL_DYNAMIC_DRAW);
    }

    public void setProjection(Matrix4f projection){
        ByteBuffer buffer = MemoryUtil.memAlloc(16 * 4);
        projection.get(buffer);
        glNamedBufferSubData(uniformBuffer, 0, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void setView(Matrix4f view){
        ByteBuffer buffer = MemoryUtil.memAlloc(16 * 4);
        view.get(buffer);
        glNamedBufferSubData(uniformBuffer, 16 * 4, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void setModel(Matrix4f model){
        ByteBuffer buffer = MemoryUtil.memAlloc(16 * 4);
        model.get(buffer);
        glNamedBufferSubData(uniformBuffer, 16 * 4 * 2, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void draw(float x, float y, float size, float r, float g, float b, float a){
        if(vertices.remaining() < 6 * 4 * 6)
            flush();
        vertex(x - size / 2, y - size / 2, r, g, b, a);
        vertex(x + size / 2, y - size / 2, r, g, b, a);
        vertex(x + size / 2, y + size / 2, r, g, b, a);
        vertex(x + size / 2, y + size / 2, r, g, b, a);
        vertex(x - size / 2, y + size / 2, r, g, b, a);
        vertex(x - size / 2, y - size / 2, r, g, b, a);
    }

    public void vertex(float x, float y, float r, float g, float b, float a){
        vertices.putFloat(x).putFloat(y).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        count += 1;
    }

    public void flush(){
        vertices.flip();
        use();
        glNamedBufferSubData(vbo, 0, vertices);
        glBindBufferBase(GL_UNIFORM_BUFFER, 0, uniformBuffer);
        glDrawArrays(GL_TRIANGLES, 0, count);
        vertices.clear();
        count = 0;
    }
}
