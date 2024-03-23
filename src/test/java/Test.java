import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import fallingsandsampletest.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application {
    Window window;
    InputTool inputTool;
    LineDrawer lineDrawer;
    Render render;

    FallingGrid grid;

    Element<ElementID>[] elements = new Element[]{new Sand(), new Stone()};
    int index = 0;

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
        lineDrawer = new LineDrawer();
        glfwSetWindowSizeCallback(window.id(), (window, width, height) -> {
            glViewport(0, 0, width, height);
            render();
        });
        render = new Render();
        grid = new FallingGrid();
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
        grid.render(render);
        render.end();
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        window.dispose();
        lineDrawer.dispose();
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
