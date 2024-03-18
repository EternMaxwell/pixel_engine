package com.maxwell_dev.pixel_engine.core;

public abstract class Application {
    public void start(){
        init();
        while(running()){
            loop();
        }
        destroy();
    }

    public abstract void init();
    public void loop(){
        input();
        update();
        render();
    }
    public abstract void input();
    public abstract void update();
    public abstract void render();
    public abstract void destroy();
    public abstract boolean running();
    public abstract void pause();
}
