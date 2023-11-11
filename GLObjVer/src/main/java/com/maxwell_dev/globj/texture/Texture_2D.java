package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;
import org.lwjgl.opengl.GL45;

import static org.lwjgl.opengl.GL45.*;


public class Texture_2D extends Texture {

    /**
     * create a new texture 2d obj
     */
    public Texture_2D() {
        super(GL_TEXTURE_2D);
    }

    /**
     * get the texture image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
     * <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
     * {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
     * {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
     * {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
     * {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
     * {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
     * {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
     * {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
     * {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
     * {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
     * {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
     * {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
     * {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
     * {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
     * {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
     * {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
     * {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param height - the height of the texture image</p>
     * <p>param border - the texture border width</p>
     * <p>param format - the texel data format. One of:</p>
     * <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
     * {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
     * {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
     * {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
     * {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
     * {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
     * {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
     * {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
     * {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
     * {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
     * {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
     * {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
     * {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
     * {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
     * {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
     * {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
     * {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param pixels - the texel data</p>
     *
     * @return the texture image object
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
