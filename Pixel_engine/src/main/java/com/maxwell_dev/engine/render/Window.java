package com.maxwell_dev.engine.render;

import org.lwjgl.glfw.*;

import com.maxwell_dev.engine.Node;

import static org.lwjgl.glfw.GLFW.*;

public class Window extends Node{
    private long window;
    private int width;
    private int height;
    private String title;

    /**
     * create a new window with the given name, width, height, and title
     * @param name the name the window as a node
     * @param width the width of the window
     * @param height the height of the window
     * @param title the title of the window
     */
    public Window(String name, int width, int height, String title) {
        super(name);
        this.width = width;
        this.height = height;
        this.title = title;
    }
}
