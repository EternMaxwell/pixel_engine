package com.maxwell_dev.engine.ui.text;

import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.engine.ui.Image;
import com.maxwell_dev.globj.texture.Texture_2D;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Text{
    private final Font font;
    private String text;
    private BufferedImage bitmap;
    private final Texture_2D texture;

    public Text(Font font, String text){
        this.font = font;
        this.text = text;
        bitmap = font.bitmapOfText(text);
        texture = new Texture_2D();
        IntBuffer buffer = MemoryUtil.memAllocInt(bitmap.getWidth()*bitmap.getHeight());
        buffer.put(bitmap.getRGB(0,0,bitmap.getWidth(),bitmap.getHeight(),null,0,bitmap.getWidth()));
        texture.textureImage().image(1,GL_RGBA32F,bitmap.getWidth(),bitmap.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,buffer);
    }

    public void setText(String text){
        if(this.text.equals(text))
            return;
        this.text = text;
        bitmap = font.bitmapOfText(text);
        IntBuffer buffer = MemoryUtil.memAllocInt(bitmap.getWidth()*bitmap.getHeight());
        buffer.put(bitmap.getRGB(0,0,bitmap.getWidth(),bitmap.getHeight(),null,0,bitmap.getWidth()));
        texture.textureImage().image(1,GL_RGBA32F,bitmap.getWidth(),bitmap.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,buffer);
    }

    public Texture_2D texture(){
        return texture;
    }
}
