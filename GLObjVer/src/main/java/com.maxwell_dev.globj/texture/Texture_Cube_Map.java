package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_Cube_Map extends Texture {
    /**
     * create a new texture cube map obj
     */
    public Texture_Cube_Map() {
        super(GL_TEXTURE_CUBE_MAP);
    }

    public TextureImageCubeMap textureImage() {
        return textureImage();
    }

    public TextureSubImageCubeMap textureSubImage() {
        return textureSubImage();
    }
}
