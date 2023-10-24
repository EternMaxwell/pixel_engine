package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL45.*;

public class Buffer implements glInterface {
    private final int id;
    private long bufferSize;
    private final long baseOffset;
    private final boolean isSub;
    private final BufferData dataLoader;
    private final BufferSubData subDataLoader;
    private final CopySubData copySubData;
    private final ClearData dataClearer;
    private final ClearSubData subDataClearer;
    private final MapBuffer bufferMapper;

    public Buffer() {
        id = glCreateBuffers();
        isSub = false;
        baseOffset = 0;
        dataLoader = new BufferData();
        dataClearer = new ClearData();
        subDataLoader = new BufferSubData();
        copySubData = new CopySubData();
        subDataClearer = new ClearSubData();
        bufferMapper = new MapBuffer();
    }

    public Buffer(Buffer father, int offset, long size) {
        this(father.id, offset, size);
    }

    /**
     * don't use this in other case
     * <p>this is only used in {@link SubBuffer#SubBuffer(Buffer, long, long)}</p>
     */
    private Buffer(int id, long offset, long size) {
        this.id = id;
        isSub = true;
        baseOffset = offset;
        bufferSize = size;
        dataLoader = new BufferData();
        dataClearer = new ClearData();
        subDataLoader = new BufferSubData();
        copySubData = new CopySubData();
        subDataClearer = new ClearSubData();
        bufferMapper = new MapBuffer();
    }

    public int id() {
        return id;
    }

    public boolean isSub() {
        return isSub;
    }

    public long offset() {
        return baseOffset;
    }

    public long size() {
        return bufferSize;
    }

    public Buffer subBuffer(long offset, long length) {
        return new Buffer(id, offset, length);
    }

    public class BufferData {
        public void load(long size, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, size, usage);
        }

