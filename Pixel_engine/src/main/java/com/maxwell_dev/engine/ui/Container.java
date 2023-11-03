package com.maxwell_dev.engine.ui;

import java.util.LinkedList;
import java.util.List;

public class Container {
    private int focusIndex;
    public static Container root;
    private final List<Container> children;

    public Container() {
        children = new LinkedList<>();
        focusIndex = -1;
    }

    /**
     * switch the focus to the next child
     * @return true if the focus is still in this container
     */
    public boolean iterateFocus() {
        focusIndex++;
        if (focusIndex >= children.size()) {
            focusIndex = -1;
            return false;
        }
        return true;
    }

    /**
     * get the focused container
     * @return the focused container
     */
    public Container focus() {
        if (focusIndex == -1) {
            return this;
        }
        Container result = this;
        while (result.focus() != result) {
            result = result.focus();
        }
        return result;
    }

    /**
     * add a child to this container
     * @param child the child to add
     * @return this container
     */
    public Container addChild(Container child) {
        children.add(child);
        return this;
    }

    /**
     * remove a child from this container
     * @param child the child to remove
     * @return this container
     */
    public Container removeChild(Container child) {
        children.remove(child);
        return this;
    }

    /**
     * if this container is focused
     * @return true if this container is focused
     */
    public boolean focused() {
        return root.focus() == this;
    }
}
