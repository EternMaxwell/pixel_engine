package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_2D_Multisample_Array extends Texture {
    /**
     * create a new texture 2d multi sample array obj
     */
    public Texture_2D_Multisample_Array() {
        super(GL_TEXTURE_2D_MULTISAMPLE_ARRAY);
    }

    /**
     * get the texture image obj
     * @return the texture image obj
     */
    public TextureImage3DMultisample textureImage() {
        return textureImage3DMultisample();
    }
}
