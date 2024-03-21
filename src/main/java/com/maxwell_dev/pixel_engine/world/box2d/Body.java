package com.maxwell_dev.pixel_engine.world.box2d;

import org.jbox2d.dynamics.World;

public abstract class Body {
    public org.jbox2d.dynamics.Body body;
    public abstract org.jbox2d.dynamics.Body createBox2dBody(World world);
}
