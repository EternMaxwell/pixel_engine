package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Powder<ElementID> extends Element<ElementID>{
    @Override
    public ElementType type() {
        return ElementType.POWDER;
    }
}
