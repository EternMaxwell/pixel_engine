package com.maxwell_dev.pixel_engine.world.box2d;

import java.util.Collection;

public interface Bodilizable<T extends Body, V extends Collection<T>> {
    public V toBodies();
}
