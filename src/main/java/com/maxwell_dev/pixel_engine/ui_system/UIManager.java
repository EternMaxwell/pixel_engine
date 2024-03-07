package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

import java.util.Map;

public abstract class UIManager <T extends Renderer>{
    public UIPage<T> currentPage;
    public Map<String, UIPage<T>> pages;

    public abstract void init();

    public void input(InputTool inputTool){
        currentPage.handleInput(inputTool);
    }

    public void update(){
        currentPage.update();
    }

    public void render(T renderer){
        currentPage.render(renderer);
    }
}
