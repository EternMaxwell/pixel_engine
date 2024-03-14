package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

import java.util.Map;

public abstract class UIPage<T extends Renderer, V extends InputTool> extends UIComponent<T, V>{
    public UIPage<T, V> parent;
    public Map<String,UIPage<T, V>> children;

    public UIPage(UIManager<T, V> manager) {
        super(manager);
    }

    @Override
    public void render(T renderer) {
        renderOwn(renderer);
        for(UIComponent<T, V> component : children.values()){
            component.render(renderer);
        }
    }

    @Override
    public void handleInput(V inputTool) {
        for(UIComponent<T, V> component : children.values()){
            component.handleInput(inputTool);
        }
        handleInputOwn(inputTool);
    }

    @Override
    public void update() {
        for(UIComponent<T, V> component : children.values()){
            component.update();
        }
        updateOwn();
    }

    public abstract void renderOwn(T renderer);
    public abstract void updateOwn();
    public abstract void handleInputOwn(V inputTool);
}
