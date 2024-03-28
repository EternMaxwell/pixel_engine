package com.maxwell_dev.pixel_engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private float scale;

    public Camera() {
        viewMatrix = new Matrix4f().identity();
        projectionMatrix = new Matrix4f().identity();
        scale = 1;
    }

    public void projectionOrtho(float left, float right, float bottom, float top, float near, float far) {
        projectionMatrix.identity();
        projectionMatrix.ortho(left, right, bottom, top, near, far);
    }

    public void scale(float scale) {
        this.scale *= scale;
    }

    public void position(Vector2f position) {
        viewMatrix.identity();
        viewMatrix.translate(-position.x, -position.y, 0);
    }

    public void move(Vector2f position) {
        viewMatrix.translate(-position.x, -position.y, 0);
    }

    public void move(float x, float y) {
        viewMatrix.translate(-x, -y, 0);
    }

    public void reOrigin(Vector2f newOrigin) {
        viewMatrix.translate(newOrigin.x, newOrigin.y, 0);
    }

    public void reOrigin(float x, float y) {
        viewMatrix.translate(x, y, 0);
    }

    public Matrix4f cameraMatrix(Matrix4f dest) {
        return projectionMatrix.mul(viewMatrix.scaleLocal(scale,dest), dest);
    }
}
