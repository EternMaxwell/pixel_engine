package com.maxwell_dev.pixel_engine.world.falling_sand;

public class ElementPlaceHolder extends Element{
    public static final ElementPlaceHolder PLACEHOLDER = new ElementPlaceHolder();

    @Override
    public String name() {
        return null;
    }

    @Override
    public Element newInstance() {
        return null;
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
    public float[] color() {
        return new float[0];
    }

    @Override
    public float density() {
        return 0;
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
