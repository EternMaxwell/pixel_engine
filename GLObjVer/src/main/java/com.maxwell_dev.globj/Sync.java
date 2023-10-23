package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL46.*;
import org.lwjgl.opengl.GL46;

public class Sync {
    private long id;
    private int flags;
    private long timeout;

    /**
     * create a sync
     * @param flags the flags of the sync. The only available flag is:
     * <p>{@link GL46#GL_SYNC_FLUSH_COMMANDS_BIT}</p>
     * @param timeout the timeout of the sync in nanoseconds
     */
    public Sync(int flags, long timeout) {
        this.flags = flags;
        this.timeout = timeout;
        id = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
    }

    /**
     * let the GL server to wait for the sync to complete
     * <p>What glWaitSync does is prevent the driver from adding any commands to the GPU's command queue until this sync object is signaled. This function does not halt the CPU.</p>
     */
    public void waitSync() {
        glWaitSync(id, flags, timeout);
    }

    /**
     * let the current thread to wait for the sync to complete
     * <p>What glClientWaitSync does is prevent the CPU from executing any commands until this sync object is signaled. This function does not halt the GPU.</p>
     * @return the status of the sync. One of:
     * <p>{@link GL46#GL_ALREADY_SIGNALED}: the sync object was signaled at the time that glClientWaitSync was called.</p>
     * <p>{@link GL46#GL_TIMEOUT_EXPIRED}: the timeout period expired before the sync object was signaled.</p>
     * <p>{@link GL46#GL_CONDITION_SATISFIED}: the sync object was signaled before the timeout period expired.</p>
     * <p>{@link GL46#GL_WAIT_FAILED}: an error occurred. Additionally, an OpenGL Error will be generated.</p>
     */
    public int clientWaitSync() {
        return glClientWaitSync(id, flags, timeout);
    }

    /**
     * get the id of the sync
     * @return the id of the sync
     */
    public long id() {
        return id;
    }

    /**
     * get the flags of the sync
     */
    public void delete() {
        glDeleteSync(id);
    }
}
