package com.maxwell_dev.pixel_engine.world.falling_sand;

import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class Grid <T extends Element<?,?,?>,V extends Renderer, ActionEnum>{
    protected float pixelSize;
    protected float gravity_y;
    protected float gravity_x;
    protected float gravity;
    protected float downX;
    protected float downY;

    public abstract int tick();
    public abstract T get(int x, int y);
    public abstract boolean valid(int x, int y);
    public abstract boolean invalidAsWall();
    public abstract void set(int x, int y, T element);
    public abstract void removeElementAt(int x, int y);
    public abstract T popElementAt(int x, int y);
    public abstract double step();
    public abstract void render(V renderer);
    public abstract int[] basePos();
    public abstract void action(double x, double y, ActionEnum action, double[] arguments);
    public abstract float airDensity();
    public abstract float airResistance();
    public float pixelSize() {
        return pixelSize;
    }
    public float gravity_y() {
        return gravity_y;
    }
    public float gravity_x() {
        return gravity_x;
    }
    public float gravity(){
        return gravity;
    }
    public void setGravity_x(float gravity_x) {
        this.gravity_x = gravity_x;
        gravity = (float) Math.sqrt(gravity_x * gravity_x + gravity_y * gravity_y);
        if(gravity == 0){
            downX = 0;
            downY = 0;
            return;
        }
        downX = gravity_x / gravity;
        downY = gravity_y / gravity;
    }
    public void setGravity_y(float gravity_y) {
        this.gravity_y = gravity_y;
        gravity = (float) Math.sqrt(gravity_x * gravity_x + gravity_y * gravity_y);
        if(gravity == 0){
            downX = 0;
            downY = 0;
            return;
        }
        downX = gravity_x / gravity;
        downY = gravity_y / gravity;
    }
    public float downX() {
        return downX;
    }
    public float downY() {
        return downY;
    }
    public abstract float default_vx();
    public abstract float default_vy();
    public abstract float tickTime();

    public void dispose(){}
}
