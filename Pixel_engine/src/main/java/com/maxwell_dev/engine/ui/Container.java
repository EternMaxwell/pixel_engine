package com.maxwell_dev.engine.ui;

import java.util.LinkedList;
import java.util.List;

public class Container {
    private int focusIndex;
    public Container parent;
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
        child.parent = this;
        return this;
    }

    /**
     * remove a child from this container
     * @param child the child to remove
     * @return this container
     */
    public Container removeChild(Container child) {
        if(focusIndex >= 0 && children.indexOf(child) < focusIndex)
            focusIndex--;
        children.remove(child);
        child.parent = null;
        return this;
    }

    /**
     * get the root of this container
     * @return the root of this container
     */
    public Container root(){
        Container result = this;
        while(result.parent != null){
            result = result.parent;
        }
        return result;
    }

    /**
     * if this container is focused
     * @return true if this container is focused
     */
    public boolean focused() {
        return root().focus() == this;
    }
}
