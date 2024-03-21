import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class LineDrawer extends Pipeline {
    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/test/resources/shaders/line/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/test/resources/shaders/line/shader.fsh");
        program.attach(vertex);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 6 * 4, 0);
        vertexAttribPointer(1, 4, GL_FLOAT, 6 * 4, 2 * 4);
        glNamedBufferData(vbo, 2 * 6 * 4, GL_DYNAMIC_DRAW);
    }

    public void draw(float x1, float y1, float x2, float y2, float r, float g, float b, float a){
        ByteBuffer vertices = mapBuffer(GL_READ_WRITE);
        vertices.putFloat(x1).putFloat(y1).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.putFloat(x2).putFloat(y2).putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        vertices.flip();
        unmapBuffer();
        use();
        glDrawArrays(GL_LINES, 0, 2);
        vertices.clear();
    }
}
