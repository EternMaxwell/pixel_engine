package com.maxwell_dev.pixel_engine.render.opengl;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram {
    public final int id;

    /**
     * create a new shader program
     */
    public ShaderProgram(){
        id = glCreateProgram();
    }

    /**
     * create a new shader program
     * @param types the types of shaders
     * @param shaders the shaders
     */
    public ShaderProgram(int[] types, String[] shaders){
        id = glCreateProgram();
        for(int i = 0; i < types.length; i++){
            attach(new Shader(types[i], shaders[i]));
        }
        link();
    }

    /**
     * attach a shader to the shader program
     * @param shader the shader to attach
     */
    public void attach(Shader shader){
        glAttachShader(id, shader.id);
    }

    /**
     * link the shader program
     */
    public void link(){
        glLinkProgram(id);
        if(glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE){
            System.err.println("Shader program failed to link!");
            System.err.println(glGetProgramInfoLog(id));
        }
    }

    /**
     * use the shader program
     */
    public void use(){
        glUseProgram(id);
    }

    /**
     * dispose of the shader program
     */
    public void dispose(){
        glDeleteProgram(id);
    }
}
