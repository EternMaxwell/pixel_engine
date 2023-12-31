package com.maxwell_dev.globj;

public class ViewPort {
    private final int[] viewport;
    private final float[] depthRange;
    
    public ViewPort() {
        viewport = new int[4];
        depthRange = new float[]{0,1};
    }
    
    public ViewPort set(int x, int y, int width, int height) {
        viewport[0] = x;
        viewport[1] = y;
        viewport[2] = width;
        viewport[3] = height;
        return this;
    }
    
    public ViewPort setDepth(float near, float far) {
        depthRange[0] = near;
        depthRange[1] = far;
        return this;
    }
    
    public int[] get() {
        return viewport;
    }
    
    public float[] getDepth() {
        return depthRange;
    }
}
