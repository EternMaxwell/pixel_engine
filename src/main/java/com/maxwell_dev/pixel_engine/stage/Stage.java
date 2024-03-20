package com.maxwell_dev.pixel_engine.stage;

import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class Stage<T extends Renderer> {
    public abstract void init();
    public abstract void input();
    public abstract void update();
    public abstract void render(T renderer);
    public abstract void dispose();
}
