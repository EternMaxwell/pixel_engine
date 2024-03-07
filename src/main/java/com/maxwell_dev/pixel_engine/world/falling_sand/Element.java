package com.maxwell_dev.pixel_engine.world.falling_sand;

public abstract class Element <T extends Grid, ElementType, ElementID>{
    public abstract String name();
    public abstract ElementID id();
    public abstract ElementType type();
    public abstract void step(T grid, int x, int y);
    public abstract float[] color();
}
