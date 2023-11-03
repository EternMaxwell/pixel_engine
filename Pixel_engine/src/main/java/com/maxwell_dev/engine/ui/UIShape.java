package com.maxwell_dev.engine.ui;

import java.util.Vector;

import org.joml.Vector2f;

public abstract class UIShape {
    public UIShape() {
    }

    public abstract Vector2f[] getVertices();

    public abstract Vector2f getVertex(int index);

    public abstract boolean contains(Vector2f point);
}
