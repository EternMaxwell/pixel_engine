package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

import java.util.HashMap;
import java.util.Map;

public abstract class UIWindow<T extends Renderer, V extends InputTool> extends UIComponent<T, V>{
    public float x, y, width, height;
    public Map<String, UIComponent<T, V>> components;
    public String name;
    public UIWindow(UIManager<T, V> manager, String name, float x, float y, float width, float height) {
        super(manager);
        this.name = name;
        components = new HashMap<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    @Override
    public void render(T renderer) {
        renderOwn(renderer);
        for(UIComponent<T, V> component : components.values()){
            component.render(renderer);
        }
    }

    @Override
    public void handleInput(V inputTool) {
        for(UIComponent<T, V> component : components.values()){
            component.handleInput(inputTool);
        }
        handleInputOwn(inputTool);
    }

    @Override
    public void update() {
        for(UIComponent<T, V> component : components.values()){
            component.update();
        }
        updateOwn();
    }

    public abstract void renderOwn(T renderer);
    public abstract void handleInputOwn(InputTool inputTool);
    public abstract void updateOwn();
}
