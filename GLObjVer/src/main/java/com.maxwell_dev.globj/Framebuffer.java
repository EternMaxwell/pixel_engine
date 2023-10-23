package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL45;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL45.*;

import java.util.LinkedList;

public class Framebuffer implements glInterface {
    private final int id;
    private final LinkedList<TextureBinding> colorAttachments;
    private final TextureBinding depthAttachment, stencilAttachment, depthStencilAttachment;
    private final Blit blit;

    public class TextureBinding {
        private Texture texture;
        private final int attachment;

        public TextureBinding(int attachment) {
            this.attachment = attachment;
        }
        
        /**
         * bind the texture to the frame buffer
         * @param texture the texture to bind
         * @param level the mipmap level to bind
         */
        public void bind(Texture texture, int level) {
            this.texture = texture;
            if (texture == null) {

                glNamedFramebufferTexture(id, attachment, 0, level);
                return;
            }
            glNamedFramebufferTexture(id, attachment, texture.id, level);
        }
        
        /**
         * bind the texture to the frame buffer
         * @param texture the texture to bind
         * @param level the mipmap level to bind 
         * @param layer  
         */
        public void bindLayer(Texture texture, int level, int layer) {
            this.texture = texture;
            if (texture == null) {
                glNamedFramebufferTextureLayer(id, attachment, 0, level, layer);
                return;
            }
            glNamedFramebufferTextureLayer(id, attachment, texture.id, level, layer);
        }

        /**
         * get the texture bound to this frame buffer
         * @return the texture bound to this frame buffer
         */
        public Texture get() {
            return texture;
        }
    }

    public class Blit {
        /**
         * blit the frame buffer to the destination frame buffer
         * 
         * @param dest   the destination frame buffer
         * @param srcX0  the source x0
         * @param srcY0  the source y0
         * @param srcX1  the source x1
         * @param srcY1  the source y1
         * @param dstX0  the destination x0
         * @param dstY0  the destination y0
         * @param dstX1  the destination x1
         * @param dstY1  the destination y1
         * @param mask   The bitwise OR of the flags indicating which buffers are to be
         *               copied.
         *               <p>
         *               The allowed flags are {@link GL45#GL_COLOR_BUFFER_BIT},
         *               {@link GL45#GL_DEPTH_BUFFER_BIT},
         *               {@link GL45#GL_STENCIL_BUFFER_BIT}
         *               </p>
         * @param filter Specifies the interpolation to be applied if the image is
         *               stretched. <p>Must be one of:</p>
         *              <p>{@link GL45#GL_NEAREST} {@link GL45#GL_LINEAR}</p>
         */
        public void to(Framebuffer dest, int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1,
                int dstY1, int mask, int filter) {
            glBlitNamedFramebuffer(id, dest.id, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        }

        /**
         * blit the source frame buffer to this frame buffer
         * 
         * @param src    the source frame buffer
         * @param srcX0  the source x0
         * @param srcY0  the source y0
         * @param srcX1  the source x1
         * @param srcY1  the source y1
         * @param dstX0  the destination x0
         * @param dstY0  the destination y0
         * @param dstX1  the destination x1
         * @param dstY1  the destination y1
         * @param mask   The bitwise OR of the flags indicating which buffers are to be
         *               copied.
         *               <p>
         *               The allowed flags are {@link GL45#GL_COLOR_BUFFER_BIT},
         *               {@link GL45#GL_DEPTH_BUFFER_BIT},
         *               {@link GL45#GL_STENCIL_BUFFER_BIT}
         *               </p>
         * @param filter Specifies the interpolation to be applied if the image is
         *               stretched. <p>Must be one of:</p>
         *              <p>{@link GL45#GL_NEAREST} {@link GL45#GL_LINEAR}</p>
         */
        public void from(Framebuffer src, int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1,
                int dstY1, int mask, int filter) {
            glBlitNamedFramebuffer(src.id, id, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
        }
    }

    /**
     * create a new frame buffer obj
     */
    public Framebuffer() {
        id = glCreateFramebuffers();

        colorAttachments = new LinkedList<TextureBinding>();
        depthAttachment = new TextureBinding(GL_DEPTH_ATTACHMENT);
        stencilAttachment = new TextureBinding(GL_STENCIL_ATTACHMENT);
        depthStencilAttachment = new TextureBinding(GL_DEPTH_STENCIL_ATTACHMENT);

        blit = new Blit();
    }

    /**
     * get the frame buffer obj
     *
     * @return the obj name or id
     */
    public int id() {
        return id;
    }

    /**
     * get the color attachment at the specified index
     * @param index the index of the color attachment
     * @return the color attachment at the specified index
     */
    public TextureBinding colorAttachment(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Index must be greater than or equal to 0");
        if (index >= colorAttachments.size())
            for (int i = colorAttachments.size(); i <= index; i++)
                colorAttachments.add(new TextureBinding(GL_COLOR_ATTACHMENT0 + i));
        return colorAttachments.get(index);
    }

    /**
     * get the depth attachment
     * @return the depth attachment
     */
    public TextureBinding depthAttachment() {
        return depthAttachment;
    }

    /**
     * get the stencil attachment
     * @return the stencil attachment
     */
    public TextureBinding stencilAttachment() {
        return stencilAttachment;
    }

    /**
     * get the depth stencil attachment
     * @return the depth stencil attachment
     */
    public TextureBinding depthStencilAttachment() {
        return depthStencilAttachment;
    }

    /**
     * get the blit obj
     * @return the blit obj
     */
    public Blit blit() {
        return blit;
    }

    /**
     * delete the frame buffer obj
     */
    public void delete() {
        glDeleteFramebuffers(id);
    }
}