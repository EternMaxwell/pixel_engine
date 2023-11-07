package com.maxwell_dev.engine.render;

import com.maxwell_dev.engine.Node;
import com.maxwell_dev.engine.NodeType;
import com.maxwell_dev.globj.Context;

import static org.lwjgl.opengl.GL46.*;

import java.util.LinkedList;
import java.util.List;

public class Renderer extends Node {
    private final Window window;
    private final Context context;
    private final List<Stream> streams;

    /**
     * Creates a new renderer
     * @param name The name of the renderer
     * @param window The window to render to
     * @param context The context to render to
     */
    public Renderer(String name, Window window, Context context){
        super(NodeType.RENDERER, name);
        this.window = window;
        this.context = context;
        streams = new LinkedList<>();
    }

    /**
     * Adds a stream to the renderer
     * @param stream The stream to add
     * @return The renderer
     */
    public Renderer addStream(Stream stream) {
        streams.add(stream);
        return this;
    }

    public Renderer addStream(List<? extends Stream> streams) {
        for (Stream stream : streams) {
            addStream(stream);
        }
        return this;
    }

    /**
     * Removes a stream from the renderer
     * @param stream The stream to remove
     * @return The renderer
     */
    public Renderer removeStream(Stream stream) {
        streams.remove(stream);
        return this;
    }

    public Window getWindow() {
        return window;
    }

    public Context getContext() {
        return context;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    /**
     * Executes all streams
     */
    public void executeStreams(){
        for (Stream stream : streams) {
            stream.pass();
        }
    }

    /**
     * Swaps the buffers and clears the screen
     */
    public void endFrame(){
        window.swapBuffers();
        context.clearBit().set(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT).clear();;
    }
}
