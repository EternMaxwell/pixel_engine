package com.maxwell_dev.pixel_engine.render.opengl;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    public final int id;
    public final int type;

    public static class Type {
        public static final int VERTEX = GL_VERTEX_SHADER;
        public static final int FRAGMENT = GL_FRAGMENT_SHADER;
        public static final int GEOMETRY = GL_GEOMETRY_SHADER;
        public static final int TESS_CONTROL = GL_TESS_CONTROL_SHADER;
        public static final int TESS_EVALUATION = GL_TESS_EVALUATION_SHADER;
        public static final int COMPUTE = GL_COMPUTE_SHADER;
    }

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
    private static @NotNull String sourceFromFile(String filePath){
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
