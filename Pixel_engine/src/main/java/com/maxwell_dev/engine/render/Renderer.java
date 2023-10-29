package com.maxwell_dev.engine.render;

import com.maxwell_dev.engine.Node;
import com.maxwell_dev.engine.NodeType;
import com.maxwell_dev.globj.Context;

import java.util.LinkedList;
import java.util.List;

public class Renderer extends Node {
    private final Window window;
    private final Context context;
    private final List<Stream> streams;

    protected Renderer(String name, Window window, Context context){
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
}
