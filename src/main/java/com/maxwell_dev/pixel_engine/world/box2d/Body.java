package com.maxwell_dev.pixel_engine.world.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.Collection;

public abstract class Body {
    public org.jbox2d.dynamics.Body body;
    public abstract org.jbox2d.dynamics.Body createBox2dBody(World world, float x, float y, float angle, Collection<Vec2[]> trianglesRetrieve, float pixelSize);
    public void removeBox2dBody() {
        body.getWorld().destroyBody(body);
    }
}
