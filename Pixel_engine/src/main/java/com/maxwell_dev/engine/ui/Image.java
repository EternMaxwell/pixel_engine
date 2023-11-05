package com.maxwell_dev.engine.ui;

import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.globj.texture.Texture_2D;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Image implements Visible {
    protected Texture_2D texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private float[] color;

    public Image(BufferedImage image){
        texture = new Texture_2D();
        x = 0;
        y = 0;
        width = image.getWidth();
        height = image.getHeight();
        color = new float[]{1,1,1,1};
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setColor(float r, float g, float b, float a){
        color = new float[]{r,g,b,a};
    }

    public void setHeight(float height){
        this.height = height;
    }


    @Override
    public ByteBuffer indexBuffer() {
        return null;
    }

    @Override
    public ByteBuffer vertexBuffer() {
        return null;
    }
}
