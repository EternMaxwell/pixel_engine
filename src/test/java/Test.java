import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import fallingsandsampletest.FallingSandStage;
import render.Render;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application<Render, InputTool> {
    Window window;
    InputTool inputTool;
    Render render;

    @Override
    public void init() {
        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        window = new Window(1024, 1024, "Test");
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
        render = new Render(window);
        stage = new FallingSandStage();
        stage.init();
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input();
        if (inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
        stage.input(inputTool);
    }

    @Override
    public void update() {
        long start = System.nanoTime();
        stage.update();
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6);
    }

    @Override
    public void render() {
        render.begin();
        stage.render(render);
        render.end();
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        stage.dispose();
        window.dispose();
        render.destroy();
        glfwTerminate();
    }

    @Override
    public boolean running() {
        return !glfwWindowShouldClose(window.id());
    }

}
