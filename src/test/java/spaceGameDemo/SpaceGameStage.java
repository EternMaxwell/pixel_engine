package spaceGameDemo;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.stage.Stage;
import org.jbox2d.common.Vec2;
import org.joml.Matrix4f;
import spaceGameDemo.body.BodyFactory;
import spaceGameDemo.body.SpaceBody;

import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class SpaceGameStage extends Stage<Render, InputTool>{
    public Camera camera;
    public SpaceWorld world;
    public SpaceBody target;

    @Override
    public void init() {
        camera = new Camera();
        world = new SpaceWorld();
        Set<SpaceBody> any = BodyFactory.randomAstroid(20, 0,0,0,new ArrayList<>());
        for(SpaceBody body: any){
            body.createBox2dBody(world, .1f);
            body.body.applyForceToCenter(new org.jbox2d.common.Vec2((float) (Math.random() - .5)*10, (float) (Math.random() - .5)*10));
//            world.addBody(body);
        }
        target = BodyFactory.square(10, -2, -2, 0, new ArrayList<>());
        target.createBox2dBody(world, .1f);
    }

    @Override
    public void input(InputTool inputTool) {
        float ratio = inputTool.window().ratio();
        camera.projectionOrtho(-ratio * 4, ratio * 4, -4, 4, -1, 1);
        if(inputTool.isKeyPressed(GLFW_KEY_W)){
            target.body.applyForceToCenter(new Vec2((float) -Math.sin(target.body.getAngle()), (float) Math.cos(target.body.getAngle())));
        }
        if(inputTool.isKeyPressed(GLFW_KEY_S)){
            target.body.applyForceToCenter(new Vec2((float) Math.sin(target.body.getAngle()), (float) -Math.cos(target.body.getAngle())));
        }
        if(inputTool.isKeyPressed(GLFW_KEY_A)){
            target.body.applyForceToCenter(new Vec2((float) -Math.cos(target.body.getAngle()), (float) -Math.sin(target.body.getAngle())));
        }
        if(inputTool.isKeyPressed(GLFW_KEY_D)){
            target.body.applyForceToCenter(new Vec2((float) Math.cos(target.body.getAngle()), (float) Math.sin(target.body.getAngle())));
        }
        if(inputTool.isKeyPressed(GLFW_KEY_Q)){
            target.body.applyTorque(1);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_E)){
            target.body.applyTorque(-1);
        }
    }

    @Override
    public void update() {
        world.update();
        target.body.setAngularDamping(.5f);
        target.body.setLinearDamping(.1f);
    }

    @Override
    public void render(Render renderer) {
        renderer.pixelDrawer.setProjection(camera.cameraMatrix(target.getCenterX(), target.getCenterY(), target.getAngle(), 1, new Matrix4f()));
        renderer.pixelDrawer.setView(new Matrix4f().identity());
        renderer.pixelDrawer.setModel(new Matrix4f().identity());
        renderer.lineDrawer.setProjection(camera.cameraMatrix(target.getCenterX(), target.getCenterY(), target.getAngle(), 1, new Matrix4f()));
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
