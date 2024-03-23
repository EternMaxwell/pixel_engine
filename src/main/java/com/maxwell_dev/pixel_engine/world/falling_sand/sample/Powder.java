package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

import org.jetbrains.annotations.NotNull;

public abstract class Powder<ElementID> extends Element<ElementID>{

    private boolean freeFall;

    @Override
    public ElementType type() {
        return ElementType.POWDER;
    }

    @Override
    public boolean step(Grid<?, ?, ElementID> grid, int x, int y, int tick) {
        return false;
    }

    @Override
    public boolean freeFall() {
        return freeFall;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {

    }
}
