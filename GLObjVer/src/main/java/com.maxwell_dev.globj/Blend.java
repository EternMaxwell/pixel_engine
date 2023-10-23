package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL46;

public class Blend {
    private final ColorBinding color;
    private final EquationBinding equation;
    private final FuncBinding func;
    private final FuncSeperateBinding funcSeperate;

    public class ColorBinding {
        private final float[] color;

        public ColorBinding() {
            color = new float[4];
        }

        /**
         * set the color of the blend
         * @param r the red component
         * @param g the green component
         * @param b the blue component
         * @param a the alpha component
         */
        public ColorBinding set(float r, float g, float b, float a) {
            color[0] = r;
            color[1] = g;
            color[2] = b;
            color[3] = a;
            return this;
        }

        public float[] get() {
            return color;
        }
    }

    public class FuncBinding {
        private Func func;

        public void set(Func func) {
            if (funcSeperate != null){
                throw new IllegalStateException("Cannot set both func and funcSeperate");
            }
            this.func = func;
        }

        public Func get() {
            return func;
        }
    }

    public class FuncSeperateBinding {
        private FuncSeperate func;

        public void set(FuncSeperate func) {
            if (func != null){
                throw new IllegalStateException("Cannot set both func and funcSeperate");
            }
            this.func = func;
        }

        public FuncSeperate get() {
            return func;
        }
    }

    public class EquationBinding {
        private Equation equation;

        public void set(Equation equation) {
            this.equation = equation;
        }

        public Equation get() {
            return equation;
        }
    }

    public static class Func{
        private int srcFactor, dstFactor;

        public Func() {
            srcFactor = GL46.GL_ONE;
            dstFactor = GL46.GL_ZERO;
        }

        /**
         * set the factors
         * @param src the factor of src.
         * @param dst the factor of dst.
         * <p>One of:</p>
         * <p>{@link GL46#GL_ZERO}, {@link GL46#GL_ONE}, {@link GL46#GL_SRC_COLOR}, {@link GL46#GL_ONE_MINUS_SRC_COLOR},
         * {@link GL46#GL_DST_COLOR}, {@link GL46#GL_ONE_MINUS_DST_COLOR}, {@link GL46#GL_SRC_ALPHA},
         * {@link GL46#GL_ONE_MINUS_SRC_ALPHA}, {@link GL46#GL_DST_ALPHA}, {@link GL46#GL_ONE_MINUS_DST_ALPHA},
         * {@link GL46#GL_CONSTANT_COLOR}, {@link GL46#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GL46#GL_CONSTANT_ALPHA},
         * {@link GL46#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GL46#GL_SRC_ALPHA_SATURATE}, {@link GL46#GL_SRC1_COLOR},
         * {@link GL46#GL_ONE_MINUS_SRC1_COLOR}, {@link GL46#GL_SRC1_ALPHA}, {@link GL46#GL_ONE_MINUS_SRC1_ALPHA}</p>
         */
        public Func set(int src, int dst) {
            this.srcFactor = src;
            this.dstFactor = dst;
            return this;
        }

        /**
         * get the factors
         * @return the factors
         */
        public int[] get() {
            return new int[] {srcFactor, dstFactor};
        }

        public int src() {
            return srcFactor;
        }

        public int dst() {
            return dstFactor;
        }
    }

    public static class FuncSeperate {
        private int srcRGBFactor, dstRGBFactor, srcAlphaFactor, dstAlphaFactor;

        public FuncSeperate() {
            srcRGBFactor = GL46.GL_ONE;
            dstRGBFactor = GL46.GL_ZERO;
            srcAlphaFactor = GL46.GL_ONE;
            dstAlphaFactor = GL46.GL_ZERO;
        }

        /**
         * set the factors
         * @param srcRGB the factor of src RGB.
         * @param dstRGB the factor of dst RGB.
         * @param srcAlpha the factor of src Alpha.
         * @param dstAlpha the factor of dst Alpha.
         * <p>One of:</p>
         * <p>{@link GL46#GL_ZERO}, {@link GL46#GL_ONE}, {@link GL46#GL_SRC_COLOR}, {@link GL46#GL_ONE_MINUS_SRC_COLOR},
         * {@link GL46#GL_DST_COLOR}, {@link GL46#GL_ONE_MINUS_DST_COLOR}, {@link GL46#GL_SRC_ALPHA},
         * {@link GL46#GL_ONE_MINUS_SRC_ALPHA}, {@link GL46#GL_DST_ALPHA}, {@link GL46#GL_ONE_MINUS_DST_ALPHA},
         * {@link GL46#GL_CONSTANT_COLOR}, {@link GL46#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GL46#GL_CONSTANT_ALPHA},
         * {@link GL46#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GL46#GL_SRC_ALPHA_SATURATE}, {@link GL46#GL_SRC1_COLOR},
         * {@link GL46#GL_ONE_MINUS_SRC1_COLOR}, {@link GL46#GL_SRC1_ALPHA}, {@link GL46#GL_ONE_MINUS_SRC1_ALPHA}</p>
         */
        public FuncSeperate set(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
            this.srcRGBFactor = srcRGB;
            this.dstRGBFactor = dstRGB;
            this.srcAlphaFactor = srcAlpha;
            this.dstAlphaFactor = dstAlpha;
            return this;
        }

        /**
         * get the factors
         * @return the factors
         */
        public int[] get() {
            return new int[] { srcRGBFactor, dstRGBFactor, srcAlphaFactor, dstAlphaFactor };
        }

        public int srcRGB() {
            return srcRGBFactor;
        }

        public int dstRGB() {
            return dstRGBFactor;
        }

        public int srcAlpha() {
            return srcAlphaFactor;
        }

        public int dstAlpha() {
            return dstAlphaFactor;
        }
    }

    public static class Equation {
        private int equation;

        public Equation() {
            equation = GL46.GL_FUNC_ADD;
        }

        /**
         * set the equation
         * @param equation the equation
         * <p>One of:</p>
         * <p>{@link GL46#GL_FUNC_ADD}, {@link GL46#GL_FUNC_SUBTRACT}, {@link GL46#GL_FUNC_REVERSE_SUBTRACT},
         * {@link GL46#GL_MIN}, {@link GL46#GL_MAX}</p>
         */
        public Equation set(int equation) {
            this.equation = equation;
            return this;
        }

        /**
         * get the equation
         * @return the equation
         */
        public int mode() {
            return equation;
        }
    }

    public Blend() {
        color = new ColorBinding();
        equation = new EquationBinding();
        func = new FuncBinding();
        funcSeperate = new FuncSeperateBinding();
    }

    /**
     * get the function binding
     * @return the function binding
     */
    public FuncBinding func() {
        return func;
    }

    /**
     * get the seperate function binding 
     * @return the seperate function binding
     */
    public FuncSeperateBinding funcSeperate() {
        return funcSeperate;
    }

    /**
     * get the color binding
     * @return the color binding
     */
    public ColorBinding color() {
        return color;
    }

    /**
     * get the equation binding
     * @return the equation binding
     */
    public EquationBinding equation() {
        return equation;
    }
}
