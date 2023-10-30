package com.maxwell_dev.engine.render;

import com.maxwell_dev.engine.NodeType;
import com.maxwell_dev.engine.Entity;

public class VisibleEntity extends Entity {
    public VisibleEntity(String name) {
        super(NodeType.VISIBLE_ENTITY,name);
    }

    protected VisibleEntity(NodeType type, String name) {
        super(type, name);
    }
}
