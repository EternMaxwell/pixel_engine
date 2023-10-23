package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL45;
import static org.lwjgl.opengl.GL45.*;

public class Texture implements glInterface {
    protected final int id;
    protected final int target;

    private final Parameteri depthStencilTextureMode, textureBaseLevel, textureCompareFunc, textureCompareMode,
            textureMinFilter, textureMagFilter, textureMaxLevel, textureSwizzleR, textureSwizzleG, textureSwizzleB,
            textureSwizzleA, textureWrapS, textureWrapT, textureWrapR;
    private final Parameteriv textureSwizzleRGBA;
    private final Parameterf textureLodBias, textureMinLod, textureMaxLod;
    private final Parameterfv textureBorderColor;
    private final Storage storage;
    private final TextureView textureView;
    private final TextureImage1D textureImage1D;
    private final TextureImage2D textureImage2D;
    private final TextureImage3D textureImage3D;
    private final TextureImageCubeMap textureImageCubeMap;
    private final TextureImageCubeMapArray textureImageCubeMapArray;
    private final TextureImage2DMultisample textureImage2DMultisample;
    private final TextureImage3DMultisample textureImage3DMultisample;
    private final TextureSubImage1D textureSubImage1D;
    private final TextureSubImage2D textureSubImage2D;
    private final TextureSubImage3D textureSubImage3D;
    private final TextureSubImageCubeMap textureSubImageCubeMap;
    private final TextureSubImageCubeMapArray textureSubImageCubeMapArray;
    private final CopyData copyData;
    private final GetImage getImage;
    private final GetSubImage getSubImage;
    private final CopyImageSubData copyImageSubData;
    private final ClearImage clearImage;
    private final ClearSubImage clearSubImage;
    private final Mipmap mipmap;

    /**
     * create a new texture obj
     *
     * @param target the type of the texture
     */
    public Texture(int target) {
        this.target = target;
        id = glCreateTextures(this.target);

        depthStencilTextureMode = new Parameteri(GL_DEPTH_STENCIL_TEXTURE_MODE);
        textureBaseLevel = new Parameteri(GL_TEXTURE_BASE_LEVEL);
        textureCompareFunc = new Parameteri(GL_TEXTURE_COMPARE_FUNC);
        textureCompareMode = new Parameteri(GL_TEXTURE_COMPARE_MODE);
        textureMinFilter = new Parameteri(GL_TEXTURE_MIN_FILTER);
        textureMagFilter = new Parameteri(GL_TEXTURE_MAG_FILTER);
        textureMaxLevel = new Parameteri(GL_TEXTURE_MAX_LEVEL);
        textureSwizzleR = new Parameteri(GL_TEXTURE_SWIZZLE_R);
        textureSwizzleG = new Parameteri(GL_TEXTURE_SWIZZLE_G);
        textureSwizzleB = new Parameteri(GL_TEXTURE_SWIZZLE_B);
        textureSwizzleA = new Parameteri(GL_TEXTURE_SWIZZLE_A);
        textureWrapS = new Parameteri(GL_TEXTURE_WRAP_S);
        textureWrapT = new Parameteri(GL_TEXTURE_WRAP_T);
        textureWrapR = new Parameteri(GL_TEXTURE_WRAP_R);

        textureSwizzleRGBA = new Parameteriv(GL_TEXTURE_SWIZZLE_RGBA);

        textureLodBias = new Parameterf(GL_TEXTURE_LOD_BIAS);
        textureMinLod = new Parameterf(GL_TEXTURE_MIN_LOD);
        textureMaxLod = new Parameterf(GL_TEXTURE_MAX_LOD);

        textureBorderColor = new Parameterfv(GL_TEXTURE_BORDER_COLOR);

        storage = new Storage();

        textureView = new TextureView();
        textureImage1D = new TextureImage1D();
        textureImage2D = new TextureImage2D();
        textureImage3D = new TextureImage3D();
        textureImageCubeMap = new TextureImageCubeMap();
        textureImageCubeMapArray = new TextureImageCubeMapArray();
        textureImage2DMultisample = new TextureImage2DMultisample();
        textureImage3DMultisample = new TextureImage3DMultisample();
        textureSubImage1D = new TextureSubImage1D();
        textureSubImage2D = new TextureSubImage2D();
        textureSubImage3D = new TextureSubImage3D();
        textureSubImageCubeMap = new TextureSubImageCubeMap();
        textureSubImageCubeMapArray = new TextureSubImageCubeMapArray();

        copyData = new CopyData();

        getImage = new GetImage();
        getSubImage = new GetSubImage();

        copyImageSubData = new CopyImageSubData();

        clearImage = new ClearImage();
        clearSubImage = new ClearSubImage();

        mipmap = new Mipmap();
    }

    /**
     * get the texture obj id
     *
     * @return the id
     */
    public int id() {
        return id;
    }

    /**
     * get the texture type
     *
     * @reture the texture type
     */
    public int type() {
        return target;
    }

    /**
     * active the selected texture unit
     *
     * @param index the texture unit index
     */
    public static void activeTextureUnit(int index) {
        glActiveTexture(GL_TEXTURE0 + index);
    }

    /**
     * bind the texture to the specified target
     */
    public void bindTexture() {
        glBindTexture(target, id);
    }

    public class Parameteri {
        private final int target;
        private int param;

        public Parameteri(int target) {
            this.target = target;
        }

