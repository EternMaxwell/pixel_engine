package com.maxwell_dev.pixel_engine.render.opengl.sample;

import com.maxwell_dev.pixel_engine.render.opengl.FrameBuffer;
import com.maxwell_dev.pixel_engine.render.opengl.Pipeline;
import com.maxwell_dev.pixel_engine.render.opengl.Shader;

public class PixelLightDrawer extends Pipeline {

    public PixelLightDrawer(FrameBuffer frameBuffer) {
        super(frameBuffer, 1024);
    }

    /**
     * initialize the pipeline
     * <p>Need to be implemented by the user</p>
     */
    @Override
    public void init() {
        Shader vertex = new Shader(Shader.Type.VERTEX, "src/main/resources/shaders/light/shader.vsh");
        Shader geometry = new Shader(Shader.Type.GEOMETRY, "src/main/resources/shaders/light/shader.geom");
        Shader fragment = new Shader(Shader.Type.FRAGMENT, "src/main/resources/shaders/light/shader.fsh");
        program.attach(vertex);
        program.attach(geometry);
        program.attach(fragment);
        program.link();
    }
}
