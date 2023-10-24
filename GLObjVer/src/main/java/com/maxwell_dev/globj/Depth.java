package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL46.*;
import org.lwjgl.opengl.GL46;

public class Depth {
    private int func;

    public Depth() {
        func = GL_LESS;
    }

    /**
     * set the depth function
     * @param func the depth function. One of:
     * <p>{@link GL46#GL_NEVER}: Never passes.</p>
     * <p>{@link GL46#GL_LESS}: Passes if the incoming depth value is less than the stored depth value.</p>
     * <p>{@link GL46#GL_EQUAL}: Passes if the incoming depth value is equal to the stored depth value.</p>
     * <p>{@link GL46#GL_LEQUAL}: Passes if the incoming depth value is less than or equal to the stored depth value.</p>
     * <p>{@link GL46#GL_GREATER}: Passes if the incoming depth value is greater than the stored depth value.</p>
     * <p>{@link GL46#GL_NOTEQUAL}: Passes if the incoming depth value is not equal to the stored depth value.</p>
     * <p>{@link GL46#GL_GEQUAL}: Passes if the incoming depth value is greater than or equal to the stored depth value.</p>
     * <p>{@link GL46#GL_ALWAYS}: Always passes.</p>
     */
    public void set(int func) {
        this.func = func;
    }

    /**
     * get the depth function 
     * @return the depth function
     */
    public int func() {
        return func;
    }
}
