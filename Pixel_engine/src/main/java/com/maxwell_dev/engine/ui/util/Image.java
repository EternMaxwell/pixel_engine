package com.maxwell_dev.engine.ui.util;

import com.maxwell_dev.engine.render.DrawPipeline;
import com.maxwell_dev.engine.render.Pipeline;
import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.engine.render.streams.VertexRenderStream;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Program;
import com.maxwell_dev.globj.Sampler;
import com.maxwell_dev.globj.Shader;
import com.maxwell_dev.globj.texture.Texture_2D;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Image implements Visible {
    protected Texture_2D texture;
    protected Sampler sampler;
    protected BufferedImage image;
    private float x;
    private float y;
    private float width;
    private float height;
    private float[] color;
    private ByteBuffer uniformBlock;

    private ByteBuffer vertexBuffer;

    public static VertexRenderStream stream;

    public static void init(Context context){
        DrawPipeline pipeline = new DrawPipeline(context);
        stream = new VertexRenderStream(pipeline, new Image(), 1, 6);
        Program program = new Program();
        program.vertexShader().attach( new Shader(GL_VERTEX_SHADER, "Pixel_engine/src/main/resources/shaders/image/image.vert"));
        program.fragmentShader().attach( new Shader(GL_FRAGMENT_SHADER, "Pixel_engine/src/main/resources/shaders/image/image.frag"));
        program.linkProgram();
        pipeline.program(program);
        pipeline.vertexArray(new com.maxwell_dev.globj.VertexArray());
        pipeline.vertexStride(8 * Float.BYTES);
        com.maxwell_dev.globj.Buffer temp = new com.maxwell_dev.globj.Buffer();
        context.vertexArray().bind(pipeline.vertexArray());
        context.arrayBuffer().bind(stream.vertexBuffer());
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * Float.BYTES, 4 * Float.BYTES);
        context.arrayBuffer().bind(null);
        context.vertexArray().bind(null);
    }

    private Image(){}

    public Image(BufferedImage image){
        texture = new Texture_2D();
        x = 0;
        y = 0;
        this.image = image;
        ByteBuffer imageData = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * 4);
        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                int color = image.getRGB(x, y);
                imageData.put((byte) ((color >> 16) & 0xFF));
                imageData.put((byte) ((color >> 8) & 0xFF));
                imageData.put((byte) (color & 0xFF));
                imageData.put((byte) ((color >> 24) & 0xFF));
            }
        }
        texture.textureImage().image(1, GL_RGBA32F, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        color = new float[]{1,1,1,1};
        vertexBuffer = MemoryUtil.memAlloc(6 * 8 * Float.BYTES);
        uniformBlock = MemoryUtil.memAlloc(16 * 3 * Float.BYTES);
        Matrix4f identity = new Matrix4f().identity();
        identity.get(0, uniformBlock);
        identity.get(16 * Float.BYTES, uniformBlock);
        identity.get(32 * Float.BYTES, uniformBlock);
        sampler = new Sampler();
        sampler.param_textureMinFilter().set(GL_NEAREST);
        sampler.param_textureMagFilter().set(GL_NEAREST);
        sampler.param_textureWrapS().set(GL_CLAMP_TO_EDGE);
        sampler.param_textureWrapT().set(GL_CLAMP_TO_EDGE);
    }

    /**
     * set the image of the image
     * @param image the image
     * @param bottomLeft whether the image should be flipped
     */
    public void setImage(BufferedImage image, boolean bottomLeft){
        this.image = image;
        if(!bottomLeft){
            //flip the image using a temporary image
            BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            for(int x = 0; x < image.getWidth(); x++){
                for(int y = 0; y < image.getHeight(); y++){
                    temp.setRGB(x, y, image.getRGB(x, image.getHeight() - y - 1));
                }
            }
            this.image = temp;
        }
        ByteBuffer imageData = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * 4);
        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                int color = image.getRGB(x, y);
                imageData.put((byte) ((color >> 16) & 0xFF));
                imageData.put((byte) ((color >> 8) & 0xFF));
                imageData.put((byte) (color & 0xFF));
                imageData.put((byte) ((color >> 24) & 0xFF));
            }
        }
        texture.textureImage().image(1, GL_RGBA32F, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
    }

    /**
     * set the projection matrix
     * @param projection the projection matrix
     */
    public void setProjection(Matrix4f projection){
        projection.get(0, uniformBlock);
    }

    /**
     * set the view matrix
     * @param view the view matrix
     */
    public void setView(Matrix4f view){
        view.get(16 * Float.BYTES, uniformBlock);
    }

    /**
     * set the model matrix
     * @param model the model matrix
     */
    public void setModel(Matrix4f model){
        model.get(32 * Float.BYTES, uniformBlock);
    }

    /**
     * set the position of the rectangle
     * @param x the x position
     * @param y the y position
     */
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * set the color of the rectangle
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @param a the alpha value
     */
    public void setColor(float r, float g, float b, float a){
        color = new float[]{r,g,b,a};
    }

    /**
     * set the height of the rectangle
     * @param height the height of the rectangle
     */
    public void setHeight(float height){
        this.height = height;
    }

    /**
     * set the width of the rectangle
     * @param width the width of the rectangle
     */
    public void setWidth(float width){
        this.width = width;
    }

    /**
     * set the size of the rectangle
     * @param height the height of the rectangle
     * @param width the width of the rectangle
     */
    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
    }

    /**
     * get the draw mode of the rectangle
     * @return the draw mode of the rectangle
     */
    @Override
    public int mode() {
        return GL_TRIANGLES;
    }

    @Override
    public ByteBuffer indexBuffer() {
        return null;
    }

    @Override
    public int indexBufferType() {
        return 0;
    }

    @Override
    public int indexBufferBytes() {
        return 0;
    }

    /**
     * get the vertices of the rectangle
     * @return the vertices of the rectangle, in counter-clockwise order
     */
    @Override
    public ByteBuffer vertexBuffer() {
        vertexBuffer.clear().putFloat(x).putFloat(y).putFloat(0).putFloat(0).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                .putFloat(x+width).putFloat(y).putFloat(0).putFloat(1).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                .putFloat(x+width).putFloat(y+height).putFloat(0).putFloat(2).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                .putFloat(x).putFloat(y).putFloat(0).putFloat(0).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                .putFloat(x+width).putFloat(y+height).putFloat(0).putFloat(2).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                .putFloat(x).putFloat(y+height).putFloat(0).putFloat(3).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3]);
        vertexBuffer.flip();
        return vertexBuffer;
    }

    @Override
    public long vertexStride() {
        return 8 * Float.BYTES;
    }

    @Override
    public int vertexCount() {
        return 6;
    }

    @Override
    public RenderStream renderStream() {
        return stream;
    }

    @Override
    public DrawPipeline pipeline() {
        return stream.pipeline();
    }

    @Override
    public boolean differStorageBuffer() {
        return Visible.super.differStorageBuffer();
    }

    @Override
    public boolean storageBufferChanged() {
        return false;
    }

    @Override
    public boolean storageBufferChanged(int index) {
        return false;
    }

    @Override
    public boolean differStorageBuffers() {
        return Visible.super.differStorageBuffers();
    }

    @Override
    public long storageBufferBytes() {
        return Visible.super.storageBufferBytes();
    }

    @Override
    public long[] storageBuffersBytes() {
        return Visible.super.storageBuffersBytes();
    }

    @Override
    public ByteBuffer shaderStorageBuffer() {
        return Visible.super.shaderStorageBuffer();
    }

    @Override
    public ByteBuffer[] shaderStorageBuffers() {
        return Visible.super.shaderStorageBuffers();
    }

    @Override
    public boolean differUniformBuffer() {
        return Visible.super.differUniformBuffer();
    }

    @Override
    public boolean differUniformBuffers() {
        return Visible.super.differUniformBuffers();
    }

    @Override
    public boolean uniformBufferChanged() {
        return false;
    }

    @Override
    public boolean uniformBufferChanged(int index) {
        return true;
    }

    @Override
    public long uniformBufferBytes() {
        return Visible.super.uniformBufferBytes();
    }

    @Override
    public long[] uniformBuffersBytes() {
        return new long[]{16 * 3 * Float.BYTES};
    }

    @Override
    public ByteBuffer uniformBuffer() {
        return Visible.super.uniformBuffer();
    }

    @Override
    public ByteBuffer[] uniformBuffers() {
        return new ByteBuffer[]{uniformBlock};
    }

    @Override
    public int indexInStream() {
        return 0;
    }

    @Override
    public void indexInStream(int index) {}

    @Override
    public void dispose() {
        texture.delete();
        MemoryUtil.memFree(vertexBuffer);
    }

    public void draw(){
        stream.loadEntity(this);
        stream.drawEntity(this);
        stream.textureUnit(0).setTexture(texture);
        stream.textureUnit(0).setSampler(sampler);
        stream.pass();
        stream.removeEntity(this);
    }
}
