package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;

public class Sampler implements glInterface {
    private final int id;
    private final Parameteri textureMinFilter, textureMagFilter, textureWrapS, textureWrapT, textureWrapR,
            textureCompareMode, textureCompareFunc;
    private final Parameterf textureMinLod, textureMaxLod, textureLodBias;
    private final Parameterfv textureBorderColor;

    public class Parameteri {
        private final int target;
        private int param;

        public Parameteri(int target) {
            this.target = target;
        }

        /**
         * set the parameter to param
         * @param param the param to set
         */
        public void set(int param) {
            this.param = param;
            glSamplerParameteri(id, target, param);
        }

        /**
         * get the current set parameter value
         * @return the parameter value
         */
        public int get() {
            return param;
        }
    }

    public class Parameterf {
        private final int target;
        private float param;

        public Parameterf(int target) {
            this.target = target;
        }

        /**
         * set the parameter to param
         * @param param the param to set
         */
        public void set(float param) {
            this.param = param;
            glSamplerParameterf(id, target, param);
        }

        /**
         * get the currently set parameter value
         * @return the parameter value
         */
        public float get() {
            return param;
        }
    }

    public class Parameterfv {
        private final int target;
        private float[] param;

        public Parameterfv(int target) {
            this.target = target;
        }

        /**
         * set the parameter value to param
         * @param param the param value to set
         */
        public void set(float[] param) {
            this.param = param;
            glSamplerParameterfv(id, target, param);
        }

        /**
         * set the parameter value to param
         * @param param the param value to set
         */
        public void set(FloatBuffer param) {
            this.param = param.array();
            glSamplerParameterfv(id, target, param);
        }

        /**
         * get the currently set parameter value
         * @return the parameter value
         */
        public float[] get() {
            return param;
        }
    }

    /**
     * create a new sampler obj
     */
    public Sampler() {
        id = glCreateSamplers();

        textureMinFilter = new Parameteri(GL_TEXTURE_MIN_FILTER);
        textureMagFilter = new Parameteri(GL_TEXTURE_MAG_FILTER);
        textureWrapR = new Parameteri(GL_TEXTURE_WRAP_R);
        textureWrapS = new Parameteri(GL_TEXTURE_WRAP_S);
        textureWrapT = new Parameteri(GL_TEXTURE_WRAP_T);
        textureCompareMode = new Parameteri(GL_TEXTURE_COMPARE_MODE);
        textureCompareFunc = new Parameteri(GL_TEXTURE_COMPARE_FUNC);

        textureMinLod = new Parameterf(GL_TEXTURE_MIN_LOD);
        textureMaxLod = new Parameterf(GL_TEXTURE_MAX_LOD);
        textureLodBias = new Parameterf(GL_TEXTURE_LOD_BIAS);

        textureBorderColor = new Parameterfv(GL_TEXTURE_BORDER_COLOR);
    }

    /**
     * get the id of this sampler obj
     * @return the id
     */
    public int id() {
        return id;
    }

    //====PARAMETER BINDING====//
    /**
     * get the texture min filter parameter binding point 
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_NEAREST} {@link org.lwjgl.opengl.GL45#GL_LINEAR} {@link org.lwjgl.opengl.GL45#GL_NEAREST_MIPMAP_NEAREST} {@link org.lwjgl.opengl.GL45#GL_NEAREST_MIPMAP_LINEAR} {@link org.lwjgl.opengl.GL45#GL_LINEAR_MIPMAP_NEAREST} {@link org.lwjgl.opengl.GL45#GL_LINEAR_MIPMAP_LINEAR}</p>
     * @return the texture min filter parameter binding 
     */
    public Parameteri param_textureMinFilter() {
        return textureMinFilter;
    }

    /**
     * get the texture mag filter parameter binding point
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_NEAREST} {@link org.lwjgl.opengl.GL45#GL_LINEAR}</p>
     * @return the texture mag filter binding 
     */
    public Parameteri param_textureMagFilter() {
        return textureMagFilter;
    }

