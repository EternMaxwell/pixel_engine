package com.maxwell_dev.pixel_engine.world.falling_sand;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.world.box2d.Body;
import org.jbox2d.dynamics.World;

import java.util.Collection;

public abstract class Grid <T extends Element<?,?,?>,V extends Renderer, ActionEnum>{
    protected float pixelSize;
    protected float gravity_y;
    protected float gravity_x;

    public abstract T elementAt(int x, int y);
    public abstract boolean validAt(int x, int y);
    public abstract boolean invalidAsWall();
    public abstract void setElementAt(int x, int y, T element);
    public abstract void removeElementAt(int x, int y);
    public abstract T popElementAt(int x, int y);
    public abstract double step();
    public abstract void render(V renderer);
    public abstract int[] basePos();
    public abstract void action(double x, double y, ActionEnum action, double[] arguments);
    public abstract float airDensity();
    public float pixelSize() {
        return pixelSize;
    }

    public float gravity_y() {
        return gravity_y;
    }

    public float gravity_x() {
        return gravity_x;
    }

    public abstract float tickTime();
}
