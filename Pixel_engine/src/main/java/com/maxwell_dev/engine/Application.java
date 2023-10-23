package com.maxwell_dev.engine;

/**
 * An application node. This node is the root of the scene graph.
 */
public abstract class Application extends Node{
    public Application(String name) {
        super(NodeType.APPLICATION, name);
    }

    public abstract void init();

    public abstract void update();

    public abstract void render();

    public abstract void destroy();

    public abstract void run();

    public abstract void stop();

    public abstract void pause();

    public abstract void resume();
}
