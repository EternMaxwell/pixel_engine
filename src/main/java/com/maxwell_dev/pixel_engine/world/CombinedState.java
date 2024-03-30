package com.maxwell_dev.pixel_engine.world;

public interface CombinedState {

    /**
     * Get the position of the object
     * @return the position of the object
     */
    public float[] position();

    /**
     * call when the origin of the coordinate system changes
     * @param x the new x origin
     * @param y the new y origin
     */
    public void reOrigin(float x, float y);
}
