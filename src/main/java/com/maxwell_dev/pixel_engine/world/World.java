package com.maxwell_dev.pixel_engine.world;

import com.maxwell_dev.pixel_engine.render.Renderer;

public abstract class World <T extends Renderer>{
    public abstract void init();
    public abstract void update();
    public abstract void render(T renderer);
    public abstract void destroy();
}
