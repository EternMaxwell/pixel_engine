package com.maxwell_dev.pixel_engine.render.opengl.sample;

import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class LineDrawer extends Pipeline {

    private int uniformBuffer;
    private ByteBuffer vertices;
    private int count = 0;

    public LineDrawer() {
        super();
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/line/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/line/shader.fsh");
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
        uniformBuffer(0, uniformBuffer);
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

    public void draw(float x1, float y1, float x2, float y2, float r, float g, float b, float a){
        if(vertices.remaining() < 6 * 4 * 2)
            flush();
        vertex(x1, y1, r, g, b, a);
        vertex(x2, y2, r, g, b, a);
        count += 2;
    }

    private void vertex(float x, float y, float r, float g, float b, float a){
        vertices.putFloat(x).putFloat(y).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
    }

    public void flush(){
        vertices.flip();
        use();
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawArrays(GL_LINES, 0, count);
        vertices.clear();
        count = 0;
    }
}
