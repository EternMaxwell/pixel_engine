package com.maxwell_dev.globj;

import java.io.*;

import static org.lwjgl.opengl.GL45.*;

public class Shader implements glInterface {
    private final int id;
    private final int type;

    /**
     * construct a shader.
     *
     * @param type the shader type to create
     */
    public Shader(int type) {
        this.type = type;
        id = glCreateShader(this.type);
    }

    /**
     * create a shader from the source file
     *
     * @param type the shader type
     * @param path the source file path
     */
    public Shader(int type, String path) {
        this(type);
        sourceFile(path);
        compileShader();
    }

    /**
     * get the shader id
     *
     * @return the shader id
     */
    public int id() {
        return id;
    }

    /**
     * get the shader type
     *
     * @return the shader type
     */
    public int type() {
        return type;
    }

    /**
     * stuff the shader with the source string
     *
     * @param source the source string
     */
    public void shaderSource(String source) {
        glShaderSource(id, source);
    }

    /**
     * get the source from the source file
     *
     * @param path the source file path
     */
    public void sourceFile(String path) {
        String source = readChars(path);
        shaderSource(source);
    }

    /**
     * delete the shader object
     */
    public void delete() {
        glDeleteShader(id);
    }

    /**
     * compile the shader
     */
    public void compileShader() {
        glCompileShader(id);
    }

    /**
     * Read the characters in the file
     *
     * @param path the file path
     * @return the char sequence
     */
    private static String readChars(String path) {
        StringBuilder builder = new StringBuilder();

        try (InputStream in = new FileInputStream(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load a shader file!"
                    + System.lineSeparator() + ex.getMessage());
        }
        String source = builder.toString();
        return source;
    }
}
