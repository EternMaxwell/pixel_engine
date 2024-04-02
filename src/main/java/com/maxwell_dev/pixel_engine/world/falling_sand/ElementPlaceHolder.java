package com.maxwell_dev.pixel_engine.world.falling_sand;

public class ElementPlaceHolder extends Element{
    public static final ElementPlaceHolder PLACEHOLDER = new ElementPlaceHolder(null);

    public ElementPlaceHolder(Grid grid) {
        super(grid);
    }

    @Override
    public String name() {
        return "element:placeholder";
    }

    @Override
    public Element newInstance(Grid grid) {
        return PLACEHOLDER;
    }

    @Override
    public Object id() {
        return null;
    }

    @Override
    public Object type() {
        return null;
    }

    @Override
    public boolean randomTick(Grid grid, int x, int y, int tick, int intensity) {
        return false;
    }

    @Override
    public float[] color() {
        return new float[]{0,0,1,1};
    }

    @Override
    public void touch(Grid grid, int x, int y) {

    }

    @Override
    public float density() {
        return 1;
    }

    @Override
    public float friction() {
        return 0.6f;
    }

    @Override
    public float restitution() {
        return 0.1f;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {

    }

    @Override
    public boolean contaminate(float[] color, float intensity) {
        return false;
    }

    @Override
    public boolean damage(Grid grid, int x, int y, float damage) {
        return false;
    }

    @Override
    public boolean heat(Grid grid, int x, int y, float heat) {
        return false;
    }

    @Override
    public boolean step(Grid grid, int x, int y, int tick) {
        return false;
    }
}
