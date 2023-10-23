package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL45.*;
import org.lwjgl.opengl.GL45;

public class Renderbuffer implements glInterface {
    private final int id;

    public Renderbuffer() {
        id = glCreateRenderbuffers();
    }

    /**
     * get the render buffer obj name
     * @return the obj name
     */
    public int id() {
        return id;
    }

    /**
     * specify the storage requirements for a renderbuffer obj's image
     * @param internalFormat the internal format to use for the render buffer obj's image. One of:
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_DEPTH_COMPONENT32F},
     * {@link GL45#GL_DEPTH32F_STENCIL8}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_RGBA32UI}</p>
     * @param width the width of the render buffer obj's image, in pixels
     * @param height the height of the render buffer obj's image, in pixels
     */
    public void storage(int internalFormat, int width, int height) {
        glNamedRenderbufferStorage(id, internalFormat, width, height);
    }

    /**
     * specify the storage requirements for a renderbuffer obj's image
     * @param samples the number of samples to be used for the render buffer obj's image
     * @param internalFormat the internal format to use for the render buffer obj's image. One of:
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_DEPTH_COMPONENT32F},
     * {@link GL45#GL_DEPTH32F_STENCIL8}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_RGBA32UI}</p>
     * @param width the width of the render buffer obj's image, in pixels
     * @param height the height of the render buffer obj's image, in pixels
     */
    public void storageMultisample(int samples, int internalFormat, int width, int height) {
        glNamedRenderbufferStorageMultisample(id, samples, internalFormat, width, height);
    }

    /**
     * delete the render buffer obj
     */
    public void delete() {
        glDeleteRenderbuffers(id);
    }
}