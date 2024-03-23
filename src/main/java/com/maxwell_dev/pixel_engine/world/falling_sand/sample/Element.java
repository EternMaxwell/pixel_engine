package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Element<ElementID> extends com.maxwell_dev.pixel_engine.world.falling_sand.Element<Grid<?,?, ElementID>, ElementType, ElementID> {
    protected float[] color;

    public abstract boolean freeFall();

    @Override
    public float[] color() {
        return color;
    }

    @Override
    public boolean contaminate(float[] color, float intensity) {
        this.color[0] = this.color[0] * (1 - intensity) + color[0] * intensity;
        return true;
    }
}
