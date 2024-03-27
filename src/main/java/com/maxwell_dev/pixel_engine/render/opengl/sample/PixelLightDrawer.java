package com.maxwell_dev.pixel_engine.render.opengl.sample;

import com.maxwell_dev.pixel_engine.render.opengl.FrameBuffer;
import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class PixelLightDrawer extends Pipeline {

    private int uniformBuffer;

    private int normalMapBuffer;

    private ByteBuffer vertices;

    private int count = 0;

    public PixelLightDrawer() {
        super();
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/light/shader.vsh");
        Shader geometry = new Shader(Shader.Type.GEOMETRY, "src/main/resources/shaders/light/shader.geom");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/light/shader.fsh");
        program.attach(vertex);
        program.attach(geometry);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 9 * 4, 0);
        vertexAttribPointer(1, 2, GL_FLOAT, 9 * 4, 2 * 4);
        vertexAttribPointer(2, 4, GL_FLOAT, 9 * 4, 4 * 4);
        vertexAttribPointer(3, 1, GL_FLOAT, 9 * 4, 8 * 4);
        uniformBuffer = glGenBuffers();
        glBindBuffer(GL_UNIFORM_BUFFER, uniformBuffer);
        glBufferData(GL_UNIFORM_BUFFER, 3 * 16 * 4, GL_DYNAMIC_DRAW);
        vertices = MemoryUtil.memAlloc(1024);
        glNamedBufferData(vbo, vertices.capacity(), GL_DYNAMIC_DRAW);

        normalMapBuffer = glCreateBuffers();
        glNamedBufferStorage(normalMapBuffer, 64 * 64 * 4 * 4, GL_DYNAMIC_STORAGE_BIT);

        uniformBuffer(0, uniformBuffer);
        shaderStorageBuffer(0, normalMapBuffer);
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

    private void vertex(float x, float y, float dirAngle, float dirConcentrate, float r, float g, float b, float a, float intensity){
        vertices.putFloat(x);
        vertices.putFloat(y);
        vertices.putFloat(dirAngle);
        vertices.putFloat(dirConcentrate);
        vertices.putFloat(r);
        vertices.putFloat(g);
        vertices.putFloat(b);
        vertices.putFloat(a);
        vertices.putFloat(intensity);
        count++;
    }

    public void setNormalMap(ByteBuffer buffer){
        glNamedBufferSubData(normalMapBuffer, 0, buffer);
    }

    private void flush(){
        vertices.flip();
        use();
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawArrays(GL_POINTS, 0, count);
        count = 0;
        vertices.clear();
    }

    public void drawLightMap(float x, float y, float dirAngle, float dirConcentrate, float r, float g, float b, float a, float intensity){
        vertex(x, y, dirAngle, dirConcentrate, r, g, b, a, intensity);
        flush();
    }
}
