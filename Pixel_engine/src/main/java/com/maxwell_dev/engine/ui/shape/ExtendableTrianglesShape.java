package com.maxwell_dev.engine.ui.shape;

import org.joml.Vector2f;

import java.util.LinkedList;
import java.util.List;

public class ExtendableTrianglesShape extends CombinedShape{
    private final List<Vector2f> vertices;

    public ExtendableTrianglesShape() {
        super();
        vertices = new LinkedList<>();
    }

    /**
     * add a triangle to this shape
     * @param index0 the index of the first vertex of the triangle
     * @param index1 the index of the second vertex of the triangle
     * @param vertex the vertex to add
     * @return the index of the vertex
     */
    public int addVertex(int index0, int index1, Vector2f vertex){
        vertices.add(vertex);
        addShape(new Triangle(vertices.get(index0), vertices.get(index1), vertex), true);
        return vertices.size() - 1;
    }

    /**
     * add a triangle to this shape
     * @param index0 the index of the first vertex of the triangle
     * @param index1 the index of the second vertex of the triangle
     * @param index2 the index of the third vertex of the triangle
     */
    public void addVertex(int index0, int index1, int index2){
        addShape(new Triangle(vertices.get(index0), vertices.get(index1), vertices.get(index2)), true);
    }

    /**
     * add a triangle to this shape
     * @param index0 the index of the first vertex of the triangle
     * @param vertex1 the second vertex of the triangle
     * @param vertex2 the third vertex of the triangle
     * @return the indices of added the vertices
     */
    public int[] addVertex(int index0, Vector2f vertex1, Vector2f vertex2){
        vertices.add(vertex1);
        vertices.add(vertex2);
        addShape(new Triangle(vertices.get(index0), vertex1, vertex2), true);
        return new int[]{vertices.size() - 2, vertices.size() - 1};
    }

    /**
     * add a triangle to this shape
     * @param vertex0 the first vertex of the triangle
     * @param vertex1 the second vertex of the triangle
     * @param vertex2 the third vertex of the triangle
     * @return the indices of added the vertices
     */
    public int[] addVertex(Vector2f vertex0, Vector2f vertex1, Vector2f vertex2){
        vertices.add(vertex0);
        vertices.add(vertex1);
        vertices.add(vertex2);
        addShape(new Triangle(vertex0, vertex1, vertex2), true);
        return new int[]{vertices.size() - 3, vertices.size() - 2, vertices.size() - 1};
    }
}
