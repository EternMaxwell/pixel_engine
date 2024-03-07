package com.maxwell_dev.pixel_engine.render;

public abstract class Window {
    public abstract long id();
    public abstract int width();
    public abstract int height();
    public abstract int[] size();
    public abstract float ratio();
    public abstract int xpos();
    public abstract int ypos();
    public abstract int[] pos();
}
