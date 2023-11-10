package com.maxwell_dev.engine.ui.text;

import com.maxwell_dev.globj.texture.Texture_2D;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Text{
    private final Font font;
    private String text;
    private BufferedImage bitmap;
    private float height;
    private final Texture_2D texture;
    private final float[] color = new float[4];

    public Text(Font font, String text){
        this.font = font;
        this.text = text;
        bitmap = font.bitmapOfText(text);
        texture = new Texture_2D();
        IntBuffer buffer = MemoryUtil.memAllocInt(bitmap.getWidth()*bitmap.getHeight());
        buffer.put(bitmap.getRGB(0,0,bitmap.getWidth(),bitmap.getHeight(),null,0,bitmap.getWidth()));
        texture.textureImage().image(1, GL_RGBA32F, bitmap.getWidth(), bitmap.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
                buffer);
        color[0] = 1;
        color[1] = 1;
        color[2] = 1;
        color[3] = 1;
    }

    public Text setColor(float r, float g, float b, float a){
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
        return this;
    }

    public BufferedImage bitmap(){
        return bitmap;
    }

    public float[] color(){
        return color;
    }

    public float colorR() {
        return color[0];
    }

    public float colorG() {
        return color[1];
    }

    public float colorB() {
        return color[2];
    }

    public float colorA() {
        return color[3];
    }

    public Text setText(String text){
        if(this.text.equals(text))
            return this;
        this.text = text;
        bitmap = font.bitmapOfText(text);
        IntBuffer buffer = MemoryUtil.memAllocInt(bitmap.getWidth()*bitmap.getHeight());
        buffer.put(bitmap.getRGB(0,0,bitmap.getWidth(),bitmap.getHeight(),null,0,bitmap.getWidth()));
        texture.textureImage().image(1,GL_RGBA32F,bitmap.getWidth(),bitmap.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,buffer);
        return this;
    }

    public Text setHeight(float height) {
        this.height = height;
        return this;
    }

    public float height() {
        return height;
    }
    
    public float width(){
        return height*bitmap.getWidth()/bitmap.getHeight();
    }

    public Texture_2D texture(){
        return texture;
    }
}
