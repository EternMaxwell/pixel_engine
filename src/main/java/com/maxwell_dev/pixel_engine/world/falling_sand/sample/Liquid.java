package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Liquid<ElementID> extends Element<ElementID>{
    boolean falling = true;
    int lastTick = -1;
    float sinkingProcess = 0.0f;
    float velocity = 0.7f;
    int dir = Math.random() > 0.5? 1: -1;
    int lastBlocked = 0;

    public Liquid(Grid<?, ?, ElementID> grid) {
        super(grid);
    }

    @Override
    public ElementType type() {
        return ElementType.LIQUID;
    }

    /**
     * @return the dispersion rate.
     */
    public abstract int dispersionRate();

    /**
     * get if the fluid is free-falling.
     * @return true if the fluid is free-falling.
     */
    public boolean freeFall() {
        return falling;
    }

    @Override
    public boolean step(Grid<?,?,ElementID> grid, int x, int y, int tick) {
        return false;
    }
}
