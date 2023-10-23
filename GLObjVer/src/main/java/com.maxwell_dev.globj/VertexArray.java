package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL45.*;

public class VertexArray implements glInterface {
    private final int id;

    /**
     * create a vertex array object
     */
    public VertexArray() {
        id = glCreateVertexArrays();
    }

    /**
     * get the vertex array id
     *
     * @return the id
     */
    public int id() {
        return id;
    }

    /**
     * delete the object
     */
    public void delete() {
        glDeleteVertexArrays(id);
    }
}
