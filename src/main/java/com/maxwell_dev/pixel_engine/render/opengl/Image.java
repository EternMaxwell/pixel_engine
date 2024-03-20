package com.maxwell_dev.pixel_engine.render.opengl;

import java.nio.ByteBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL46.*;

public class Image {
    public final int width;
    public final int height;
    public final int texture;
    public final int sampler;
    public Image(String path, int level){
        texture = glGenTextures();
        sampler = glGenSamplers();
        glBindTexture(GL_TEXTURE_2D, texture);
        stbi_set_flip_vertically_on_load(true);
        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];
        ByteBuffer data = stbi_load(path, w, h, c, 4);
        if(data != null) {
            width = w[0];
            height = h[0];
            glTexImage2D(GL_TEXTURE_2D, level, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            stbi_image_free(data);
        }else {
            width = 0;
            height = 0;
            glDeleteTextures(texture);
            glDeleteSamplers(sampler);
        }
    }

    public Image(int texture){
        this.texture = texture;
        int[] w = new int[1];
        int[] h = new int[1];
        glGetTextureLevelParameteriv(texture, 0, GL_TEXTURE_WIDTH, w);
        glGetTextureLevelParameteriv(texture, 0, GL_TEXTURE_HEIGHT, h);
        width = w[0];
        height = h[0];
        sampler = glGenSamplers();
    }

    public void samplerParameteri(int pname, int param){
        glSamplerParameteri(sampler, pname, param);
    }

    public void bind(int unit){
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindSampler(unit, sampler);
    }

    public void unbind(int unit){
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindSampler(unit, 0);
    }

    public void dispose(){
        glDeleteTextures(texture);
        glDeleteSamplers(sampler);
    }

    public Image(String path){
        this(path, 0);
    }
}
