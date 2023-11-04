package com.maxwell_dev.engine.ui.shape;

import com.maxwell_dev.engine.ui.UIShape;
import org.joml.Vector2f;

import java.util.HashSet;
import java.util.Set;

public class CombinedShape extends UIShape {
    private final Set<UIShape> shapesContain;
    private final Set<UIShape> shapesNotContain;

    public CombinedShape() {
        shapesContain = new HashSet<>();
        shapesNotContain = new HashSet<>();
    }

    /**
     * add a shape to this combined shape
     * @param shape the shape to add
     * @param contain if this shape is contained in this combined shape
     */
    public void addShape(UIShape shape, boolean contain){
        if(contain){
            shapesContain.add(shape);
        }else {
            shapesNotContain.add(shape);
        }
    }

    /**
     * remove a shape from this combined shape
     * @param shape the shape to remove
     */
    public void removeShape(UIShape shape){
        shapesContain.remove(shape);
        shapesNotContain.remove(shape);
    }

    /**
     * Not supported in this class
     * @return null
     */
    @Override
    public Vector2f[] getVertices() {
        return null;
    }

    /**
     * Not supported in this class
     * @param index the index of the vertex
     * @return null
     */
    @Override
    public Vector2f getVertex(int index) {
        return null;
    }

    /**
     * if this shape contains the specified point
     * @param point the point to check
     * @return true if this shape contains the specified point
     */
    @Override
    public boolean contains(Vector2f point) {
        boolean result = false;
        for (UIShape shape : shapesContain) {
            result |= shape.contains(point);
        }
        if (!result) {
            return false;
        }
        for (UIShape shape : shapesNotContain) {
            if(shape.contains(point))
                return false;
        }
        return true;
    }
}
