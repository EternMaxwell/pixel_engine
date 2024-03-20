package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Gas<ElementID> extends Element<ElementID>{
    @Override
    public ElementType type() {
        return ElementType.GAS;
    }
}
