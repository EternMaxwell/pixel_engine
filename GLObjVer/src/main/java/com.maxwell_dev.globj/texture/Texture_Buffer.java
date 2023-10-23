package com.maxwell_dev.globj.texture;

import com.maxwell_dev.globj.Texture;
import com.maxwell_dev.globj.Buffer;

import static org.lwjgl.opengl.GL45.*;


public class Texture_Buffer extends Texture {
    /**
     * create a new texture buffer obj
     */
    public Texture_Buffer() {
        super(GL_TEXTURE_BUFFER);
    }

    /**
     * attach a buffer object's data store to a buffer texture object
     *
     * @param internalFormat the internal format of the data in the store belonging to buffer
     * @param buffer         the name of the buffer object whose storage to attach to the active buffer texture
     */
    public void textureBuffer(int internalFormat, Buffer buffer) {
        glTextureBuffer(id, internalFormat, buffer.id());
    }

    /**
     * attach a range of a buffer object's data store to a buffer texture object
     *
     * @param internalFormat the internal format of the data in the store belonging to buffer
     * @param buffer         the buffer object whose storage to attach to the active buffer texture
     * @param offset         the offset of the start of the range of the buffer's data store to attach
     * @param size           the size of the range of the buffer's data store to attach
     */
    public void textureBufferRange(int internalFormat, Buffer buffer, int offset, long size) {
        glTextureBufferRange(id, internalFormat, buffer.id(), offset, size);
    }
}
