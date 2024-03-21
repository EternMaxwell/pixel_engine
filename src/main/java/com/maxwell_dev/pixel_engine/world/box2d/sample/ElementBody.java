package com.maxwell_dev.pixel_engine.world.box2d.sample;

import com.maxwell_dev.pixel_engine.world.box2d.Body;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import org.jbox2d.dynamics.World;

public class ElementBody<T extends Element> extends Body {
    T[][] grid;
    public ElementBody(World world, T[][] body) {
        super(createBody(world, body));
    }

    public static org.jbox2d.dynamics.Body createBody(World world, Element[][] grid) {
        return null;
    }

    @Override
    public org.jbox2d.dynamics.Body getBox2dBody(World world) {
        return null;
    }
}
