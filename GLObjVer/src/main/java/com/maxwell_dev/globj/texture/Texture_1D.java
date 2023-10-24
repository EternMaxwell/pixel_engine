package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;

public class Texture_1D extends Texture {
    /**
     * create a new texture 1d obj
     */
    public Texture_1D() {
        super(GL_TEXTURE_1D);
    }

    /**
     * get the texture image obj
     * @return the texture image obj
     */
    public TextureImage1D textureImage() {
        return textureImage1D();
    }

    /**
     * get the texture sub image obj
     * @return the texture sub image obj
     */
    public TextureSubImage1D textureSubImage() {
        return textureSubImage1D();
    }
}
