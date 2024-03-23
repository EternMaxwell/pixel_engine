package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.render.opengl.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class ImageDrawer extends Pipeline {

    ByteBuffer vertices;
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/test/resources/shaders/image/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/test/resources/shaders/image/shader.fsh");
        program.attach(vertex);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 4 * 4, 0);
        vertexAttribPointer(1, 2, GL_FLOAT, 4 * 4, 2 * 4);
        glNamedBufferData(vbo, 6 * 4 * 4, GL_DYNAMIC_DRAW);
    }

    public void draw(Image image){
        vertices = mapBuffer(GL_READ_WRITE);
        vertices.putFloat(-1.0f).putFloat(-1.0f).putFloat(0.0f).putFloat(0.0f);
        vertices.putFloat(1.0f).putFloat(-1.0f).putFloat(1.0f).putFloat(0.0f);
        vertices.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);
        vertices.putFloat(1.0f).putFloat(1.0f).putFloat(1.0f).putFloat(1.0f);
        vertices.putFloat(-1.0f).putFloat(1.0f).putFloat(0.0f).putFloat(1.0f);
        vertices.putFloat(-1.0f).putFloat(-1.0f).putFloat(0.0f).putFloat(0.0f);
        vertices.flip();
        unmapBuffer();
        image.bind(0);
        use();
        glDrawArrays(GL_TRIANGLES, 0, 6);
        vertices.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
        MemoryUtil.memFree(vertices);
    }
}
