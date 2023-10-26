package com.maxwell_dev.engine.render;

import org.lwjgl.glfw.*;

import com.maxwell_dev.engine.Node;
import com.maxwell_dev.engine.NodeType;

import static org.lwjgl.glfw.GLFW.*;

public class Window extends Node{
    private long window;
    private int width;
    private int height;
    private String title;

    public void createWindow() {
        glfwDefaultWindowHints();
        window = glfwCreateWindow(width, height, title, 0, 0);
        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            if(window != this.window)
                return;
            this.width = width;
            this.height = height;
        });
    }

    public Window(String name, int width, int height, String title) {
        super(NodeType.WINDOW, name);
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /**
     * set the window position
     * @param xpos the x position of the window
     * @param ypos the y position of the window
     */
    public void setPosition(int xpos, int ypos) {
        glfwSetWindowPos(window, xpos, ypos);
    }

    /**
     * set the window size
     * @param width the width of the window
     * @param height the height of the window
     */
    public void setSize(int width, int height) {
        glfwSetWindowSize(window, width, height);
    }

    /**
     * set the window title
     * @param title the title of the window
     */
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
    }

    /**
     * set the window monitor
     * @param monitor the monitor to set
     * @param xpos the x position of the window
     * @param ypos the y position of the window
     * @param width the width of the window
     * @param height the height of the window
     * @param refreshRate the refresh rate of the window
     */
    public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate) {
        glfwSetWindowMonitor(window, monitor, xpos, ypos, width, height, refreshRate);
    }

    /**
     * get the id of the window
     * @return the id of the window
     */
    public long id(){
        return window;
    }

    /**
     * get the width of the window
     * @return the width of the window
     */
    public int width(){
        return width;
    }

    /**
     * get the height of the window
     * @return the height of the window
     */
    public int height() {
        return height;
    }
    
    /**
     * get the title of the window
     * @return the title of the window
     */
    public String title() {
        return title;
    }
}
