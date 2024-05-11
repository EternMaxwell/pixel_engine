package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public abstract class UIManager <T extends Renderer, V extends InputTool>{
    public UIPage<T, V> currentPage;
    public Map<String, UIPage<T, V>> pages;
    /*
     * The projection matrix for the UI, this is used to convert the UI from ui space to screen space(to (-ratio,ratio,-1,1))
     */
    private final Matrix4f projectionMatrix;
    private final Matrix4f invProjectionMatrix;
    private final boolean projectionEnabled;

    public UIManager(){
        projectionMatrix = new Matrix4f().identity();
        invProjectionMatrix = new Matrix4f().identity();
        pages = new HashMap<>();
        projectionEnabled = false;
        init();
    }

    public UIManager(Matrix4f projectionMatrix){
        this.projectionMatrix = projectionMatrix;
        invProjectionMatrix = new Matrix4f();
        projectionMatrix.invert(invProjectionMatrix);
        pages = new HashMap<>();
        projectionEnabled = true;
        init();
    }

    public abstract void init();

    public void input(V inputTool){
        if (currentPage == null) return;
        currentPage.handleInput(inputTool);
    }

    public void update(){
        if (currentPage == null) return;
        currentPage.update();
    }

    public void render(T renderer){
        if (currentPage == null) return;
        currentPage.render(renderer);
    }

    public void setCurrentPage(String name){
        currentPage = pages.get(name);
    }

    /**
     * get the projection matrix for the UI, ui space to screen space
     * @return the projection matrix
     */
    public Matrix4f projectionMatrix(){
        return projectionMatrix;
    }

    /**
     * get the inverse of the projection matrix for the UI, screen space to ui space
     * @return the inverse of the projection matrix
     */
    public Matrix4f invProjectionMatrix(){
        return invProjectionMatrix;
    }

    public boolean isProjectionEnabled(){
        return projectionEnabled;
    }
}
