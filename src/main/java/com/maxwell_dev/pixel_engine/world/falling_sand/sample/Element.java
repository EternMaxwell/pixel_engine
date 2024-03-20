package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Element<ElementID> extends com.maxwell_dev.pixel_engine.world.falling_sand.Element<Grid<?,?,ElementID>, ElementType, ElementID> {
    public abstract boolean freeFall();
}
