package com.maxwell_dev.engine;

public class Entity extends Node {
    public Entity(String name) {
        super(NodeType.ENTITY, name);
    }

    protected Entity(NodeType type, String name) {
        super(type, name);
    }
}