        /**
         * set the parameter to param
         *
         * @param param the param to set
         */
        public void set(int param) {
            this.param = param;
            glTextureParameteri(id, target, param);
        }

        /**
         * get the current set parameter value
         *
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
         *
         * @param param the param to set
         */
        public void set(float param) {
            this.param = param;
            glTextureParameterf(id, target, param);
        }

        /**
         * get the currently set parameter value
         *
         * @return the parameter value
         */
        public float get() {
            return param;
        }
    }

    public class Parameteriv {
        private final int target;
        private int[] param;

        public Parameteriv(int target) {
            this.target = target;
        }

        /**
         * set the parameter to param
         *
         * @param param the param to set
         */
        public void set(int[] param) {
            this.param = param;
            glTextureParameteriv(id, target, param);
        }

        /**
         * get the current set parameter value
         *
         * @return the parameter value
         */
        public int[] get() {
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
         * set the parameter to param
         *
         * @param param the param to set
         */
        public void set(float[] param) {
            this.param = param;
            glTextureParameterfv(id, target, param);
        }

        /**
         * get the currently set parameter value
         *
         * @return the parameter value
         */
        public float[] get() {
            return param;
        }
    }

    public class Storage {
        /**
         * specify a one-dimensional texture image
         *
         * @param level          Specifies the level-of-detail number. Level 0 is the base image level. Level n is the nth mipmap reduction image.
         * @param internalFormat the texture internal format. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width          Specifies the width of the texture, in texels.
         */
        public void store1D(int level, int internalFormat, int width) {
            glTextureStorage1D(id, level, internalFormat, width);
        }

        /**
         * specify a two-dimensional texture image
         *
         * @param level          Specifies the level-of-detail number. Level 0 is the base image level. Level n is the nth mipmap reduction image.
         * @param internalFormat the texture internal format. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width          Specifies the width of the texture, in texels.
         * @param height         Specifies the height of the texture, in texels.
         */
        public void store2D(int level, int internalFormat, int width, int height) {
            glTextureStorage2D(id, level, internalFormat, width, height);
        }

        /**
         * specify a three-dimensional texture image
         *
         * @param level          Specifies the level-of-detail number. Level 0 is the base image level. Level n is the nth mipmap reduction image.
         * @param internalFormat the texture internal format. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width          Specifies the width of the texture, in texels.
         * @param height         Specifies the height of the texture, in texels.
         * @param depth          Specifies the depth of the texture, in texels.
         */
        public void store3D(int level, int internalFormat, int width, int height, int depth) {
            glTextureStorage3D(id, level, internalFormat, width, height, depth);
        }

        /**
         * specify a two-dimensional multisample texture image
         *
         * @param samples              Specifies the number of samples in the texture.
         * @param internalFormat       the texture internal format. One of:
         *                             <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                             {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                             {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                             {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                             {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                             {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                             {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                             {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                             {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                             {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                             {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                             {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                             {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                             {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                             {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                             {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                             {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                             {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                             {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                             {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                             {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                             {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                             {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                             {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width                Specifies the width of the texture, in texels.
         * @param height               Specifies the height of the texture, in texels.
         * @param fixedSampleLocations Specifies whether the image will use identical sample locations and the same number of samples for all texels in the image, and the sample locations will not depend on the internal format or size of the image.
         */
        public void store2DMultisample(int samples, int internalFormat, int width, int height,
                                       boolean fixedSampleLocations) {
            glTextureStorage2DMultisample(id, samples, internalFormat, width, height, fixedSampleLocations);
        }

        /**
         * specify a two-dimensional multisample array texture image
         *
         * @param samples              Specifies the number of samples in the texture.
         * @param internalFormat       the texture internal format. One of:
         *                             <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                             {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                             {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                             {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                             {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                             {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                             {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                             {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                             {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                             {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                             {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                             {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                             {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                             {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                             {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                             {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                             {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                             {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                             {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                             {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                             {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                             {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                             {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                             {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                             {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width                Specifies the width of the texture, in texels.
         * @param height               Specifies the height of the texture, in texels.
         * @param depth                Specifies the depth of the texture, in layers.
         * @param fixedSampleLocations Specifies whether the image will use identical sample locations and the same number of samples for all texels in the image, and the sample locations will not depend on the internal format or size of the image.
         */
        public void store3DMultisample(int samples, int internalFormat, int width, int height, int depth,
                                       boolean fixedSampleLocations) {
            glTextureStorage3DMultisample(id, samples, internalFormat, width, height, depth, fixedSampleLocations);
        }

        /**
         * specify a two-dimensional texture image
         *
         * @param level          Specifies the level-of-detail number. Level 0 is the base image level. Level n is the nth mipmap reduction image.
         * @param internalFormat the texture internal format. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width          Specifies the width of the texture, in texels.
         */
        public void storeCubeMap(int level, int internalFormat, int width) {
            glTextureStorage2D(id, level, internalFormat, width, width);
        }

        /**
         * specify a two-dimensional texture image
         *
         * @param level          Specifies the level-of-detail number. Level 0 is the base image level. Level n is the nth mipmap reduction image.
         * @param internalFormat the texture internal format. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param width          Specifies the width of the texture, in texels.
         * @param layers         Specifies the number of layers in the texture.
         */
        public void storeCubeMapArray(int level, int internalFormat, int width, int layers) {
            glTextureStorage3D(id, level, internalFormat, width, width, layers * 6);
        }
    }

