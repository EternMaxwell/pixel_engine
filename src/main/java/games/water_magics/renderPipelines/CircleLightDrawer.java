package games.water_magics.renderPipelines;

import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL44.GL_DYNAMIC_STORAGE_BIT;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL45.glNamedBufferSubData;

public class CircleLightDrawer extends Pipeline {

    private int uniformBuffer;

    private ByteBuffer vertices;

    private int count = 0;

    public CircleLightDrawer() {
        super();
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/circleLight/shader.vsh");
        Shader geometry = new Shader(Shader.Type.GEOMETRY, "src/main/resources/shaders/circleLight/shader.geom");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/circleLight/shader.fsh");
        program.attach(vertex);
        program.attach(geometry);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 10 * 4, 0);
        vertexAttribPointer(1, 2, GL_FLOAT, 10 * 4, 2 * 4);
        vertexAttribPointer(2, 4, GL_FLOAT, 10 * 4, 4 * 4);
        vertexAttribPointer(3, 1, GL_FLOAT, 10 * 4, 8 * 4);
        vertexAttribPointer(4, 1, GL_FLOAT, 10 * 4, 9 * 4);
        uniformBuffer = glGenBuffers();
        glBindBuffer(GL_UNIFORM_BUFFER, uniformBuffer);
        glBufferData(GL_UNIFORM_BUFFER, 3 * 16 * 4, GL_DYNAMIC_DRAW);
        vertices = MemoryUtil.memAlloc(1024);
        glNamedBufferData(vbo, vertices.capacity(), GL_DYNAMIC_DRAW);

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

    private void vertex(float x, float y, float dirAngle, float dirConcentrate, float r, float g, float b, float a, float intensity, float brightness){
        vertices.putFloat(x);
        vertices.putFloat(y);
        vertices.putFloat(dirAngle);
        vertices.putFloat(dirConcentrate);
        vertices.putFloat(r);
        vertices.putFloat(g);
        vertices.putFloat(b);
        vertices.putFloat(a);
        vertices.putFloat(intensity);
        vertices.putFloat(brightness);
        count++;
    }

    private void flush(){
        vertices.flip();
        use();
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawArrays(GL_POINTS, 0, count);
        count = 0;
        vertices.clear();
    }

    public void drawLightMap(float x, float y, float dirAngle, float dirConcentrate, float r, float g, float b, float a, float intensity, float brightness){
        vertex(x, y, dirAngle, dirConcentrate, r, g, b, a, intensity, brightness);
        flush();
    }
}