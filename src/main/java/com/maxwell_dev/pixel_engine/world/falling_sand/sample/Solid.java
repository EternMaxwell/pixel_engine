package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Solid<ElementID> extends Element<ElementID>{
    @Override
    public ElementType type() {
        return ElementType.SOLID;
    }

    /**
     * step for Solid won't do anything.
     * @param grid the grid.
     * @param x    the x position.
     * @param y    the y position.
     * @param tick the tick.
     * @return false.
     */
    @Override
    public boolean step(Grid<?,?, ElementID> grid, int x, int y, int tick){
        if(lastTick == tick)
            return false;
        lastTick = tick;
        return false;
    }

    /**
     * free fall for Solid will always return false.
     * @return false.
     */
    @Override
    public boolean freeFall(){
        return false;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {
        //do nothing
    }
}