    public class TextureView {
        /**
         * Initializes this texture as a data alias of another texture's data store.
         * <p>If the origtexture is not a texture type that has mipmaps (multisample or rectangle textures), then minlevel must be 0 and numlevels must be 1.</p>
         * <p>For textures that could have mipmaps, then minlevel and numlevels will be clamped to the actual available number of mipmaps in the source texture (though it is an error if minlevel is outside of the range of mipmaps).</p>
         * <p>For textures that have layers or faces (GL_TEXTURE_1D_ARRAY, GL_TEXTURE_2D_ARRAY, GL_TEXTURE_CUBE_MAP, or GL_TEXTURE_CUBE_MAP_ARRAY), a range of layers to take can be specified with minlayer and numlayers. As with the mipmap level range parameters, the layer ranges are clamped to the available range of layers, and minlayer must be an available layer in the image. Cube maps are treated as an array texture with 6 layers. Cube map array layers here are layer-faces.</p>
         *
         * @param originTexture  the texture to use as the data store
         * @param internalFormat the internal format to use for the view. One of:
         *                       <p>{@link GL45#GL_DEPTH_COMPONENT16}, {@link GL45#GL_DEPTH_COMPONENT24}, {@link GL45#GL_DEPTH_COMPONENT32},
         *                       {@link GL45#GL_DEPTH_COMPONENT32F}, {@link GL45#GL_DEPTH24_STENCIL8}, {@link GL45#GL_DEPTH32F_STENCIL8},
         *                       {@link GL45#GL_STENCIL_INDEX8}, {@link GL45#GL_R8}, {@link GL45#GL_R8_SNORM}, {@link GL45#GL_R16},
         *                       {@link GL45#GL_R16_SNORM}, {@link GL45#GL_RG8}, {@link GL45#GL_RG8_SNORM}, {@link GL45#GL_RG16},
         *                       {@link GL45#GL_RG16_SNORM}, {@link GL45#GL_R3_G3_B2}, {@link GL45#GL_RGB4}, {@link GL45#GL_RGB5},
         *                       {@link GL45#GL_RGB8}, {@link GL45#GL_RGB8_SNORM}, {@link GL45#GL_RGB10}, {@link GL45#GL_RGB12},
         *                       {@link GL45#GL_RGB16_SNORM}, {@link GL45#GL_RGBA2}, {@link GL45#GL_RGBA4}, {@link GL45#GL_RGB5_A1},
         *                       {@link GL45#GL_RGBA8}, {@link GL45#GL_RGBA8_SNORM}, {@link GL45#GL_RGB10_A2}, {@link GL45#GL_RGB10_A2UI},
         *                       {@link GL45#GL_RGBA12}, {@link GL45#GL_RGBA16}, {@link GL45#GL_SRGB8}, {@link GL45#GL_SRGB8_ALPHA8},
         *                       {@link GL45#GL_R16F}, {@link GL45#GL_RG16F}, {@link GL45#GL_RGB16F}, {@link GL45#GL_RGBA16F},
         *                       {@link GL45#GL_R32F}, {@link GL45#GL_RG32F}, {@link GL45#GL_RGB32F}, {@link GL45#GL_RGBA32F},
         *                       {@link GL45#GL_R11F_G11F_B10F}, {@link GL45#GL_RGB9_E5}, {@link GL45#GL_R8I}, {@link GL45#GL_R8UI},
         *                       {@link GL45#GL_R16I}, {@link GL45#GL_R16UI}, {@link GL45#GL_R32I}, {@link GL45#GL_R32UI},
         *                       {@link GL45#GL_RG8I}, {@link GL45#GL_RG8UI}, {@link GL45#GL_RG16I}, {@link GL45#GL_RG16UI},
         *                       {@link GL45#GL_RG32I}, {@link GL45#GL_RG32UI}, {@link GL45#GL_RGB8I}, {@link GL45#GL_RGB8UI},
         *                       {@link GL45#GL_RGB16I}, {@link GL45#GL_RGB16UI}, {@link GL45#GL_RGB32I}, {@link GL45#GL_RGB32UI},
         *                       {@link GL45#GL_RGBA8I}, {@link GL45#GL_RGBA8UI}, {@link GL45#GL_RGBA16I}, {@link GL45#GL_RGBA16UI},
         *                       {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
         *                       {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
         *                       {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
         *                       {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
         *                       {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
         *                       {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
         * @param minLevel       the lowest level of the texture to use
         * @param numLevels      the number of levels to use
         * @param minLayer       the lowest layer of the texture to use
         * @param numLayers      the number of layers to use
         */
        public void view(Texture originTexture, int internalFormat, int minLevel, int numLevels, int minLayer,
                         int numLayers) {
            glTextureView(id, target, originTexture.id(), internalFormat, minLevel, numLevels, minLayer, numLayers);
        }
    }

    public class TextureImage1D {
        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          java.nio.ByteBuffer pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          java.nio.ShortBuffer pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          java.nio.IntBuffer pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          java.nio.FloatBuffer pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          java.nio.DoubleBuffer pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          short[] pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type, int[] pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          float[] pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int border, int format, int type,
                          double[] pixels) {
            storage.store1D(level, internalFormat, width);
            glTextureSubImage1D(id, level, 0, width, format, type, pixels);
        }

