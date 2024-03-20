package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Liquid<ElementID> extends Element<ElementID>{
    @Override
    public ElementType type() {
        return ElementType.LIQUID;
    }
}
