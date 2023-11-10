package com.maxwell_dev.engine.ui.util;

import com.maxwell_dev.engine.render.DrawPipeline;
import com.maxwell_dev.engine.render.Pipeline;
import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.engine.render.streams.VertexRenderStream;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Program;
import com.maxwell_dev.globj.Shader;
import com.maxwell_dev.globj.texture.Texture_2D;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Image implements Visible {
    protected Texture_2D texture;
    protected BufferedImage image;
    private float x;
    private float y;
    private float width;
    private float height;
    private float[] color;

    private ByteBuffer vertexBuffer;

    private static VertexRenderStream stream;

    public static void init(Context context){
        DrawPipeline pipeline = new DrawPipeline(context);
        Program program = new Program();
        program.vertexShader().attach( new Shader(GL_VERTEX_SHADER, "Pixel_engine/src/main/resources/shaders/image/image.vert"));
        program.fragmentShader().attach( new Shader(GL_FRAGMENT_SHADER, "Pixel_engine/src/main/resources/shaders/image/image.frag"));
        program.linkProgram();
        pipeline.program(program);
        pipeline.vertexArray(new com.maxwell_dev.globj.VertexArray());
        pipeline.vertexStride(8 * Float.BYTES);
        com.maxwell_dev.globj.Buffer temp = new com.maxwell_dev.globj.Buffer();
        context.vertexArray().bind(pipeline.vertexArray());
        context.arrayBuffer().bind(temp);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * Float.BYTES, 4 * Float.BYTES);
        context.arrayBuffer().bind(null);
        context.vertexArray().bind(null);

        stream = new VertexRenderStream(pipeline, new Image(), 1, 6);
    }

    private Image(){}

    public Image(BufferedImage image){
        texture = new Texture_2D();
        x = 0;
        y = 0;
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        color = new float[]{1,1,1,1};
        vertexBuffer = MemoryUtil.memAlloc(6 * 8 * Float.BYTES);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setColor(float r, float g, float b, float a){
        color = new float[]{r,g,b,a};
    }

    public void setHeight(float height){
        this.height = height;
    }
    public void setWidth(float width){
        this.width = width;
    }

    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
    }


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
        return 0;
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
        return false;
    }

    @Override
    public long uniformBufferBytes() {
        return Visible.super.uniformBufferBytes();
    }

    @Override
    public long[] uniformBuffersBytes() {
        return Visible.super.uniformBuffersBytes();
    }

    @Override
    public ByteBuffer uniformBuffer() {
        return Visible.super.uniformBuffer();
    }

    @Override
    public ByteBuffer[] uniformBuffers() {
        return Visible.super.uniformBuffers();
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
        stream.pass();
        stream.removeEntity(this);
    }
}
