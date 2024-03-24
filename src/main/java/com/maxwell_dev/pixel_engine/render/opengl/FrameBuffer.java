package com.maxwell_dev.pixel_engine.render.opengl;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public class FrameBuffer {
    public final int id;
    private final Map<Integer,Integer> colorAttachments;
    private int depthAttachment;
    private boolean hasDepth;

    public FrameBuffer() {
        id = glGenFramebuffers();
        colorAttachments = new HashMap<>();
        hasDepth = false;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void bindAsRead() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
    }

    public void bindAsDraw() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int attachColor(int attachment, int texture) {
        int old = colorAttachments.get(attachment);
        colorAttachments.put(attachment, texture);
        glNamedFramebufferTexture(id, GL_COLOR_ATTACHMENT0 + attachment, texture, 0);
        return old;
    }

    public void attachDepth(int texture) {
        depthAttachment = texture;
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, texture, 0);
        hasDepth = true;
    }

    public int getDepthAttachment() {
        return depthAttachment;
    }

    public boolean hasDepth() {
        return hasDepth;
    }

    public int getColorAttachment(int attachment) {
        return colorAttachments.get(attachment);
    }

    public void noDrawBuffers() {
        glNamedFramebufferDrawBuffers(id, new int[]{GL_NONE});
    }

    public void drawBuffers(int[] attachments) {
        glNamedFramebufferDrawBuffers(id, attachments);
    }

    public void check() {
        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if(status != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("Framebuffer is not complete!");
        }
    }

    public void dispose() {
        glDeleteFramebuffers(id);
    }
}
