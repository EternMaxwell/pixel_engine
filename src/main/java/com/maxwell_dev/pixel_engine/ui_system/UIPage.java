package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.render.Renderer;

import java.util.Map;

public abstract class UIPage<T extends Renderer> extends UIComponent<T>{
    public UIPage parent;
    public Map<String,UIPage> children;

    public UIPage(UIManager manager) {
        super(manager);
    }
}
