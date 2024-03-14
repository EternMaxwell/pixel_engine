package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

import java.util.Map;

public abstract class UIManager <T extends Renderer, V extends InputTool>{
    public UIPage<T, V> currentPage;
    public Map<String, UIPage<T, V>> pages;

    public abstract void init();

    public void input(V inputTool){
        currentPage.handleInput(inputTool);
    }

    public void update(){
        currentPage.update();
    }

    public void render(T renderer){
        currentPage.render(renderer);
    }
}
