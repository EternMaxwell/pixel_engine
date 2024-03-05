package com.maxwell_dev.pixel_engine.core;

public abstract class Application {
    public abstract void start();

    public abstract void init();
    public abstract void loop();
    public abstract void input();
    public abstract void update();
    public abstract void render();
    public abstract void destroy();
    public abstract boolean running();
    public abstract void pause();
}
