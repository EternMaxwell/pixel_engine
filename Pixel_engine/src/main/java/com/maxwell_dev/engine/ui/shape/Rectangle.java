package com.maxwell_dev.engine.ui.shape;

import com.maxwell_dev.engine.ui.UIShape;
import org.joml.Vector2f;

public class Rectangle extends UIShape{
    private float x;
    private float y;
    private float width;
    private float height;


    /**
     * create a rectangle
     * @param x the x coordinate of the bottom left corner
     * @param y the y coordinate of the bottom left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    /**
     * get the vertices of the rectangle
     * @return the vertices of the rectangle
     */
    public Vector2f[] getVertices() {
        Vector2f[] vertices = new Vector2f[4];
        vertices[0] = new Vector2f(x,y);
        vertices[1] = new Vector2f(x+width,y);
        vertices[2] = new Vector2f(x+width,y+height);
        vertices[3] = new Vector2f(x,y+height);
        return vertices;
    }

    @Override
    /**
     * get a vertex of the rectangle
     * @param index the index of the vertex
     * @return the vertex
     */
    public Vector2f getVertex(int index) {
        //return a list of vertices in clockwise order
        switch (index) {
            case 0:
                return new Vector2f(x,y+height);
            case 1:
                return new Vector2f(x+width,y+height);
            case 2:
                return new Vector2f(x+width,y);
            case 3:
                return new Vector2f(x,y);
            default:
                return null;
        }
    }

    @Override
    /**
     * check if a point is inside the rectangle
     * @param point the point to check
     * @return true if the point is inside the rectangle
     */
    public boolean contains(Vector2f point) {
        return point.x >= x && point.x <= x+width && point.y >= y && point.y <= y+height;
    }
}