        public void imageCompressed(int level, int internalFormat, int width, int border, java.nio.ByteBuffer data) {
            storage.store1D(level, internalFormat, width);
            glCompressedTextureSubImage1D(id, level, 0, width, internalFormat, data);
        }
    }

    public class TextureImage2D {
        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          java.nio.ByteBuffer pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          java.nio.ShortBuffer pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          java.nio.IntBuffer pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          java.nio.FloatBuffer pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          java.nio.DoubleBuffer pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          short[] pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          int[] pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          float[] pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int border, int format, int type,
                          double[] pixels) {
            storage.store2D(level, internalFormat, width, height);
            glTextureSubImage2D(id, level, 0, 0, width, height, format, type, pixels);
        }

        public void imageCompressed(int level, int internalFormat, int width, int height, int border,
                                    java.nio.ByteBuffer data) {
            storage.store2D(level, internalFormat, width, height);
            glCompressedTextureSubImage2D(id, level, 0, 0, width, height, internalFormat, data);
        }
    }

    public class TextureImage3D {
        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, java.nio.ByteBuffer pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, java.nio.ShortBuffer pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, java.nio.IntBuffer pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, java.nio.FloatBuffer pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, java.nio.DoubleBuffer pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, short[] pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, int[] pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, float[] pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void image(int level, int internalFormat, int width, int height, int depth, int border, int format,
                          int type, double[] pixels) {
            storage.store3D(level, internalFormat, width, height, depth);
            glTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, format, type, pixels);
        }

        public void imageCompressed(int level, int internalFormat, int width, int height, int depth, int border,
                                    java.nio.ByteBuffer data) {
            storage.store3D(level, internalFormat, width, height, depth);
            glCompressedTextureSubImage3D(id, level, 0, 0, 0, width, height, depth, internalFormat, data);
        }
    }

    public class TextureImageCubeMap {
        public void image(int level, int internalFormat, int width) {
            storage.store2D(level, internalFormat, width, width);
        }

        public void imageCompressed(int level, int internalFormat, int width) {
            storage.store2D(level, internalFormat, width, width);
        }
    }

    public class TextureImageCubeMapArray {
        public void image(int layer, int level, int internalFormat, int width) {
            storage.store3D(level, internalFormat, width, width, layer);
        }

        public void imageCompressed(int layer, int level, int internalFormat, int width) {
            storage.store3D(level, internalFormat, width, width, layer);
        }
    }

    public class TextureImage2DMultisample {
        public void image(int samples, int internalFormat, int width, int height,
                          boolean fixedSampleLocations) {
            storage.store2DMultisample(samples, internalFormat, width, height, fixedSampleLocations);
        }
    }

    public class TextureImage3DMultisample {
        public void image(int samples, int internalFormat, int width, int height, int depth,
                          boolean fixedSampleLocations) {
            storage.store3DMultisample(samples, internalFormat, width, height, depth, fixedSampleLocations);
        }
    }

    public class TextureSubImage1D {
        public void subImage(int level, int xOffset, int width, int format, int type, java.nio.ByteBuffer pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, java.nio.ShortBuffer pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, java.nio.IntBuffer pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, java.nio.FloatBuffer pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, java.nio.DoubleBuffer pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, short[] pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, int[] pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, float[] pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int width, int format, int type, double[] pixels) {
            glTextureSubImage1D(id, level, xOffset, width, format, type, pixels);
        }

        public void subImageCompressed(int level, int xOffset, int width, int format, java.nio.ByteBuffer data) {
            glCompressedTextureSubImage1D(id, level, xOffset, width, format, data);
        }
    }

    public class TextureSubImage2D {
        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             java.nio.ByteBuffer pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             java.nio.ShortBuffer pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             java.nio.IntBuffer pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             java.nio.FloatBuffer pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             java.nio.DoubleBuffer pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             short[] pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             int[] pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             float[] pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int width, int height, int format, int type,
                             double[] pixels) {
            glTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, type, pixels);
        }

        public void subImageCompressed(int level, int xOffset, int yOffset, int width, int height, int format,
                                       java.nio.ByteBuffer data) {
            glCompressedTextureSubImage2D(id, level, xOffset, yOffset, width, height, format, data);
        }
    }

    public class TextureSubImage3D {
        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, java.nio.ByteBuffer pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, java.nio.ShortBuffer pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, java.nio.IntBuffer pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, java.nio.FloatBuffer pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, java.nio.DoubleBuffer pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, short[] pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, int[] pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, float[] pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImage(int level, int xOffset, int yOffset, int zOffset, int width, int height, int depth,
                             int format, int type, double[] pixels) {
            glTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, type, pixels);
        }

        public void subImageCompressed(int level, int xOffset, int yOffset, int zOffset, int width, int height,
                                       int depth, int format, java.nio.ByteBuffer data) {
            glCompressedTextureSubImage3D(id, level, xOffset, yOffset, zOffset, width, height, depth, format, data);
        }
    }

    public class TextureSubImageCubeMap {
        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, java.nio.ByteBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, java.nio.ShortBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, java.nio.IntBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, java.nio.FloatBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, java.nio.DoubleBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, short[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, int[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, float[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImage(int face, int level, int xOffset, int yOffset, int width, int height, int format,
                             int type, double[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, type, data);
        }

        public void subImageCompressed(int face, int level, int xOffset, int yOffset, int width, int height,
                                       int format, java.nio.ByteBuffer data) {
            glCompressedTextureSubImage3D(id, level, xOffset, yOffset, face, width, height, 1, format, data);
        }
    }

    public class TextureSubImageCubeMapArray {
        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, java.nio.ByteBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, java.nio.ShortBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, java.nio.IntBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, java.nio.FloatBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, java.nio.DoubleBuffer data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, short[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, int[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, float[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImage(int layer, int face, int level, int xOffset, int yOffset, int width, int height,
                             int format, int type, double[] data) {
            glTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format, type, data);
        }

        public void subImageCompressed(int layer, int face, int level, int xOffset, int yOffset, int width,
                                       int height, int format, java.nio.ByteBuffer data) {
            glCompressedTextureSubImage3D(id, level, xOffset, yOffset, layer * 6 + face, width, height, 1, format,
                    data);
        }
    }

    public class CopyData {
        /**
         * copy a block of pixels from the framebuffer to the texture
         *
         * @param dstTexture the destination texture
         * @param srcLevel   the source level
         * @param srcX       the source x coordinate
         * @param srcY       the source y coordinate
         * @param srcZ       the source z coordinate
         * @param dstLevel   the destination level
         * @param dstX       the destination x coordinate
         * @param dstY       the destination y coordinate
         * @param dstZ       the destination z coordinate
         * @param width      the width of the block
         * @param height     the height of the block
         * @param depth      the depth of the block
         */
        public void to(Texture dstTexture, int srcLevel, int srcX, int srcY, int srcZ, int dstLevel, int dstX,
                       int dstY, int dstZ, int width, int height, int depth) {
            glCopyImageSubData(id, target, srcLevel, srcX, srcY, srcZ, dstTexture.id, dstTexture.target, dstLevel,
                    dstX, dstY, dstZ, width, height, depth);
        }

        /**
         * copy a block of pixels from the texture to the framebuffer
         *
         * @param srcTexture the source texture
         * @param srcLevel   the source level
         * @param srcX       the source x coordinate
         * @param srcY       the source y coordinate
         * @param srcZ       the source z coordinate
         * @param dstLevel   the destination level
         * @param dstX       the destination x coordinate
         * @param dstY       the destination y coordinate
         * @param dstZ       the destination z coordinate
         * @param width      the width of the block
         * @param height     the height of the block
         * @param depth      the depth of the block
         */
        public void from(Texture srcTexture, int srcLevel, int srcX, int srcY, int srcZ, int dstLevel, int dstX,
                         int dstY, int dstZ, int width, int height, int depth) {
            glCopyImageSubData(srcTexture.id, srcTexture.target, srcLevel, srcX, srcY, srcZ, id, target, dstLevel,
                    dstX, dstY, dstZ, width, height, depth);
        }
    }

    public class GetImage {
        public void get(int level, int format, int type, java.nio.ByteBuffer pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, java.nio.ShortBuffer pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, java.nio.IntBuffer pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, java.nio.FloatBuffer pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, java.nio.DoubleBuffer pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, short[] pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, int[] pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, float[] pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }

        public void get(int level, int format, int type, double[] pixels) {
            glGetTextureImage(id, level, format, type, pixels);
        }
    }

    public class GetSubImage {
        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, java.nio.ByteBuffer data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, java.nio.ShortBuffer data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, java.nio.IntBuffer data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, java.nio.FloatBuffer data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, java.nio.DoubleBuffer data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, short[] data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, int[] data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, float[] data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void get(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                        int format, int type, double[] data) {
            glGetTextureSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }
    }

    public class CopyImageSubData {
        /**
         * copy a block of pixels from the framebuffer to the texture
         *
         * @param srcTexture the source texture
         * @param srcLevel   the source level
         * @param srcX       the source x coordinate
         * @param srcY       the source y coordinate
         * @param srcZ       the source z coordinate
         * @param dstLevel   the destination level
         * @param dstX       the destination x coordinate
         * @param dstY       the destination y coordinate
         * @param dstZ       the destination z coordinate
         * @param width      the width of the block
         * @param height     the height of the block
         * @param depth      the depth of the block
         */
        public void from(Texture srcTexture, int srcLevel, int srcX, int srcY, int srcZ, int dstLevel, int dstX,
                         int dstY, int dstZ, int width, int height, int depth) {
            glCopyImageSubData(srcTexture.id, srcTexture.target, srcLevel, srcX, srcY, srcZ, id, target, dstLevel,
                    dstX, dstY, dstZ, width, height, depth);
        }

        /**
         * copy a block of pixels from the texture to the framebuffer
         *
         * @param dstTexture the destination texture
         * @param srcLevel   the source level
         * @param srcX       the source x coordinate
         * @param srcY       the source y coordinate
         * @param srcZ       the source z coordinate
         * @param dstLevel   the destination level
         * @param dstX       the destination x coordinate
         * @param dstY       the destination y coordinate
         * @param dstZ       the destination z coordinate
         * @param width      the width of the block
         * @param height     the height of the block
         * @param depth      the depth of the block
         */
        public void to(Texture dstTexture, int srcLevel, int srcX, int srcY, int srcZ, int dstLevel, int dstX,
                       int dstY, int dstZ, int width, int height, int depth) {
            glCopyImageSubData(id, target, srcLevel, srcX, srcY, srcZ, dstTexture.id, dstTexture.target, dstLevel,
                    dstX, dstY, dstZ, width, height, depth);
        }
    }

    public class ClearImage {
        public void clear(int level, int format, int type, java.nio.ByteBuffer data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, java.nio.ShortBuffer data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, java.nio.IntBuffer data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, java.nio.FloatBuffer data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, java.nio.DoubleBuffer data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, short[] data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, int[] data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, float[] data) {
            glClearTexImage(id, level, format, type, data);
        }

        public void clear(int level, int format, int type, double[] data) {
            glClearTexImage(id, level, format, type, data);
        }
    }

    public class ClearSubImage {
        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, java.nio.ByteBuffer data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, java.nio.ShortBuffer data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, java.nio.IntBuffer data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, java.nio.FloatBuffer data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, java.nio.DoubleBuffer data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, short[] data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, int[] data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, float[] data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }

        public void clear(int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth,
                          int format, int type, double[] data) {
            glClearTexSubImage(id, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
        }
    }

    public class Mipmap {
        public void generate() {
            glGenerateTextureMipmap(id);
        }
    }

    /**
     * get the parameter object for GL_DEPTH_STENCIL_TEXTURE_MODE
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_DEPTH_COMPONENT} {@link GL45#GL_STENCIL_INDEX}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_depthStencilTextureMode() {
        return depthStencilTextureMode;
    }

    /**
     * get the parameter object for GL_TEXTURE_BASE_LEVEL
     * <p>Specifies the index of the lowest defined mipmap level.</p>
     * <p>Default is 0</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureBaseLevel() {
        return textureBaseLevel;
    }

    /**
     * get the parameter object for GL_TEXTURE_COMPARE_FUNC
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_NEVER} {@link GL45#GL_LESS} {@link GL45#GL_EQUAL} {@link GL45#GL_LEQUAL} {@link GL45#GL_GREATER} {@link GL45#GL_NOTEQUAL} {@link GL45#GL_GEQUAL} {@link GL45#GL_ALWAYS}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureCompareFunc() {
        return textureCompareFunc;
    }

    /**
     * get the parameter object for GL_TEXTURE_COMPARE_MODE
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_NONE} {@link GL45#GL_COMPARE_REF_TO_TEXTURE}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureCompareMode() {
        return textureCompareMode;
    }

    /**
     * get the parameter object for GL_TEXTURE_MIN_FILTER
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_NEAREST} {@link GL45#GL_LINEAR} {@link GL45#GL_NEAREST_MIPMAP_NEAREST} {@link GL45#GL_LINEAR_MIPMAP_NEAREST} {@link GL45#GL_NEAREST_MIPMAP_LINEAR} {@link GL45#GL_LINEAR_MIPMAP_LINEAR}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureMinFilter() {
        return textureMinFilter;
    }

    /**
     * get the parameter object for GL_TEXTURE_MAG_FILTER
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_NEAREST} {@link GL45#GL_LINEAR}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureMagFilter() {
        return textureMagFilter;
    }

    /**
     * get the parameter object for GL_TEXTURE_MAX_LEVEL
     * <p>Specifies the index of the highest defined mipmap level.</p>
     * <p>Default is 1000</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureMaxLevel() {
        return textureMaxLevel;
    }

    /**
     * get the parameter object for GL_TEXTURE_SWIZZLE_R
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_RED} {@link GL45#GL_GREEN} {@link GL45#GL_BLUE} {@link GL45#GL_ALPHA} {@link GL45#GL_ZERO} {@link GL45#GL_ONE}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureSwizzleR() {
        return textureSwizzleR;
    }

    /**
     * get the parameter object for GL_TEXTURE_SWIZZLE_G
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_RED} {@link GL45#GL_GREEN} {@link GL45#GL_BLUE} {@link GL45#GL_ALPHA} {@link GL45#GL_ZERO} {@link GL45#GL_ONE}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureSwizzleG() {
        return textureSwizzleG;
    }

    /**
     * get the parameter object for GL_TEXTURE_SWIZZLE_B
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_RED} {@link GL45#GL_GREEN} {@link GL45#GL_BLUE} {@link GL45#GL_ALPHA} {@link GL45#GL_ZERO} {@link GL45#GL_ONE}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureSwizzleB() {
        return textureSwizzleB;
    }

    /**
     * get the parameter object for GL_TEXTURE_SWIZZLE_A
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_RED} {@link GL45#GL_GREEN} {@link GL45#GL_BLUE} {@link GL45#GL_ALPHA} {@link GL45#GL_ZERO} {@link GL45#GL_ONE}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureSwizzleA() {
        return textureSwizzleA;
    }

    /**
     * get the parameter object for GL_TEXTURE_WRAP_S
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT} {@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureWrapS() {
        return textureWrapS;
    }

    /**
     * get the parameter object for GL_TEXTURE_WRAP_T
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT} {@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureWrapT() {
        return textureWrapT;
    }

    /**
     * get the parameter object for GL_TEXTURE_WRAP_R
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT} {@link GL45#GL_CLAMP_TO_EDGE} {@link GL45#GL_CLAMP_TO_BORDER} {@link GL45#GL_MIRRORED_REPEAT} {@link GL45#GL_REPEAT}</p>
     *
     * @return the parameter object
     */
    public Parameteri param_textureWrapR() {
        return textureWrapR;
    }

    /**
     * get the parameter object for GL_TEXTURE_SWIZZLE_RGBA
     * <p>the available param value for this are:</p>
     * <p>{@link GL45#GL_RED} {@link GL45#GL_GREEN} {@link GL45#GL_BLUE} {@link GL45#GL_ALPHA} {@link GL45#GL_ZERO} {@link GL45#GL_ONE}</p>
     *
     * @return the parameter object
     */
    public Parameteriv param_textureSwizzleRGBA() {
        return textureSwizzleRGBA;
    }

    /**
     * get the parameter object for GL_TEXTURE_LOD_BIAS
     * <p>Specifies a fixed bias value that is to be added to the level-of-detail parameter for the texture before texture sampling.</p>
     * <p>Default is 0</p>
     *
     * @return the parameter object
     */
    public Parameterf param_textureLodBias() {
        return textureLodBias;
    }

    /**
     * get the parameter object for GL_TEXTURE_MIN_LOD
     * <p>Specifies the minimum level-of-detail parameter.</p>
     * <p>Default is -1000</p>
     *
     * @return the parameter object
     */
    public Parameterf param_textureMinLod() {
        return textureMinLod;
    }

    /**
     * get the parameter object for GL_TEXTURE_MAX_LOD
     * <p>Specifies the maximum level-of-detail parameter.</p>
     * <p>Default is 1000</p>
     *
     * @return the parameter object
     */
    public Parameterf param_textureMaxLod() {
        return textureMaxLod;
    }

    /**
     * get the parameter object for GL_TEXTURE_BORDER_COLOR
     * <p>Specifies the texture border color.</p>
     * <p>Default is (0,0,0,0)</p>
     *
     * @return the parameter object
     */
    public Parameterfv param_textureBorderColor() {
        return textureBorderColor;
    }

    /**
     * get the storage object for this texture
     *
     * @return the storage object
     */
    public Storage textureStorage() {
        return storage;
    }

    /**
     * get the texture view object for this texture
     *
     * @return the texture view object
     */
    public TextureView textureView() {
        return textureView;
    }

    /**
     * get the texture image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param border - the texture border width</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param pixels - the texel data</p>
     *
     * @return the texture image object
     */
    public TextureImage1D textureImage1D() {
        return textureImage1D;
    }

    /**
     * get the texture image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param height - the height of the texture image</p>
     * <p>param border - the texture border width</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param pixels - the texel data</p>
     *
     * @return the texture image object
     */
    public TextureImage2D textureImage2D() {
        return textureImage2D;
    }

    /**
     * get the texture image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param height - the height of the texture image</p>
     * <p>param depth - the depth of the texture image</p>
     * <p>param border - the texture border width</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param pixels - the texel data</p>
     *
     * @return the texture image object
     */
    public TextureImage3D textureImage3D() {
        return textureImage3D;
    }

    /**
     * get the texture image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     *
     * @return the texture image object
     */
    public TextureImageCubeMap textureImageCubeMap() {
        return textureImageCubeMap;
    }

    /**
     * get the texture image object for this texture
     * <p>param layer - the layer of the texture image</p>
     * <p>param level - the level of the texture image</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     *
     * @return the texture image object
     */
    public TextureImageCubeMapArray textureImageCubeMapArray() {
        return textureImageCubeMapArray;
    }

    /**
     * get the texture image object for this texture
     * <p>param samples - the number of samples in the multisample texture</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param height - the height of the texture image</p>
     * <p>param fixedSampleLocations - whether the image uses a fixed sample location</p>
     *
     * @return the texture image object
     */
    public TextureImage2DMultisample textureImage2DMultisample() {
        return textureImage2DMultisample;
    }

    /**
     * get the texture image object for this texture
     * <p>param samples - the number of samples in the multisample texture</p>
     * <p>param internalFormat - the internal format of the texture image. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param width - the width of the texture image</p>
     * <p>param height - the height of the texture image</p>
     * <p>param depth - the depth of the texture image</p>
     * <p>param fixedSampleLocations - whether the image uses a fixed sample location</p>
     *
     * @return the texture image object
     */
    public TextureImage3DMultisample textureImage3DMultisample() {
        return textureImage3DMultisample;
    }

    /**
     * get the texture sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the texel data</p>
     *
     * @return the texture sub image object
     */
    public TextureSubImage1D textureSubImage1D() {
        return textureSubImage1D;
    }

    /**
     * get the texture sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param yOffset - the y offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param height - the height of the texture sub image</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the texel data</p>
     *
     * @return the texture sub image object
     */
    public TextureSubImage2D textureSubImage2D() {
        return textureSubImage2D;
    }

    /**
     * get the texture sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param yOffset - the y offset of the texture sub image</p>
     * <p>param zOffset - the z offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param height - the height of the texture sub image</p>
     * <p>param depth - the depth of the texture sub image</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the texel data</p>
     *
     * @return the texture sub image object
     */
    public TextureSubImage3D textureSubImage3D() {
        return textureSubImage3D;
    }

    /**
     * get the texture sub image object for this texture
     * <p>param face - the face of the texture image. 0-6 refers to {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_X} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_X} {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_Y} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y} {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_Z} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z}</p>
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param yOffset - the y offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param height - the height of the texture sub image</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the texel data</p>
     *
     * @return the texture sub image object
     */
    public TextureSubImageCubeMap textureSubImageCubeMap() {
        return textureSubImageCubeMap;
    }

    /**
     * get the texture sub image object for this texture
     * <p>param layer - the layer of the texture image</p>
     * <p>param face - the face of the texture image. 0-6 refers to {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_X} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_X} {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_Y} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y} {@link GL45#GL_TEXTURE_CUBE_MAP_POSITIVE_Z} {@link GL45#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z}</p>
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param yOffset - the y offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param height - the height of the texture sub image</p>
     * <p>param format - the texel data format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the texel data type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the texel data</p>
     *
     * @return the texture sub image object
     */
    public TextureSubImageCubeMapArray textureSubImageCubeMapArray() {
        return textureSubImageCubeMapArray;
    }

    /**
     * get the copy texture image object for this texture
     *
     * @return the copy texture image object
     */
    public CopyData copyTextureImage() {
        return copyData;
    }

    /**
     * get the get sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param format - the pixel format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the pixel type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the buffer in which to place the returned data</p>
     *
     * @return the get image object
     */
    public GetImage getImage() {
        return getImage;
    }

    /**
     * get the get sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param xoffset - the x-position of the subregion</p>
     * <p>param yoffset - the y-position of the subregion</p>
     * <p>param zoffset - the z-position of the subregion</p>
     * <p>param width - the subregion width</p>
     * <p>param height - the subregion height</p>
     * <p>param depth - the subregion depth</p>
     * <p>param format - the pixel format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the pixel type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the buffer in which to place the returned data</p>
     *
     * @return the get sub image object
     */
    public GetSubImage getSubImage() {
        return getSubImage;
    }

    /**
     * get the copy image object for this texture
     *
     * @return the copy image object
     */
    public CopyImageSubData copyImageSubData() {
        return copyImageSubData;
    }

    /**
     * get the clear image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param format - the pixel format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the pixel type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the buffer in which to place the returned data</p>
     *
     * @return the clear image object
     */
    public ClearImage clearImage() {
        return clearImage;
    }

    /**
     * get the clear sub image object for this texture
     * <p>param level - the level of the texture image</p>
     * <p>param xOffset - the x offset of the texture sub image</p>
     * <p>param yOffset - the y offset of the texture sub image</p>
     * <p>param zOffset - the z offset of the texture sub image</p>
     * <p>param width - the width of the texture sub image</p>
     * <p>param height - the height of the texture sub image</p>
     * <p>param depth - the depth of the texture sub image</p>
     * <p>param format - the pixel format. One of:</p>
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
     * {@link GL45#GL_RGBA32I}, {@link GL45#GL_RGBA32UI}, {@link GL45#GL_COMPRESSED_RED}, {@link GL45#GL_COMPRESSED_RG},
     * {@link GL45#GL_COMPRESSED_RGB}, {@link GL45#GL_COMPRESSED_RGBA}, {@link GL45#GL_COMPRESSED_SRGB},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA}, {@link GL45#GL_COMPRESSED_RED_RGTC1},
     * {@link GL45#GL_COMPRESSED_SIGNED_RED_RGTC1}, {@link GL45#GL_COMPRESSED_RG_RGTC2},
     * {@link GL45#GL_COMPRESSED_SIGNED_RG_RGTC2}, {@link GL45#GL_COMPRESSED_RGBA_BPTC_UNORM},
     * {@link GL45#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM}, {@link GL45#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT},
     * {@link GL45#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}, {@link GL45#GL_COMPRESSED_RGB8_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_ETC2}, {@link GL45#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2},
     * {@link GL45#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}, {@link GL45#GL_COMPRESSED_RGBA8_ETC2_EAC},
     * {@link GL45#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}, {@link GL45#GL_COMPRESSED_R11_EAC}</p>
     * <p>param type - the pixel type. One of:</p>
     * <p>{@link GL45#GL_UNSIGNED_BYTE}, {@link GL45#GL_BYTE}, {@link GL45#GL_HALF_FLOAT}, {@link GL45#GL_FLOAT},
     * {@link GL45#GL_UNSIGNED_BYTE_3_3_2}, {@link GL45#GL_UNSIGNED_BYTE_2_3_3_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_6_5},
     * {@link GL45#GL_UNSIGNED_SHORT_5_6_5_REV}, {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4},
     * {@link GL45#GL_UNSIGNED_SHORT_4_4_4_4_REV}, {@link GL45#GL_UNSIGNED_SHORT_5_5_5_1},
     * {@link GL45#GL_UNSIGNED_SHORT_1_5_5_5_REV}, {@link GL45#GL_UNSIGNED_INT_8_8_8_8},
     * {@link GL45#GL_UNSIGNED_INT_8_8_8_8_REV}, {@link GL45#GL_UNSIGNED_INT_10_10_10_2},
     * {@link GL45#GL_UNSIGNED_INT_2_10_10_10_REV}</p>
     * <p>param data - the buffer in which to place the returned data</p>
     *
     * @return the clear sub image object
     */
    public ClearSubImage clearSubImage() {
        return clearSubImage;
    }

    /**
     * get the mipmap generator object for this texture
     *
     * @return the mipmap generator object
     */
    public Mipmap mipmap() {
        return mipmap;
    }

    //====DELETE====//

    /**
     * delete the texture
     */
    public void delete() {
        glDeleteTextures(id);
    }
}