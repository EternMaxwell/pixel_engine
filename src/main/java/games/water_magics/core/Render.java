package games.water_magics.core;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.render.Window;
import com.maxwell_dev.pixel_engine.render.opengl.sample.ImageDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.LineDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelLightDrawer;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;

public class Render extends Renderer {
    public PixelDrawer pixelDrawer;
    public LineDrawer lineDrawer;
    public ImageDrawer imageDrawer;
    public PixelLightDrawer pixelLightDrawer;
    private Window window;

    public Render(Window window) {
        this.window = window;
    }

    @Override
    public void init() {
        pixelDrawer = new PixelDrawer();
        lineDrawer = new LineDrawer();
        imageDrawer = new ImageDrawer();
        pixelLightDrawer = new PixelLightDrawer();
        glDisable(GL_MULTISAMPLE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(window.id());
    }

    @Override
    public void destroy() {

    }

    @Override
    public Window window() {
        return window;
    }
}
