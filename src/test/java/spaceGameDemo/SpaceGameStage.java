package spaceGameDemo;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.stage.Stage;
import org.joml.Matrix4f;
import spaceGameDemo.body.BodyFactory;
import spaceGameDemo.body.SpaceBody;

import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class SpaceGameStage extends Stage<Render, InputTool>{
    public Camera camera;
    public SpaceWorld world;

    @Override
    public void init() {
        camera = new Camera();
        world = new SpaceWorld();
        Set<SpaceBody> any = BodyFactory.randomAstroid(5, 0,0,0,new ArrayList<>());
        for(SpaceBody body: any){
            body.createBox2dBody(world, .1f);
            body.body.applyForceToCenter(new org.jbox2d.common.Vec2((float) (Math.random() - .5), (float) (Math.random() - .5)));
//            world.addBody(body);
        }
    }

    @Override
    public void input(InputTool inputTool) {
        float ratio = inputTool.window().ratio();
        camera.projectionOrtho(-ratio * 4, ratio * 4, -4, 4, -1, 1);
        if(inputTool.isKeyPressed(GLFW_KEY_W)){
            camera.move(0, .01f);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_S)){
            camera.move(0, -.01f);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_A)){
            camera.move(-.01f, 0);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_D)){
            camera.move(.01f, 0);
        }
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render(Render renderer) {
        renderer.pixelDrawer.setProjection(camera.cameraMatrix(new Matrix4f()));
        renderer.pixelDrawer.setView(new Matrix4f().identity());
        renderer.pixelDrawer.setModel(new Matrix4f().identity());
        renderer.lineDrawer.setProjection(camera.cameraMatrix(new Matrix4f()));
        renderer.lineDrawer.setView(new Matrix4f().identity());
        renderer.lineDrawer.setModel(new Matrix4f().identity());
        for(SpaceBody body: world.bodies){
            body.render(renderer);
        }
    }

    @Override
    public void dispose() {

    }
}
