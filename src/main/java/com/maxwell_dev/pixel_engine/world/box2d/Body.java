package com.maxwell_dev.pixel_engine.world.box2d;

import org.jbox2d.dynamics.World;

public abstract class Body {
    public org.jbox2d.dynamics.Body body;
    public Body(org.jbox2d.dynamics.Body body) {
        this.body = body;
    }
    public abstract org.jbox2d.dynamics.Body getBox2dBody(World world);
}
