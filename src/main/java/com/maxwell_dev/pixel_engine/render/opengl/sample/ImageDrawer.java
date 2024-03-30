package com.maxwell_dev.pixel_engine.render.opengl.sample;

import com.maxwell_dev.pixel_engine.render.opengl.*;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class ImageDrawer extends Pipeline {

    ByteBuffer vertices;
    int uniformBuffer;

    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/image/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/image/shader.fsh");
        program.attach(vertex);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 8 * 4, 0);
        vertexAttribPointer(1, 2, GL_FLOAT, 8 * 4, 2 * 4);
        vertexAttribPointer(2, 4, GL_FLOAT, 8 * 4, 4 * 4);

        glNamedBufferStorage(vbo, 48 * 4 * 4, GL_DYNAMIC_STORAGE_BIT);
        vertices = MemoryUtil.memAlloc(48 * 4 * 4);

        uniformBuffer = glCreateBuffers();
        glNamedBufferData(uniformBuffer, 16 * 4 * 3, GL_DYNAMIC_DRAW);
        uniformBuffer(0, uniformBuffer);
    }

    public void setProjection(Matrix4f projection){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        projection.get(buffer);
        glNamedBufferSubData(uniformBuffer, 0, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void setView(Matrix4f view){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        view.get(buffer);
        glNamedBufferSubData(uniformBuffer, 16 * 4, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void setModel(Matrix4f model){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        model.get(buffer);
        glNamedBufferSubData(uniformBuffer, 16 * 4 * 2, buffer);
        MemoryUtil.memFree(buffer);
    }

    public void draw(Image image, float x, float y, float width, float height, float u, float v, float u2, float v2,
                     float r, float g, float b, float a){
        image.bind(0);
        vertices.putFloat(x).putFloat(y).putFloat(u).putFloat(v).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x + width).putFloat(y).putFloat(u2).putFloat(v).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x + width).putFloat(y + height).putFloat(u2).putFloat(v2).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x).putFloat(y).putFloat(u).putFloat(v).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x + width).putFloat(y + height).putFloat(u2).putFloat(v2).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x).putFloat(y + height).putFloat(u).putFloat(v2).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.flip();
        glNamedBufferSubData(vbo, 0, vertices);
        use();
        glDrawArrays(GL_TRIANGLES, 0, 6);
        vertices.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
        vertices.clear();
    }
}