        public void load(java.nio.FloatBuffer data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(java.nio.DoubleBuffer data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(java.nio.ByteBuffer data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(java.nio.ShortBuffer data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(java.nio.IntBuffer data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(short[] data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(int[] data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(long[] data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(float[] data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }

        public void load(double[] data, int usage) {
            if (isSub) {
                throw new RuntimeException("Sub buffer is not allow to use buffer data funcs");
            }
            glNamedBufferData(id, data, usage);
        }
    }

    public class BufferSubData {
        public void load(long offset, java.nio.ByteBuffer data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, java.nio.ShortBuffer data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, java.nio.IntBuffer data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, java.nio.FloatBuffer data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, java.nio.DoubleBuffer data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, short[] data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, int[] data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, long[] data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, float[] data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }

        public void load(long offset, double[] data) {
            glNamedBufferSubData(id, offset + baseOffset, data);
        }
    }

    public class CopySubData {
        public void to(Buffer buffer, long readOffset, long writeOffset, long size) {
            glCopyNamedBufferSubData(id, buffer.id, readOffset + baseOffset, writeOffset + buffer.baseOffset, size);
        }

        public void from(Buffer buffer, long readOffset, long writeOffset, long size) {
            glCopyNamedBufferSubData(buffer.id, id, readOffset + buffer.baseOffset, writeOffset + baseOffset, size);
        }
    }

    public class ClearData {
        public void clear(int internalFormat, int format, int type, java.nio.ByteBuffer data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, java.nio.ShortBuffer data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, java.nio.IntBuffer data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, java.nio.FloatBuffer data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, short[] data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, int[] data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }

        public void clear(int internalFormat, int format, int type, float[] data) {
            if (isSub) {
                glClearNamedBufferSubData(id, internalFormat, baseOffset, bufferSize, format, type, data);
                return;
            }
            glClearNamedBufferData(id, internalFormat, format, type, data);
        }
    }

    public class ClearSubData {
        public void clear(int internalFormat, long offset, long size, int format, int type, java.nio.ByteBuffer data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, java.nio.ShortBuffer data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, java.nio.IntBuffer data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, java.nio.FloatBuffer data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, short[] data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, int[] data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }

        public void clear(int internalFormat, long offset, long size, int format, int type, float[] data) {
            if (offset + size > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the specified buffer range is not all inside this buffer");
            }
            glClearNamedBufferSubData(id, internalFormat, offset + baseOffset, size, format, type, data);
        }
    }

    public class MapBuffer {
        /**
         * Maps a buffer object's data store.
         * Calls GetBufferParameteriv to retrieve the buffer size and a new ByteBuffer instance is always returned.
         *
         * @param access the access policy, indicating whether it will be possible to read from, write to, or both read from and write to the buffer object's mapped data store. One of:
         *               READ_ONLY
         *               WRITE_ONLY
         *               READ_WRITE
         * @return the generated buffer
         */
        public java.nio.ByteBuffer map(int access) {
            if (isSub) {
                return glMapNamedBufferRange(id, baseOffset, bufferSize, access);
            }
            return glMapNamedBuffer(id, access);
        }

        /**
         * Maps a buffer object's data store.
         * Calls GetBufferParameteriv to retrieve the buffer size and a new ByteBuffer instance is always returned.
         *
         * @param access     the access policy, indicating whether it will be possible to read from, write to, or both read from and write to the buffer object's mapped data store. One of:
         *                   READ_ONLY
         *                   WRITE_ONLY
         *                   READ_WRITE
         * @param old_buffer map the data to this buffer
         * @return the buffer
         */
        public java.nio.ByteBuffer map(int access, java.nio.ByteBuffer old_buffer) {
            if (isSub) {
                return glMapNamedBufferRange(id, baseOffset, bufferSize, access, old_buffer);
            }
            return glMapNamedBuffer(id, access, old_buffer);
        }

        /**
         * Maps a buffer object's data store.
         * Calls GetBufferParameteriv to retrieve the buffer size and a new ByteBuffer instance is always returned.
         *
         * @param access     the access policy, indicating whether it will be possible to read from, write to, or both read from and write to the buffer object's mapped data store. One of:
         *                   READ_ONLY
         *                   WRITE_ONLY
         *                   READ_WRITE
         * @param length     the length of the buffer
         * @param old_buffer map the buffer to this buffer
         * @return the buffer
         */
        public java.nio.ByteBuffer map(int access, long length, java.nio.ByteBuffer old_buffer) {
            if (isSub) {
                return glMapBufferRange(id, baseOffset, length, access);
            }
            return glMapNamedBuffer(id, access, length, old_buffer);
        }

        /**
         * Maps a section of a buffer object's data store.
         *
         * @param offset the starting offset within the buffer of the range to be mapped
         * @param length the length of the range to be mapped
         * @param access a combination of access flags indicating the desired access to the range. One or more of:
         *               MAP_READ_BIT
         *               MAP_WRITE_BIT
         *               MAP_INVALIDATE_RANGE_BIT
         *               MAP_INVALIDATE_BUFFER_BIT
         *               MAP_FLUSH_EXPLICIT_BIT
         *               MAP_UNSYNCHRONIZED_BIT
         * @return the buffer
         */
        public java.nio.ByteBuffer range(long offset, long length, int access) {
            if (offset + length > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the required range is not all included in this buffer");
            }
            return glMapNamedBufferRange(id, offset + baseOffset, length, access);
        }

        /**
         * Maps a section of a buffer object's data store.
         *
         * @param offset     the starting offset within the buffer of the range to be mapped
         * @param length     the length of the range to be mapped
         * @param access     a combination of access flags indicating the desired access to the range. One or more of:
         *                   MAP_READ_BIT
         *                   MAP_WRITE_BIT
         *                   MAP_INVALIDATE_RANGE_BIT
         *                   MAP_INVALIDATE_BUFFER_BIT
         *                   MAP_FLUSH_EXPLICIT_BIT
         *                   MAP_UNSYNCHRONIZED_BIT
         * @param old_buffer map the buffer to this buffer
         * @return the buffer
         */
        public java.nio.ByteBuffer range(long offset, long length, int access, java.nio.ByteBuffer old_buffer) {
            if (offset + length > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the required range is not all included in this buffer");
            }
            return glMapNamedBufferRange(id, offset + baseOffset, length, access, old_buffer);
        }

        /**
         * Relinquishes the mapping of a buffer object and invalidates the pointer to its data store.
         * <p>Returns TRUE unless data values in the buffer’s data store have become corrupted during the period that the buffer was mapped. Such corruption can be the result of a screen resolution change or other window system-dependent event that causes system heaps such as those for high-performance graphics memory to be discarded. GL implementations must guarantee that such corruption can occur only during the periods that a buffer’s data store is mapped. If such corruption has occurred, UnmapBuffer returns FALSE, and the contents of the buffer’s data store become undefined.
         */
        public boolean unmap() {
            return glUnmapNamedBuffer(id);
        }

        /**
         * Indicates modifications to a range of a mapped buffer.
         *
         * @param offset the start of the buffer subrange, in basic machine units
         * @param length the length of the buffer subrange, in basic machine units
         */
        public void flushRange(long offset, long length) {
            if (offset + length > bufferSize) {
                throw new ArrayIndexOutOfBoundsException("the required range is not all included in this buffer");
            }
            glFlushMappedNamedBufferRange(id, offset + baseOffset, length);
        }
    }

    /**
     * load the buffer with the data or with empty value
     *
     * <p>param size - the size of the buffer</p>
     * <p>param data - the data to be load</p>
     * <p>param usage - how the data is used</p>
     *
     * @return the data loader
     */
    public BufferData bufferData() {
        return dataLoader;
    }

    /**
     * load a part of the buffer with the data
     *
     * <p>param offset - Specifies the offset into the buffer object's data store where data replacement will begin, measured in bytes</p>
     * <p>param data - the data to be copied</p>
     *
     * @return the sub data loader
     */
    public BufferSubData bufferSubData() {
        return subDataLoader;
    }

    /**
     * copy part of one buffer obj's data to the data of another buffer obj
     * 
     * <p>param buffer - the buffer to copy to or from </p>
     * <p>param readOffset - the offset of the first data to copy in the read buffer </p>
     * <p>param writeOffset - the offset of the data store to copy data to in the write buffer </p>
     * <p>param size - the size of the copied data
     * @return the sub data copy handler
     */
    public CopySubData copySubData() {
        return copySubData;
    }

    /**
     * fill a buffer object's data store with a fixed value
     *
     * <p>param internalFormat the internal format with which the data will be stored in the buffer object</p>
     * <p>param format         the format of the data in memory addressed by data. One of:
     *                       RED	GREEN	BLUE	ALPHA	RG	RGB	RGBA	BGR
     *                       BGRA	RED_INTEGER	GREEN_INTEGER	BLUE_INTEGER	ALPHA_INTEGER	RG_INTEGER	RGB_INTEGER	RGBA_INTEGER
     *                       BGR_INTEGER	BGRA_INTEGER	STENCIL_INDEX	DEPTH_COMPONENT	DEPTH_STENCIL	LUMINANCE	LUMINANCE_ALPHA
     * </p><p>param type           the type of the data in memory addressed by data. One of:
     *                       UNSIGNED_BYTE	BYTE	UNSIGNED_SHORT	SHORT
     *                       UNSIGNED_INT	INT	HALF_FLOAT	FLOAT
     *                       UNSIGNED_BYTE_3_3_2	UNSIGNED_BYTE_2_3_3_REV	UNSIGNED_SHORT_5_6_5	UNSIGNED_SHORT_5_6_5_REV
     *                       UNSIGNED_SHORT_4_4_4_4	UNSIGNED_SHORT_4_4_4_4_REV	UNSIGNED_SHORT_5_5_5_1	UNSIGNED_SHORT_1_5_5_5_REV
     *                       UNSIGNED_INT_8_8_8_8	UNSIGNED_INT_8_8_8_8_REV	UNSIGNED_INT_10_10_10_2	UNSIGNED_INT_2_10_10_10_REV
     *                       UNSIGNED_INT_24_8	UNSIGNED_INT_10F_11F_11F_REV	UNSIGNED_INT_5_9_9_9_REV	FLOAT_32_UNSIGNED_INT_24_8_REV
     *                       BITMAP
     * </p><p>param data           the buffer containing the data to be used as the source of the constant fill value. The elements of data are converted by the GL into the format specified by internalformat, and then used to fill the specified range of the destination buffer. If data is NULL, then it is ignored and the sub-range of the buffer is filled with zeros.</p>
     * @return the data clear handler
     */
    public ClearData clearData() {
        return dataClearer;
    }

    /**
     * fill a part of  buffer object's data store with a fixed value
     *
     * <p>param internalFormat the internal format with which the data will be stored in the buffer object
     * <p>param offset         the offset of the first byte to fill
     * <p>param size           the size of the sub region
     * <p>param format         the format of the data in memory addressed by data. One of:
     *                       RED	GREEN	BLUE	ALPHA	RG	RGB	RGBA	BGR
     *                       BGRA	RED_INTEGER	GREEN_INTEGER	BLUE_INTEGER	ALPHA_INTEGER	RG_INTEGER	RGB_INTEGER	RGBA_INTEGER
     *                       BGR_INTEGER	BGRA_INTEGER	STENCIL_INDEX	DEPTH_COMPONENT	DEPTH_STENCIL	LUMINANCE	LUMINANCE_ALPHA
     * <p>param type           the type of the data in memory addressed by data. One of:
     *                       UNSIGNED_BYTE	BYTE	UNSIGNED_SHORT	SHORT
     *                       UNSIGNED_INT	INT	HALF_FLOAT	FLOAT
     *                       UNSIGNED_BYTE_3_3_2	UNSIGNED_BYTE_2_3_3_REV	UNSIGNED_SHORT_5_6_5	UNSIGNED_SHORT_5_6_5_REV
     *                       UNSIGNED_SHORT_4_4_4_4	UNSIGNED_SHORT_4_4_4_4_REV	UNSIGNED_SHORT_5_5_5_1	UNSIGNED_SHORT_1_5_5_5_REV
     *                       UNSIGNED_INT_8_8_8_8	UNSIGNED_INT_8_8_8_8_REV	UNSIGNED_INT_10_10_10_2	UNSIGNED_INT_2_10_10_10_REV
     *                       UNSIGNED_INT_24_8	UNSIGNED_INT_10F_11F_11F_REV	UNSIGNED_INT_5_9_9_9_REV	FLOAT_32_UNSIGNED_INT_24_8_REV
     *                       BITMAP
     * <p>param data           the buffer containing the data to be used as the source of the constant fill value. The elements of data are converted by the GL into the format specified by internalformat, and then used to fill the specified range of the destination buffer. If data is NULL, then it is ignored and the sub-range of the buffer is filled with zeros.
     * @return the sub data clear handler
     */
    public ClearSubData clearSubData() {
        return subDataClearer;
    }

    /**
     * get the buffer mapping handler
     * @return the buffer mapping handler
     */
    public MapBuffer mapBuffer() {
        return bufferMapper;
    }

    public void delete() {
        glDeleteBuffers(id);
    }
}
