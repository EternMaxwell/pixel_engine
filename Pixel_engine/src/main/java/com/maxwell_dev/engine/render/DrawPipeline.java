package com.maxwell_dev.engine.render;

import com.maxwell_dev.globj.*;

/**
 * a pipeline class is a class where stores the opengl pipeline data 
 * the function construct() is to actually installs it to the opengl 
 * context 。
 * the data to be store can be informed from the opengl pipeline
 * overview wiki at the krone's group's website
 * 
 * or you can refer to the vulkan pipeline class
 * 
 * contains the shader program , vertex array , vertex buffer , color 
 * blend func , stencil , viewport
 */
public class DrawPipeline extends Pipeline {
    private final Context context;
    private VertexArray vertexArray;
    private Program program;

    private Scissor scissor;
    private Stencil stencil;
    private Blend blend;
    private Depth depth;
    private LogicOp logicOp;
    private FaceCull faceCull;

    private int vertexStride;

    private int vertexSize;
    private int mode;

    public DrawPipeline(Context context) {
        this.context = context;
    }

    public void vertexStride(int vertexStride) {
        this.vertexStride = vertexStride;
    }

    public int vertexStride() {
        return vertexStride;
    }

    public Context context() {
        return context;
    }

    public Program program() {
        return program;
    }

    public void vertexArray(VertexArray vertexArray) {
        this.vertexArray = vertexArray;
    }

    public VertexArray vertexArray() {
        return vertexArray;
    }

    public void program(Program program) {
        this.program = program;
    }

    public Scissor scissor() {
        return scissor;
    }

    public void scissor(Scissor scissor) {
        this.scissor = scissor;
    }

    public Stencil stencil() {
        return stencil;
    }

    public void stencil(Stencil stencil) {
        this.stencil = stencil;
    }

    public Blend blend() {
        return blend;
    }

    public void blend(Blend blend) {
        this.blend = blend;
    }

    public Depth depth() {
        return depth;
    }

    public void depth(Depth depth) {
        this.depth = depth;
    }

    public LogicOp logicOp() {
        return logicOp;
    }

    public void logicOp(LogicOp logicOp) {
        this.logicOp = logicOp;
    }

    public FaceCull faceCull() {
        return faceCull;
    }

    public void faceCull(FaceCull faceCull) {
        this.faceCull = faceCull;
    }

    public int vertexSize() {
        return vertexSize;
    }

    public void vertexSize(int vertexSize) {
        this.vertexSize = vertexSize;
    }

    public int mode() {
        return mode;
    }

    public void mode(int mode) {
        this.mode = mode;
    }

    public void usePipeline() {
        context.program().bind(program);
        context.vertexArray().bind(vertexArray);
        context.scissor().set(scissor);
        context.stencil().set(stencil);
        context.blend().set(blend);
        context.depth().set(depth);
        context.logicOp().set(logicOp);
        context.faceCull().set(faceCull);
    }

    public void dispose() {
        program.delete();
        vertexArray.delete();
    }
}
