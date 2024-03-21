package com.maxwell_dev.pixel_engine.world.box2d.sample;

import com.maxwell_dev.pixel_engine.world.box2d.Body;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import org.jbox2d.dynamics.World;

public class ElementBody<T extends Element> extends Body {
    T[][] grid;
    private ElementBody(World world, T[][] grid) {

    }

    @Override
    public org.jbox2d.dynamics.Body createBox2dBody(World world) {
        return null;
    }
}
