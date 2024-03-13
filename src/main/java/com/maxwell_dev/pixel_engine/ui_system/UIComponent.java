package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class UIComponent <T extends Renderer>{
    public UIManager<T> manager;
    public UIComponent(UIManager<T> manager){
        this.manager = manager;
    }
    public abstract void render(T renderer);
    public abstract void handleInput(InputTool inputTool);
    public abstract void update();
}
