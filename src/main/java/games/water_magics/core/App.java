package games.water_magics.core;

import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class App extends Application<Render, InputTool> {

    Window window;

    @Override
    public void init() {
        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        window = new Window(1920, 1080, "Water Magics");
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
        renderer = new Render(window);
        renderer.init();

        uiManager = new UISystem();
        uiManager.init();

        stage = new GameStage();
        stage.init();
    }

    @Override
    public void destroy() {
        if(stage != null)
            stage.dispose();
        window.dispose();
        renderer.destroy();
        glfwTerminate();
    }

    @Override
    public boolean running() {
        return !glfwWindowShouldClose(window.id());
    }
}
