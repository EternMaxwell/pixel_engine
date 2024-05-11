package com.maxwell_dev.pixel_engine.core;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.stage.Stage;
import com.maxwell_dev.pixel_engine.ui_system.UIManager;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public abstract class Application<T extends Renderer, V extends InputTool> {

    /**
     * The renderer of the application
     */
    public T renderer;

    /**
     * The stage of the application
     */
    public Stage<T, V> stage;

    /**
     * The UI manager of the application
     */
    public UIManager<T, V> uiManager;

    /**
     * The input tool of the application
     */
    public V inputTool;

    /**
     * The pause value of the application
     * <p>Only affects the stage</p>
     * <p>Default value is false</p>
     */
    private boolean paused = false;

    /**
     * Start the application
     */
    public void start() {
        init();
        while (running()) {
            loop();
        }
        destroy();
    }

    /**
     * Initialize the application
     * <p>Needs to be implemented by the user</p>
     */
    public abstract void init();

    /**
     * The main loop of the application
     * <p>Has automatically implemented by the engine</p>
     */
    public void loop() {
        input();
        update();
        render();
    }

    /**
     * Handle the input
     * <p>Only uses in the loop method</p>
     */
    public void input() {
        glfwPollEvents();
        inputTool.input();
        uiManager.input(inputTool);
        if (!paused && stage != null) {
            stage.input(inputTool);
        }
    }

    /**
     * Update the application
     * <p>Only uses in the loop method</p>
     */
    public void update() {
        uiManager.update();
        if (!paused && stage != null) {
            stage.update();
        }
    }

    /**
     * Render the application
     * <p>Mostly uses in the loop method</p>
     * <p>Also recommend to use in glfwSetSizeCallback</p>
     */
    public void render() {
        renderer.begin();
        if(stage != null){
            stage.render(renderer);
        }
        uiManager.render(renderer);
        renderer.end();
    }

    /**
     * Destroy the application
     * <p>Needs to be implemented by the user</p>
     */
    public abstract void destroy();

    /**
     * Check if the application is running
     * <p>Needs to be implemented by the user</p>
     * @return true if the application is running
     */
    public abstract boolean running();

    /**
     * Check if the application is paused
     * <p>The pause value only affects the stage</p>
     * @return true if the application is paused
     */
    public boolean paused() {
        return paused;
    }

    /**
     * Pause the application
     * <p>Only affects the stage</p>
     */
    public void pause() {
        paused = !paused;
    }
}
