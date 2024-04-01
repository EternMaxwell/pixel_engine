package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

import org.jetbrains.annotations.NotNull;

public abstract class Powder<ElementID> extends Element<ElementID>{
    private int lastTick = -1;
    private float velocity = 0.7f;
    private float sinkingProcess = 0.0f;
    private float floatingProcess = 0.0f;
    private boolean falling = true;
    private boolean sinkInLargeDensity = true;

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
        return falling;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {

    }

    public boolean sinkInLargeDensity(){
        return sinkInLargeDensity;
    }
}
