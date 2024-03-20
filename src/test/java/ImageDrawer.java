import com.maxwell_dev.pixel_engine.render.opengl.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class ImageDrawer extends Pipeline {

    FloatBuffer vertices;
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/test/resources/shaders/image/shader.vsh");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/test/resources/shaders/image/shader.fsh");
        program.attach(vertex);
        program.attach(fragment);
        program.link();
        vertexAttribPointer(0, 2, GL_FLOAT, 4 * 4, 0);
        vertexAttribPointer(1, 2, GL_FLOAT, 4 * 4, 2 * 4);
        vertices = MemoryUtil.memAllocFloat(6 * 4);
    }

    public void draw(Image image){
        use();
        image.bind(0);
        vertices.put(new float[]{
                -1, -1, 0, 0,
                1, -1, 1, 0,
                1, 1, 1, 1,
                -1, -1, 0, 0,
                1, 1, 1, 1,
                -1, 1, 0, 1
        });
        vertices.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        vertices.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
        MemoryUtil.memFree(vertices);
    }
}
