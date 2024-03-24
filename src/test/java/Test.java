import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.FrameBuffer;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.render.opengl.sample.LineDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelLightDrawer;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import fallingsandsampletest.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application {
    Window window;
    InputTool inputTool;
    Render render;

    FallingGrid grid;

    Element<ElementID>[] elements = new Element[]{new Sand(), new Stone()};
    int index = 0;

    PixelLightDrawer pixelLightDrawer;
    Image lightMap;
    Image test;
    FrameBuffer frameBuffer;

    int texture;
    int texture2;

    @Override
    public void init() {
        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        window = new Window(800, 600, "Test");
        window.setContextVersionMajor(4);
        window.setContextVersionMinor(6);
        window.createWindow();
        glfwMakeContextCurrent(window.id());
        GL.createCapabilities();
        inputTool = new InputTool(window);
        glfwSetWindowSizeCallback(window.id(), (window, width, height) -> {
            glViewport(0, 0, width, height);
            render();
        });
        render = new Render();
        grid = new FallingGrid();
        frameBuffer = new FrameBuffer(512, 512);
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTextureStorage2D(texture, 1, GL_RGBA8, 512, 512);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        texture2 = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture2);
        glTextureStorage2D(texture2, 1, GL_RGBA8, 512, 512);
        lightMap = new Image(texture2);
        lightMap.samplerParameteri(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        lightMap.samplerParameteri(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        lightMap.samplerParameteri(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        lightMap.samplerParameteri(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        frameBuffer.attachColor(0, texture);
        frameBuffer.drawBuffer(0);
//        int depthRenderBuffer = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, 512, 512);
//        frameBuffer.renderBuffer(GL_DEPTH_ATTACHMENT, depthRenderBuffer);

        frameBuffer.check();

        test = new Image("src/test/resources/textures/test.jpg", 0);

        pixelLightDrawer = new PixelLightDrawer(frameBuffer);
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input();
        if (inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_1)){
            double x = inputTool.mouseX();
            double y = inputTool.mouseY();
            int gridX = (int) ((1 + x) * 512);
            int gridY = (int) ((1 + y) * 512);
            for(int i = -5; i <= 5; i++)
                for(int j = -5; j <= 5; j++)
                    grid.set(gridX + i, gridY + j, (Element<ElementID>) elements[index].newInstance());
        }
        if(inputTool.scrollY() > 0)
            index = (index + 1) % elements.length;
        if(inputTool.scrollY() < 0)
            index = (index - 1 + elements.length) % elements.length;
    }

    @Override
    public void update() {
        long start = System.nanoTime();
        grid.step();
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6);
    }

    @Override
    public void render() {
        render.begin();
        render.pixelDrawer.setProjection(new Matrix4f().ortho(-(window.ratio() - 1) * 512, (window.ratio() + 1) * 512, 0, 1 * 1024f, -1, 1));
        render.pixelDrawer.setView(new Matrix4f().identity());
        render.pixelDrawer.setModel(new Matrix4f().identity());
        pixelLightDrawer.setProjection(new Matrix4f().identity());
        pixelLightDrawer.setView(new Matrix4f().identity());
        pixelLightDrawer.setModel(new Matrix4f().identity());

        glViewport(0, 0, 512, 512);
        pixelLightDrawer.drawLightMap(0,0,0,0,1,1,1,1,1);
        long fence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
        glClientWaitSync(fence, GL_SYNC_FLUSH_COMMANDS_BIT, 1_000_000_000);
        glDeleteSync(fence);
        frameBuffer.bindAsRead();
        glCopyTextureSubImage2D(texture2, 0, 0, 0, 0, 0, 512, 512);

        frameBuffer.unbind(window);
        render.imageDrawer.draw(lightMap);
//        frameBuffer.bindAsRead();
//        glReadBuffer(GL_FRONT_RIGHT);
//        render.imageDrawer.draw(test);

        grid.render(render);
        render.end();
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        frameBuffer.clear(window);
    }

    @Override
    public void destroy() {
        window.dispose();
        render.destroy();
        frameBuffer.dispose();
        glfwTerminate();
    }

    @Override
    public boolean running() {
        return !glfwWindowShouldClose(window.id());
    }

    @Override
    public void pause() {

    }
}
