package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_2D_Multisample extends Texture {
    /**
     * create a new texture 2d multi sample obj
     */
    public Texture_2D_Multisample() {
        super(GL_TEXTURE_2D_MULTISAMPLE);
    }

    /**
     * get the texture image obj
     * @return the texture image obj
     */
    public TextureImage2DMultisample textureImage() {
        return textureImage2DMultisample();
    }
}
