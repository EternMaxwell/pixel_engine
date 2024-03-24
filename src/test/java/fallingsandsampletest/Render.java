package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.render.Renderer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.ImageDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.LineDrawer;
import com.maxwell_dev.pixel_engine.render.opengl.sample.PixelDrawer;

public class Render extends Renderer {
    public PixelDrawer pixelDrawer;
    public LineDrawer lineDrawer;
    public ImageDrawer imageDrawer;

    public Render() {
        init();
    }

    @Override
    public void init() {
        pixelDrawer = new PixelDrawer();
        lineDrawer = new LineDrawer();
        imageDrawer = new ImageDrawer();
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
}
