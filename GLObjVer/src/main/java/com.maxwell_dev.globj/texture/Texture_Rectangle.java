package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;

import static org.lwjgl.opengl.GL45.*;

/**
 * When using rectangle samplers, all texture lookup functions automatically use non-normalized texture coordinates. This means that the values of the texture coordinates span (0..W, 0..H) across the texture, where the (W,H) refers to its dimensions in texels, rather than (0..1, 0..1). For example, the value 12.5 is the center of the 13th texel and the value 0 is the left side of the 1st texel.
 * Rectangle textures contain exactly one image; they cannot have mipmaps.
 * Also, wrapping modes for each coordinate must be either GL_CLAMP_TO_EDGE or GL_CLAMP_TO_BORDER.
 */
public class Texture_Rectangle extends Texture {
    /**
     * create a new texture rectangle obj
     */
    public Texture_Rectangle() {
        super(GL_TEXTURE_RECTANGLE);
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
