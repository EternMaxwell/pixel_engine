package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL46.*;
import org.lwjgl.opengl.GL46;

public class LogicOp {
    private int op;

    public LogicOp() {
        op = GL_COPY;
    }

    /**
     * set the logic operation
     * @param op the logic operation. One of:
     * <p>{@link GL46#GL_CLEAR}: Clears the destination buffer to zero.</p>
     * <p>{@link GL46#GL_SET}: Sets the destination buffer to the bitwise complement of the source buffer value.</p>
     * <p>{@link GL46#GL_COPY}: Sets the destination buffer to the source buffer value.</p>
     * <p>{@link GL46#GL_COPY_INVERTED}: Sets the destination buffer to the bitwise complement of the source buffer value.</p>
     * <p>{@link GL46#GL_NOOP}: Leaves the destination buffer unchanged.</p>
     * <p>{@link GL46#GL_INVERT}: Bitwise inverts the destination buffer.</p>
     * <p>{@link GL46#GL_AND}: Bitwise AND of source and destination.</p>
     * <p>{@link GL46#GL_NAND}: Bitwise NAND of source and destination.</p>
     * <p>{@link GL46#GL_OR}: Bitwise OR of source and destination.</p>
     * <p>{@link GL46#GL_NOR}: Bitwise NOR of source and destination.</p>
     * <p>{@link GL46#GL_XOR}: Bitwise XOR of source and destination.</p>
     * <p>{@link GL46#GL_EQUIV}: Bitwise XNOR of source and destination.</p>
     * <p>{@link GL46#GL_AND_REVERSE}: Bitwise AND of inverted source and destination.</p>
     * <p>{@link GL46#GL_AND_INVERTED}: Bitwise AND of source and inverted destination.</p>
     * <p>{@link GL46#GL_OR_REVERSE}: Bitwise OR of inverted source and destination.</p>
     * <p>{@link GL46#GL_OR_INVERTED}: Bitwise OR of source and inverted destination.</p>
     */
    public void set(int op) {
        this.op = op;
    }

    /**
     * get the logic operation
     * @return the logic operation
     */
    public int op() {
        return op;
    }
}
