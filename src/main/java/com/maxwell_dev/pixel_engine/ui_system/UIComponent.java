package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class UIComponent <T extends Renderer, V extends InputTool>{
    public UIManager<T, V> manager;
    public UIComponent(UIManager<T, V> manager){
        this.manager = manager;
    }
    public abstract void init();
    public abstract void render(T renderer);
    public abstract void handleInput(V inputTool);
    public abstract void update();
}
