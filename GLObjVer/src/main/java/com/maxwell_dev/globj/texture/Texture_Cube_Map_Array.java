package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;


public class Texture_Cube_Map_Array extends Texture {
    /**
     * create a new texture cube map array obj
     */
    public Texture_Cube_Map_Array() {
        super(GL_TEXTURE_CUBE_MAP_ARRAY);
    }

    public TextureImageCubeMapArray textureImage() {
        return textureImage();
    }

    public TextureSubImageCubeMapArray textureSubImage() {
        return textureSubImage();
    }
}
