package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL45.*;

public class Program implements glInterface {
    private final int id;
    private final ShaderAttachment vertexShader, fragmentShader, geometryShader, tessControlShader, tessEvalShader;

    public class ShaderAttachment {
        private Shader shader;

        /**
         * attach a shader to the program
         *
         * @param shader the shader to attach
         */
        public void attach(Shader shader) {
            this.shader = shader;
            glAttachShader(id, shader.id());
        }

        /**
         * get the shader attached to the program
         *
         * @return the shader attached to the program
         */
        public Shader get() {
            return shader;
        }
    }

    /**
     * construct a new shader program
     */
    public Program() {
        id = glCreateProgram();

        vertexShader = new ShaderAttachment();
        fragmentShader = new ShaderAttachment();
        geometryShader = new ShaderAttachment();
        tessControlShader = new ShaderAttachment();
        tessEvalShader = new ShaderAttachment();
    }

    /**
     * get the shader program id
     *
     * @return the id
     */
    public int id() {
        return id;
    }

    /**
     * get the vertex shader attachment
     * @return the vertex shader attachment
     */
    public ShaderAttachment vertexShader() {
        return vertexShader;
    }

    /**
     * get the fragment shader attachment
     * @return the fragment shader attachment
     */
    public ShaderAttachment fragmentShader() {
        return fragmentShader;
    }

    /**
     * get the geometry shader attachment
     * @return the geometry shader attachment
     */
    public ShaderAttachment geometryShader() {
        return geometryShader;
    }

    /**
     * get the tessellation control shader attachment
     * @return the tessellation control shader attachment
     */
    public ShaderAttachment tessControlShader() {
        return tessControlShader;
    }

    /**
     * get the tessellation evaluation shader attachment
     * @return the tessellation evaluation shader attachment
     */
    public ShaderAttachment tessEvalShader() {
        return tessEvalShader;
    }

    /**
     * link the program
     */
    public void linkProgram() {
        glLinkProgram(id);
    }

    /**
     * delete the program object
     */
    public void delete() {
        glDeleteProgram(id);
    }
}
