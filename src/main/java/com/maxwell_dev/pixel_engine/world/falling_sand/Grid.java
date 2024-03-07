package com.maxwell_dev.pixel_engine.world.falling_sand;

public abstract class Grid <T extends Element, Renderer>{
    public abstract T elementsAt(int x, int y);
    public abstract boolean validAt(int x, int y);
    public abstract boolean invalidAsWall();
    public abstract void setElementAt(int x, int y, T element);
    public abstract void removeElementAt(int x, int y);
    public abstract T popElementAt(int x, int y);
    public abstract double step();
    public abstract void render(Renderer renderer);
    public abstract int[] basePos();
}
