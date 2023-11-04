package com.maxwell_dev.engine.ui.shape;

import com.maxwell_dev.engine.ui.UIShape;
import org.joml.Vector2f;

public class Triangle extends UIShape {
    private final Vector2f[] vertices;
    private boolean clockwise;

    /**
     * create a new triangle
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     */
    public Triangle(Vector2f v1, Vector2f v2, Vector2f v3) {
        vertices = new Vector2f[3];
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
        Vector2f line1 = new Vector2f(vertices[1]).sub(vertices[0]);
        Vector2f line2 = new Vector2f(vertices[2]).sub(vertices[0]);
        float det = line1.x * line2.y - line1.y * line2.x;
        clockwise = det < 0;
    }

    /**
     * get the vertices of this triangle
     * @return the vertices of this triangle
     */
    public Vector2f[] getVertices() {
        return vertices;
    }

    /**
     * get the vertex at the specified index
     * @param index the index of the vertex
     * @return the vertex at the specified index
     */
    public Vector2f getVertex(int index) {
        return vertices[index];
    }

    /**
     * check if the specified point is inside this triangle
     * @param point the point to check
     * @return true if the point is inside this triangle
     */
    @Override
    public boolean contains(Vector2f point) {
        Vector2f arrow = new Vector2f(point).sub(vertices[0]);
        Vector2f line1 = new Vector2f(vertices[1]).sub(vertices[0]);
        Vector2f line2 = new Vector2f(vertices[2]).sub(vertices[0]);
        float det1 = arrow.x * line1.y - arrow.y * line1.x;
        float det2 = arrow.x * line2.y - arrow.y * line2.x;
        if(clockwise){
            if(det1 < 0 || det2 > 0){
                return false;
            }
        }else {
            if(det1 > 0 || det2 < 0){
                return false;
            }
        }
        Vector2f arrow2 = new Vector2f(point).sub(vertices[1]);
        Vector2f line3 = new Vector2f(vertices[2]).sub(vertices[1]);
        Vector2f line4 = new Vector2f(vertices[0]).sub(vertices[1]);
        float det3 = arrow2.x * line3.y - arrow2.y * line3.x;
        float det4 = arrow2.x * line4.y - arrow2.y * line4.x;
        if(clockwise){
            if(det3 < 0 || det4 > 0){
                return false;
            }
        }else {
            if(det3 > 0 || det4 < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * reorder the vertices of this triangle
     * @param clockwise true if the vertices should be ordered clockwise
     */
    private void order(boolean clockwise){
        this.clockwise = clockwise;
        Vector2f line1 = new Vector2f(vertices[1]).sub(vertices[0]);
        Vector2f line2 = new Vector2f(vertices[2]).sub(vertices[0]);
        float det = line1.x * line2.y - line1.y * line2.x;
        if(clockwise){
            if(det > 0){
                Vector2f temp = vertices[1];
                vertices[1] = vertices[2];
                vertices[2] = temp;
            }
        } else {
            if(det < 0){
                Vector2f temp = vertices[1];
                vertices[1] = vertices[2];
                vertices[2] = temp;
            }
        }
    }
}
