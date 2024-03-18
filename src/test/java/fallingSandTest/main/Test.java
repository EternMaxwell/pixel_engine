package fallingSandTest.main;

import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application {
    Window window;
    InputTool inputTool;
    @Override
    public void init() {
        if(!glfwInit()){
            System.err.println("Failed to initialize GLFW");
            System.exit(1);
        }
        window = new Window(800, 600, "Test");
        window.setContextVersionMajor(4);
        window.setContextVersionMinor(6);
        window.createWindow();
        glfwMakeContextCurrent(window.id());
        GL.createCapabilities();
        inputTool = new InputTool(window);
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input(window);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        window.swapBuffers();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean running() {
        return !glfwWindowShouldClose(window.id());
    }

    @Override
    public void pause() {

    }
}
