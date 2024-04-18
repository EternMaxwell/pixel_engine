package com.maxwell_dev.pixel_engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {
    private Matrix4f projectionMatrix;
    private float scale;
    private float angle;
    private float x;
    private float y;

    public Camera() {
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void position(Vector2f position) {
    }

    public void position(float x, float y) {
    }

    public void rotate(float angle){
        this.angle += angle;
    }

    public void move(Vector2f position) {
        x += position.x;
        y += position.y;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void reOrigin(Vector2f newOrigin) {
        x -= newOrigin.x;
        y -= newOrigin.y;
    }

    public void reOrigin(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public Matrix4f cameraMatrixMix(float x, float y, float angle, Matrix4f dest, float mix) {
        this.x = x * mix + this.x * (1 - mix);
        this.y = y * mix + this.y * (1 - mix);
        this.angle = angle * mix + this.angle * (1 - mix);
        return cameraMatrix(dest);
    }

    public Matrix4f cameraMatrix(Matrix4f dest) {
        return projectionMatrix.mul(new Matrix4f().translate(-x, -y, 0).rotateLocalZ(-angle).scaleLocal(1/scale,dest), dest);
    }

    public Matrix4f cameraMatrix(Matrix4f customViewMatrix, Matrix4f dest) {
        return projectionMatrix.mul(customViewMatrix, dest);
    }

    public Matrix4f cameraMatrix(float x, float y, float angle, float scale, Matrix4f dest) {
        return projectionMatrix.mul(new Matrix4f().translate(-x, -y, 0).rotateLocalZ(-angle).scaleLocal(1/scale,dest), dest);
    }

    public Matrix4f viewMatrix(Matrix4f dest) {
        return new Matrix4f().translate(-x, -y, 0).rotateLocalZ(-angle, dest);
    }

    public Matrix4f viewMatrix(float x, float y, float angle, float scale, Matrix4f dest) {
        return new Matrix4f().translate(-x, -y, 0).rotateLocalZ(-angle).scaleLocal(1/scale,dest);
    }

    public Matrix4f viewMatrixMix(float x, float y, float angle, Matrix4f dest, float mix) {
        this.x = x * mix + this.x * (1 - mix);
        this.y = y * mix + this.y * (1 - mix);
        this.angle = angle * mix + this.angle * (1 - mix);
        return new Matrix4f().translate(-this.x, -this.y, 0).rotateLocalZ(-this.angle,dest);
    }

    public Matrix4f projectionMatrix(Matrix4f dest) {
        return projectionMatrix.scale(1/scale, dest);
    }
}
