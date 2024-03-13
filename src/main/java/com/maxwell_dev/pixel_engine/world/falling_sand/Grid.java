package com.maxwell_dev.pixel_engine.world.falling_sand;

import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class Grid <T extends Element<?,?,?>,V extends Renderer, ActionEnum>{
    public abstract T elementsAt(int x, int y);
    public abstract boolean validAt(int x, int y);
    public abstract boolean invalidAsWall();
    public abstract void setElementAt(int x, int y, T element);
    public abstract void removeElementAt(int x, int y);
    public abstract T popElementAt(int x, int y);
    public abstract double step();
    public abstract void render(V renderer);
    public abstract int[] basePos();
    public abstract void action(double x, double y, ActionEnum action, double[] arguments);
}