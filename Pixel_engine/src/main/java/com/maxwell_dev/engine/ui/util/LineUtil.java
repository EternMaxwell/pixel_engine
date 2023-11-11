package com.maxwell_dev.engine.ui.util;

import com.maxwell_dev.engine.render.DrawPipeline;
import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.engine.render.streams.VertexRenderStream;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Program;
import com.maxwell_dev.globj.Shader;
import com.maxwell_dev.globj.VertexArray;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class LineUtil {

    private final VertexRenderStream stream;

    public LineUtil(Context context){
        DrawPipeline pipeline = new DrawPipeline(context);
        stream = new VertexRenderStream(pipeline, new Line(), 1024, 2048);
        pipeline.vertexStride(6* Float.BYTES);
        pipeline.vertexArray(new VertexArray());
        Program program = new Program();
        program.vertexShader().attach(new Shader(GL_VERTEX_SHADER, "Pixel_engine/src/main/resources/shaders/line/line.vert"));
        program.fragmentShader().attach(new Shader(GL_FRAGMENT_SHADER, "Pixel_engine/src/main/resources/shaders/line/line.frag"));
        program.linkProgram();
        pipeline.program(program);
        context.vertexArray().bind(pipeline.vertexArray());
        context.arrayBuffer().bind(stream.vertexBuffer());
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 6 * Float.BYTES, 2 * Float.BYTES);
        context.arrayBuffer().bind(null);
        context.vertexArray().bind(null);
        Line.init();
    }

    public void setProjection(Matrix4f projection) {
        Line.setProjection(projection);
    }

    public void setView(Matrix4f view) {
        Line.setView(view);
    }

    public void setModel(Matrix4f model) {
        Line.setModel(model);
    }

    public void drawLine(float x1, float y1, float x2, float y2, float[] color) {
        Line line = new Line(x1, y1, x2, y2, color[0], color[1], color[2], color[3]);
        stream.loadEntity(line);
        stream.drawEntity(line);
        stream.pass();
        stream.removeEntity(line);
    }

    public class Line implements Visible {
        private float x1, y1, x2, y2;
        private float[] color;
        private int index;
        private ByteBuffer vertexBuffer;
        private static ByteBuffer uniformBlock;
        private static boolean changed = true;

        public static void init(){
            uniformBlock = MemoryUtil.memAlloc(48 * Float.BYTES);
            Matrix4f identity = new Matrix4f().identity();
            identity.get(0, uniformBlock);
            identity.get(16 * Float.BYTES, uniformBlock);
            identity.get(32 * Float.BYTES, uniformBlock);
        }

        public static void setProjection(Matrix4f projection) {
            projection.get(0, uniformBlock);
            changed = true;
        }

        public static void setView(Matrix4f view) {
            view.get(16 * Float.BYTES, uniformBlock);
            changed = true;
        }

        public static void setModel(Matrix4f model) {
            model.get(32 * Float.BYTES, uniformBlock);
            changed = true;
        }

        public Line(){}

        public Line(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;

            color = new float[4];
            color[0] = r;
            color[1] = g;
            color[2] = b;
            color[3] = a;

            vertexBuffer = MemoryUtil.memAlloc(6 * Float.BYTES * 2);
        }

        public Line(float x1, float y1, float x2, float y2) {
            this(x1, y1, x2, y2, 1, 1, 1, 1);
        }

        public void setPoints(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public void setColor(float r, float g, float b, float a) {
            color[0] = r;
            color[1] = g;
            color[2] = b;
            color[3] = a;
        }

        /**
         * @return The mode to render in
         */
        @Override
        public int mode() {
            return Visible.super.mode();
        }

        /**
         * @return The vertex buffer
         */
        @Override
        public ByteBuffer vertexBuffer() {
            vertexBuffer.clear().putFloat(x1).putFloat(y1).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
                    .putFloat(x2).putFloat(y2).putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3]);
            vertexBuffer.flip();
            return vertexBuffer;
        }

        /**
         * @return The vertex stride in bytes
         */
        @Override
        public long vertexStride() {
            return 6 * Float.BYTES;
        }

        /**
         * @return The vertex count
         */
        @Override
        public int vertexCount() {
            return 2;
        }

        /**
         * get the render stream this visible object uses
         *
         * @return the render stream
         */
        @Override
        public RenderStream renderStream() {
            return stream;
        }

        /**
         * get the pipeline this visible object uses
         *
         * @return the pipeline
         */
        @Override
        public DrawPipeline pipeline() {
            return stream.pipeline();
        }

        /**
         * get if the uniform buffers are different in different instances of this visible
         *
         * @return if the uniform buffers are different
         */
        @Override
        public boolean differUniformBuffers() {
            return false;
        }

        /**
         * get if the uniform buffer has changed since last load
         *
         * @param index the index of the uniform buffer
         * @return if the uniform buffer is changed
         */
        @Override
        public boolean uniformBufferChanged(int index) {
            boolean result = changed;
            changed = false;
            return result;
        }

        /**
         * get the uniform buffers bytes, the lengths of the uniform buffers per instance of this visible uses
         *
         * @return the uniform buffers bytes
         */
        @Override
        public long[] uniformBuffersBytes() {
            return new long[]{48 * Float.BYTES};
        }

        /**
         * get the uniform buffers
         *
         * @return the uniform buffers
         */
        @Override
        public ByteBuffer[] uniformBuffers() {
            return new ByteBuffer[]{uniformBlock};
        }

        /**
         * get the index of this instance in the render stream
         *
         * @return the index of this instance in the render stream
         */
        @Override
        public int indexInStream() {
            return index;
        }

        /**
         * set the index of this instance in the render stream
         *
         * @param index the index of this instance in the render stream
         */
        @Override
        public void indexInStream(int index) {
            this.index = index;
        }

        /**
         * dispose of this visible
         */
        @Override
        public void dispose() {
            MemoryUtil.memFree(vertexBuffer);
        }
    }
}
