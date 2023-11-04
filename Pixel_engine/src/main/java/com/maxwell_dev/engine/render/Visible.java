package com.maxwell_dev.engine.render;

import com.maxwell_dev.engine.NodeType;
import com.maxwell_dev.engine.Entity;

import java.nio.ByteBuffer;

public interface Visible{

    /**
     * @return The index buffer
     */
    public ByteBuffer indexBuffer();

    /**
     * @return The vertex buffer
     */
    public ByteBuffer vertexBuffer();
}
