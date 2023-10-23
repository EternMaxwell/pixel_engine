package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL46;

public class FaceCull {
    private int frontFace;
    private boolean cullFace;
    private int mode;

    /**
     * set the face culling
     * @param face the face to cull. One of:
     * <p>{@link GL46#GL_CW}, {@link GL46#GL_CCW}</p>
     * @param mode the face culling mode. One of:
     * <p>{@link GL46#GL_FRONT}, {@link GL46#GL_BACK}, {@link GL46#GL_FRONT_AND_BACK}</p>
     */
    public FaceCull(int face,boolean cullFace, int mode) {
        this.frontFace = face;
        this.cullFace = cullFace;
        this.mode = mode;
    }

    /**
     * set the face culling
     * @param face the face to cull. One of:
     * <p>{@link GL46#GL_CW}, {@link GL46#GL_CCW}</p>
     * @param mode the face culling mode. One of:
     * <p>{@link GL46#GL_FRONT}, {@link GL46#GL_BACK}, {@link GL46#GL_FRONT_AND_BACK}</p>
     * @return this FaceCull
     */
    public FaceCull set(int face, int mode) {
        this.frontFace = face;
        this.mode = mode;
        return this;
    }

    /**
     * set the face culling
     * @param mode the face culling mode. One of:
     * <p>{@link GL46#GL_FRONT}, {@link GL46#GL_BACK}, {@link GL46#GL_FRONT_AND_BACK}</p>
     * @return this FaceCull
     */
    public FaceCull set(int mode) {
        this.mode = mode;
        return this;
    }

    /**
     * get the front face
     * @return the front face
     */
    public int frontFace() {
        return frontFace;
    }

    /**
     * get whether to cull faces
     * @return whether to cull faces
     */
    public boolean cullFace() {
        return cullFace;
    }

    /**
     * get the face culling mode
     * @return the face culling mode
     */
    public int mode() {
        return mode;
    }
}
