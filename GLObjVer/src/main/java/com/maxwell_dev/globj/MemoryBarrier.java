package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL46;

public class MemoryBarrier {
    private int barrier;

    public MemoryBarrier() {
        barrier = 0;
    }

    /**
     * get the barrier
     * @return the barrier
     */
    public int barrier() {
        return barrier;
    }

    /**
     * set the barrier
     * @param barrier the barrier. A bitwise OR of the following values:
     * <p>{@link GL46#GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT}, {@link GL46#GL_ELEMENT_ARRAY_BARRIER_BIT},
     * {@link GL46#GL_UNIFORM_BARRIER_BIT}, {@link GL46#GL_TEXTURE_FETCH_BARRIER_BIT},
     * {@link GL46#GL_SHADER_IMAGE_ACCESS_BARRIER_BIT}, {@link GL46#GL_COMMAND_BARRIER_BIT},
     * {@link GL46#GL_PIXEL_BUFFER_BARRIER_BIT}, {@link GL46#GL_TEXTURE_UPDATE_BARRIER_BIT},
     * {@link GL46#GL_BUFFER_UPDATE_BARRIER_BIT}, {@link GL46#GL_FRAMEBUFFER_BARRIER_BIT},
     * {@link GL46#GL_TRANSFORM_FEEDBACK_BARRIER_BIT}, {@link GL46#GL_ATOMIC_COUNTER_BARRIER_BIT},
     * {@link GL46#GL_SHADER_STORAGE_BARRIER_BIT}, {@link GL46#GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT},
     * {@link GL46#GL_QUERY_BUFFER_BARRIER_BIT}, {@link GL46#GL_ALL_BARRIER_BITS}</p>
     */
    public void setBarrier(int barrier) {
        this.barrier = barrier;
    }
}
