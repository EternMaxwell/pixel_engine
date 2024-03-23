package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.render.Renderer;

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
}
