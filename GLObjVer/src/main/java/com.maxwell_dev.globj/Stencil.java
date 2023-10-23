package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL46;

public class Stencil {
    private Face front;
    private Face back;

    public class Face {
        private FuncSeperate func;
        private OpSeperate op;

        public void setFunc(FuncSeperate func) {
            this.func = func;
        }

        public void setOp(OpSeperate op) {
            this.op = op;
        }

        public FuncSeperate func() {
            return func;
        }

        public OpSeperate op() {
            return op;
        }
    }

    public class FuncSeperate {
        private int func;
        private int ref;
        private int mask;

        public FuncSeperate() {
            func = GL46.GL_ALWAYS;
            ref = 0;
            mask = 0xFFFFFFFF;
        }

        /**
         * set the stencil function, reference value, and stencil mask for both front and back stencil states
         * <p>the equation is fs&mask func ds&mask</p>
         * <p>where fs is the ref and ds comes from the stencil buffer</p>
         * @param func the stencil function. One of:
         * <p>{@link GL46#GL_NEVER}, {@link GL46#GL_LESS}, {@link GL46#GL_LEQUAL},
         * {@link GL46#GL_GREATER}, {@link GL46#GL_GEQUAL}, {@link GL46#GL_EQUAL},
         * {@link GL46#GL_NOTEQUAL}, {@link GL46#GL_ALWAYS}</p>
         * <p>The initial value is {@link GL46#GL_ALWAYS}.</p>
         * <p>they means the following:</p>
         * <p>GL_NEVER: Never passes.</p>
         * <p>GL_LESS: Passes if ( ref & mask ) < ( stencil & mask ).</p>
         * <p>GL_LEQUAL: Passes if ( ref & mask ) <= ( stencil & mask ).</p>
         * <p>GL_GREATER: Passes if ( ref & mask ) > ( stencil & mask ).</p>
         * <p>GL_GEQUAL: Passes if ( ref & mask ) >= ( stencil & mask ).</p>
         * <p>GL_EQUAL: Passes if ( ref & mask ) = ( stencil & mask ).</p>
         * <p>GL_NOTEQUAL: Passes if ( ref & mask ) != ( stencil & mask ).</p>
         * <p>GL_ALWAYS: Always passes.</p>
         * @param ref the reference value for the stencil test. The value is clamped to the range [0, 2n - 1], where n is the number of bitplanes in the stencil buffer.
         * @param mask the mask that is ANDed with both the reference value and the stored stencil value when the test is done. The initial value is all 1's.
         */
        public FuncSeperate set(int func, int ref, int mask) {
            this.func = func;
            this.ref = ref;
            this.mask = mask;
            return this;
        }

        /**
         * get the stencil function
         * @return  the stencil function. One of:
         */
        public int func() {
            return func;
        }

        /**
         * get the reference value for the stencil test
         * @return the reference value for the stencil test
         */
        public int ref() {
            return ref;
        }

        /**
         * get the mask that is ANDed with both the reference value and the stored stencil value when the test is done
         * @return the mask that is ANDed with both the reference value and the stored stencil value when the test is done
         */
        public int mask() {
            return mask;
        }
    }

    public class OpSeperate {
        private int sfail;
        private int dpfail;
        private int dppass;

        /**
         * set the stencil test actions for front and back-facing polygons
         * @param sfail the action to take when the stencil test fails.
         * @param dpfail the action to take when the stencil test passes, but the depth test fails. 
         * sfail and dpfail accept the following values:
         * <p>{@link GL46#GL_KEEP}, {@link GL46#GL_ZERO}, {@link GL46#GL_REPLACE},
         * {@link GL46#GL_INCR}, {@link GL46#GL_INCR_WRAP}, {@link GL46#GL_DECR},
         * {@link GL46#GL_DECR_WRAP}, {@link GL46#GL_INVERT}</p>
         * <p>they means the following:</p>
         * <p>GL_KEEP: Keeps the current value.</p>
         * <p>GL_ZERO: Sets the stencil buffer value to 0.</p>
         * <p>GL_REPLACE: Sets the stencil buffer value to ref, as specified by glStencilFunc.</p>
         * <p>GL_INCR: Increments the current stencil buffer value. Clamps to the maximum representable unsigned value.</p>
         * <p>GL_INCR_WRAP: Increments the current stencil buffer value. Wraps stencil buffer value to zero when incrementing the maximum representable unsigned value.</p>
         * <p>GL_DECR: Decrements the current stencil buffer value. Clamps to 0.</p>
         * <p>GL_DECR_WRAP: Decrements the current stencil buffer value. Wraps stencil buffer value to the maximum representable unsigned value when decrementing a stencil buffer value of zero.</p>
         * <p>GL_INVERT: Bitwise inverts the current stencil buffer value.</p>
         * @param dppass the action to take when both the stencil test and the depth test pass, or when the stencil test passes and either there is no depth buffer or depth testing is not enabled. One of:
         * they accept the following values:
         * <p>{@link GL46#GL_KEEP}, {@link GL46#GL_ZERO}, {@link GL46#GL_REPLACE},
         * {@link GL46#GL_INCR}, {@link GL46#GL_INCR_WRAP}, {@link GL46#GL_DECR},
         * {@link GL46#GL_DECR_WRAP}, {@link GL46#GL_INVERT}</p>
         * <p>they means the following:</p>
         * <p>GL_KEEP: Keeps the current value.</p>
         * <p>GL_ZERO: Sets the stencil buffer value to 0.</p>
         * <p>GL_REPLACE: Sets the stencil buffer value to ref, as specified by glStencilFunc.</p>
         * <p>GL_INCR: Increments the current stencil buffer value. Clamps to the maximum representable unsigned value.</p>
         * <p>GL_INCR_WRAP: Increments the current stencil buffer value. Wraps stencil buffer value to zero when incrementing the maximum representable unsigned value.</p>
         * <p>GL_DECR: Decrements the current stencil buffer value. Clamps to 0.</p>
         * <p>GL_DECR_WRAP: Decrements the current stencil buffer value. Wraps stencil buffer value to the maximum representable unsigned value when decrementing a stencil buffer value of zero.</p>
         * <p>GL_INVERT: Bitwise inverts the current stencil buffer value.</p>         
         */
        public OpSeperate set(int sfail, int dpfail, int dppass) {
            this.sfail = sfail;
            this.dpfail = dpfail;
            this.dppass = dppass;
            return this;
        }

        /**
         * get the action to take when the stencil test fails
         * @return the action to take when the stencil test fails
         */
        public int sfail() {
            return sfail;
        }

        /**
         * get the action to take when the stencil test passes, but the depth test fails
         * @return the action to take when the stencil test passes, but the depth test fails
         */
        public int dpfail() {
            return dpfail;
        }

        /**
         * get the action to take when both the stencil test and the depth test pass, or when the stencil test passes and either there is no depth buffer or depth testing is not enabled
         * @return the action to take when both the stencil test and the depth test pass, or when the stencil test passes and either there is no depth buffer or depth testing is not enabled
         */
        public int dppass() {
            return dppass;
        }
    }

    public Stencil() {
        front = new Face();
        back = new Face();
    }

    public Face front() {
        return front;
    }

    public Face back() {
        return back;
    }
}
