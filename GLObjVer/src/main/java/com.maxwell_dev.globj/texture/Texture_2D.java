package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_2D extends Texture {

    /**
     * create a new texture 2d obj
     */
    public Texture_2D() {
        super(GL_TEXTURE_2D);
    }

    /**
     * get the texture image obj
     * @return the texture image obj
     */
    public TextureImage2D textureImage() {
        return textureImage2D();
    }

    /**
     * get the texture sub image obj
     * @return the texture sub image obj
     */
    public TextureSubImage2D textureSubImage() {
        return textureSubImage2D();
    }
}
