package com.maxwell_dev.engine.render;

import com.maxwell_dev.engine.NodeType;
import com.maxwell_dev.engine.Entity;

import java.nio.ByteBuffer;

public abstract class VisibleEntity extends Entity {
    public VisibleEntity(String name) {
        super(NodeType.VISIBLE_ENTITY,name);
    }

    protected VisibleEntity(NodeType type, String name) {
        super(type, name);
    }

    /**
     * @return The index buffer
     */
    public abstract ByteBuffer indexBuffer();

    /**
     * @return The vertex buffer
     */
    public abstract ByteBuffer vertexBuffer();
}
