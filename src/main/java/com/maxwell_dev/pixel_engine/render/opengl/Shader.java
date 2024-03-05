package com.maxwell_dev.pixel_engine.render.opengl;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    public final int id;
    public final int type;

    /**
     * create a new shader
     * @param type the type of shader
     * @param filePath the path to the file
     */
    public Shader(int type, String filePath){
        this.type = type;
        id = glCreateShader(type);
        String source = sourceFromFile(filePath);
        glShaderSource(id, source);
        glCompileShader(id);
        if(glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE){
            System.err.printf("Shader at %s failed to compile!\n", filePath);
            System.err.println(glGetShaderInfoLog(id));
        }
    }

    /**
     * read the source from a file
     * @param filePath the path to the file
     * @return the source
     */
    private @NotNull String sourceFromFile(String filePath){
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine()) != null){
                source.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return source.toString();
    }

    /**
     * dispose of the shader
     */
    public void dispose(){
        glDeleteShader(id);
    }
}
