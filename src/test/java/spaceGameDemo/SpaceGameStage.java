package spaceGameDemo;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.stage.Stage;
import org.jbox2d.common.Vec2;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import spaceGameDemo.body.BodyFactory;
import spaceGameDemo.body.SpaceBody;

import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;
import java.util.ArrayList;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class SpaceGameStage extends Stage<Render, InputTool>{
    public Camera camera;
    public SpaceWorld world;
    public SpaceBody target;
    public Image background;

    @Override
    public void init() {
        camera = new Camera();
        world = new SpaceWorld();
//        Set<SpaceBody> any = BodyFactory.random(200, -1,-1,0,new ArrayList<>(), 0.5f);
//        for(SpaceBody body: any){
//            body.createBox2dBody(world, .1f);
//            body.body.applyForceToCenter(new org.jbox2d.common.Vec2((float) (Math.random() - .5)*10, (float) (Math.random() - .5)*10));
////            world.addBody(body);
//        }
        SpaceBody asteroid = BodyFactory.circleStone(10, 0, 0, 0, new ArrayList<>());
        asteroid.createBox2dBody(world, .1f);
        target = BodyFactory.square(10, -2, -2, 0, new ArrayList<>());
        target.createBox2dBody(world, .1f);

        int backgroundTexture = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(backgroundTexture, 1, GL_RGBA8, 1024, 1024);
        ByteBuffer buffer = MemoryUtil.memAlloc(1024 * 1024 * 4);
        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++) {
                buffer.put((byte) (Math.random() * 10));
                buffer.put((byte) (Math.random() * 10));
                buffer.put((byte) (Math.random() * 255));
                buffer.put((byte) 255);
            }
        }
        buffer.flip();
        glTextureSubImage2D(backgroundTexture, 0, 0, 0, 1024, 1024, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        MemoryUtil.memFree(buffer);
        background = new Image(backgroundTexture);
//        background = new Image("src/test/resources/textures/test.jpg");
        background.samplerParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        background.samplerParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        background.samplerParameteri(GL_TEXTURE_WRAP_S, GL_REPEAT);
        background.samplerParameteri(GL_TEXTURE_WRAP_T, GL_REPEAT);
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
        for(SpaceBody body: world.bodies){
            for(SpaceBody body1: world.bodies){
                if(body != body1){
                    Vec2 diff = body.body.getWorldCenter().sub(body1.body.getWorldCenter());
                    float distance = diff.length();
                    if(body.body.getMass() > body1.body.getMass()/1000){
                        diff.normalize();
                        diff = diff.mul(1/distance/distance).mul(body.body.getMass() * body1.body.getMass());
                        body1.body.applyForceToCenter(diff.mul(.05f));
                    }
                }
            }
        }
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
        renderer.imageDrawer.setProjection(camera.cameraMatrix(target.getCenterX(), target.getCenterY(), target.getAngle(), 1, new Matrix4f()));
        renderer.imageDrawer.setView(new Matrix4f().identity());
        renderer.imageDrawer.setModel(new Matrix4f().identity());
        renderer.imageDrawer.draw(background, -1000, -1000, 2000, 2000, 0, 0, 10, 10, 0.2f, 0.2f, 0.2f, 1);
        for(SpaceBody body: world.bodies){
            body.render(renderer);
        }
        System.out.println("target pos: " + target.getCenterX() + " " + target.getCenterY());
    }

    @Override
    public void dispose() {

    }
}
