package com.maxwell_dev.pixel_engine.world;

public interface DirectState {
    void damage(float damage);
    void heat(float heat);
    void heal(float heal);
    float health();
    boolean isDead();
}
