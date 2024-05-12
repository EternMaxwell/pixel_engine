package games.water_magics.core;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.render.Window;
import com.maxwell_dev.pixel_engine.render.opengl.sample.ImageDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.LineDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelLightDrawer;
import games.water_magics.renderPipelines.CircleLightDrawer;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;

public class Render extends Renderer {
    public PixelDrawer pixelDrawer;
    public LineDrawer lineDrawer;
    public ImageDrawer imageDrawer;
    public CircleLightDrawer circleLightDrawer;
    private Window window;

    public Render(Window window) {
        this.window = window;
    }

    @Override
    public void init() {
        pixelDrawer = new PixelDrawer();
        lineDrawer = new LineDrawer();
        imageDrawer = new ImageDrawer();
        circleLightDrawer = new CircleLightDrawer();
        glDisable(GL_MULTISAMPLE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        lineDrawer.dispose();
        pixelDrawer.dispose();
        imageDrawer.dispose();
    }

    @Override
    public Window window() {
        return window;
    }
}
