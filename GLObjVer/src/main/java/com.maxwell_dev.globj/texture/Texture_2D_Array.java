package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_2D_Array extends Texture {
    /**
     * create a new texture 2d array obj
     */
    public Texture_2D_Array() {
        super(GL_TEXTURE_2D_ARRAY);
    }

    /**
     * get the texture image obj
     * @return the texture image obj
     */
    public TextureImage3D textureImage() {
        return textureImage3D();
    }

    /**
     * get the texture sub image obj
     * @return the texture sub image obj
     */
    public TextureSubImage3D textureSubImage() {
        return textureSubImage3D();
    }
}
