package com.maxwell_dev.pixel_engine.world.falling_sand;

public abstract class Element <T extends Grid<?,?,?>, ElementType, ElementID>{
    //methods that must be implemented
    public abstract String name();
    public abstract Element<T,ElementType,ElementID> newInstance();
    public abstract ElementID id();
    public abstract ElementType type();
    public abstract boolean step(T grid, int x, int y, int tick);
    public abstract float[] color();

    //functions that may not be needed
    public abstract float density();
    public abstract boolean heat(T grid, int x, int y, float heat);
    public abstract boolean damage(T grid, int x, int y, float damage);
    public abstract boolean contaminate(float[] color, float intensity);
    public abstract boolean contaminate(Element<T,ElementType,ElementID> element, float intensity);
}
