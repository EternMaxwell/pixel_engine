package com.maxwell_dev.engine.render;

public abstract class RenderStream extends Stream {
    @Override
    public void pass() {

    }

    @Override
    public void useStream() {

    }

    public abstract boolean loadEntity(Visible entity);

    public abstract void removeEntity(Visible entity);

    public abstract boolean drawEntity(Visible entity);

    public abstract DrawPipeline pipeline();

    public abstract com.maxwell_dev.globj.Buffer vertexBuffer();
}