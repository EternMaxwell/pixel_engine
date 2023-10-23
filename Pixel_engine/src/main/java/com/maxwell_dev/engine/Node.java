package com.maxwell_dev.engine;

import java.util.Set;

/**
 * The basic node class. A node is a part of the scene graph. It can have children and a parent. It also has a name and a type.
 */
public class Node {
    private final NodeType type;
    private final String name;

    /**
     * create a new node with the given type and name
     * @param type the type of the node
     * @param name the name of the node
     */
    protected Node(NodeType type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * create a new node with the given name
     * @param name the name of the node
     */
    public Node(String name) {
        this(NodeType.NODE, name);
    }

    /**
     * get the type of the node
     * @return the type of the node
     */
    public NodeType type() {
        return type;
    }

    /**
     * get the name of the node 
     * @return the name of the node
     */
    public String name() {
        return name;
    }
}