    /**
     * get the texture wrap s binding point 
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_REPEAT} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_MIRRORED_REPEAT} {@link org.lwjgl.opengl.GL45#GL_MIRROR_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_BORDER}
     * @return the texture wrap s binding point 
     */
    public Parameteri param_textureWrapS() {
        return textureWrapS;
    }

    /**
     * get the texture wrap t binding point 
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_REPEAT} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_MIRRORED_REPEAT} {@link org.lwjgl.opengl.GL45#GL_MIRROR_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_BORDER}
     * @return the texture wrap t binding point 
     */
    public Parameteri param_textureWrapT() {
        return textureWrapT;
    }

    /**
     * get the texture wrap r binding point 
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_REPEAT} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_MIRRORED_REPEAT} {@link org.lwjgl.opengl.GL45#GL_MIRROR_CLAMP_TO_EDGE} {@link org.lwjgl.opengl.GL45#GL_CLAMP_TO_BORDER}
     * @return the texture wrap r binding point 
     */
    public Parameteri param_textureWrapR() {
        return textureWrapR;
    }

    /**
     * get the texture compare mode binding point 
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_NONE} {@link org.lwjgl.opengl.GL45#GL_COMPARE_REF_TO_TEXTURE}</p>
     * @return the texture compare mode binding point 
     */
    public Parameteri param_textureCompareMode() {
        return textureCompareMode;
    }

    /**
     * get the texture compare func binding point
     * <p>the param value for this must be one of:</p>
     * <p>{@link org.lwjgl.opengl.GL45#GL_LEQUAL} {@link org.lwjgl.opengl.GL45#GL_GEQUAL} {@link org.lwjgl.opengl.GL45#GL_LESS} {@link org.lwjgl.opengl.GL45#GL_GREATER} {@link org.lwjgl.opengl.GL45#GL_EQUAL} {@link org.lwjgl.opengl.GL45#GL_NOTEQUAL} {@link org.lwjgl.opengl.GL45#GL_ALWAYS} {@link org.lwjgl.opengl.GL45#GL_NEVER}</p> 
     * @return the texture compare func binding 
     */
    public Parameteri param_textureCompareFunc() {
        return textureCompareFunc;
    }

    /**
     * get the texture min lod binding point
     * <p>Sets the minimum level-of-detail parameter</p>
     * <p>This floating-point value limits the selection of highest resolution mipmap (lowest mipmap level)</p>
     * <p>The initial value is -1000</p> 
     * @return the texture min lod binding 
     */
    public Parameterf param_textureMinLod() {
        return textureMinLod;
    }

    /**
     * get the texture max lod binding point
     * <p>Sets the maximum level-of-detail parameter</p>
     * <p>This floating-point value limits the selection of the lowest resolution mipmap (highest mipmap level)</p>
     * <p>The initial value is 1000</p>
     * @return the texture max lod binding 
     */
    public Parameterf param_textureMaxLod() {
        return textureMaxLod;
    }

    /**
     * get the texture lod bias binding point
     * <p>specifies a fixed bias value that is to be added to the level-of-detail parameter for the texture before texture sampling.</p>
     * <p>The specified value is added to the shader-supplied bias value (if any) and subsequently clamped into the implementation-defined range - bias max, where bias max is the value of the implementation defined constant {@link org.lwjgl.opengl.GL45#GL_MAX_TEXTURE_LOD_BIAS}</p>
     * <p>The initial value is 0.0</p>
     * @return the texture lod bias binding
     */
    public Parameterf param_textureLodBias() {
        return textureLodBias;
    }

    /**
     * get the texture border color binding point 
     * @return the texture border color binding
     */
    public Parameterfv param_textureBorderColor() {
        return textureBorderColor;
    }

    /**
     * delete the sampler obj
     */
    public void delete() {
        glDeleteSamplers(id);
    }
}
