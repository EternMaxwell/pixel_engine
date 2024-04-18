package render;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.render.Window;
import com.maxwell_dev.pixel_engine.render.opengl.sample.ImageDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.LineDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelLightDrawer;

import static org.lwjgl.opengl.GL46.*;

public class Render extends Renderer {
    public PixelDrawer pixelDrawer;
    public LineDrawer lineDrawer;
    public ImageDrawer imageDrawer;
    public PixelLightDrawer pixelLightDrawer;
    public Window window;

    public Render(Window window) {
        this.window = window;
        init();
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
//        glLineWidth(0f);
    }

    @Override
    public void begin() {
    }

    @Override
    public void end() {
        pixelDrawer.flush();
        lineDrawer.flush();
    }

    @Override
    public void destroy(){
        pixelDrawer.dispose();
        lineDrawer.dispose();
        imageDrawer.dispose();
    }

    @Override
    public Window window() {
        return window;
    }
}
