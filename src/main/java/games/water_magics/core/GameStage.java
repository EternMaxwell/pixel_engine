package games.water_magics.core;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.stage.Stage;
import org.joml.Matrix4f;

public class GameStage extends Stage<Render, InputTool> {
    Camera camera = new Camera();
    @Override
    public void init() {
        camera.setScale(540);
    }

    @Override
    public void input(InputTool inputTool) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Render renderer) {
        camera.projectionOrtho(-renderer.window().ratio(), renderer.window().ratio(), -1, 1, -1, 1);
        renderer.circleLightDrawer.setView(camera.viewMatrix(new Matrix4f()));
        renderer.circleLightDrawer.setProjection(camera.projectionMatrix(new Matrix4f()));
        renderer.circleLightDrawer.setModel(new Matrix4f().identity());
        renderer.pixelDrawer.setModel(new Matrix4f().identity());
        renderer.pixelDrawer.setProjection(new Matrix4f().identity());
        renderer.pixelDrawer.setView(new Matrix4f().identity());
//        renderer.pixelDrawer.draw(0,0,1,1,1,1,1);
//        renderer.pixelDrawer.flush();
        renderer.circleLightDrawer.drawLightMap(0,0,0,0,1,1,1,1,1);
        renderer.circleLightDrawer.drawLightMap(100,100, 0,0,1,1,1,1,1);
    }

    @Override
    public void dispose() {

    }
}
