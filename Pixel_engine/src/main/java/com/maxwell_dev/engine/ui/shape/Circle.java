package com.maxwell_dev.engine.ui.shape;

import org.joml.Vector2f;
import com.maxwell_dev.engine.ui.UIShape;

public class Circle extends UIShape{
    private float x;
    private float y;
    private float radius;

    /**
     * create a circle
     * @param x the x coordinate of the center
     * @param y the y coordinate of the center
     * @param radius the radius of the circle
     */
    public Circle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    /**
     * get the vertices of the circle
     * @return the vertices of the circle
     */
    public Vector2f[] getVertices() {
        return null;
    }

    @Override
    /**
     * get a vertex of the circle
     * @param index the index of the vertex
     * @return the vertex
     */
    public Vector2f getVertex(int index) {
        return null;
    }

    @Override
    /**
     * check if a point is inside the circle
     * @param point the point to check
     * @return true if the point is inside the circle
     */
    public boolean contains(Vector2f point) {
        float distance = (float) Math.sqrt(Math.pow((double)(x - point.x), 2) + Math.pow((double)(y - point.y), 2));
        return distance <= radius*radius;
    }
}
