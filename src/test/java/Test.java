import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.util.Util;
import com.maxwell_dev.pixel_engine.world.box2d.sample.ElementBody;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.ElementPlaceHolder;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application {
    Window window;
    InputTool inputTool;
    LineDrawer lineDrawer;
    World world = new World(new org.jbox2d.common.Vec2(0, -9.8f));
    Map<Body, List<Vec2>> bodyVertices = new HashMap<>();
    Element hold = ElementPlaceHolder.PLACEHOLDER;

    Element[][] elementsGrid = new Element[][]{
            {hold, hold, hold, null, null, null, null, null, null, null},
            {hold, null, hold, hold, hold, hold, hold, null, null, null},
            {hold, null, null, null, hold, null, hold, null, null, null},
            {hold, hold, hold, hold, hold, null, hold, null, null, null},
            {null, null, null, hold, hold, hold, hold, null, null, null},
            {null, null, null, hold, null, null, hold, null, null, null},
            {null, null, null, hold, hold, hold, hold, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null}
    };

    @Override
    public void init() {
        if(!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        window = new Window(800, 600, "Test");
        window.setContextVersionMajor(4);
        window.setContextVersionMinor(6);
        window.createWindow();
        glfwMakeContextCurrent(window.id());
        GL.createCapabilities();
        inputTool = new InputTool(window);
        lineDrawer = new LineDrawer();
        glfwSetWindowSizeCallback(window.id(), (window, width, height) -> {
            glViewport(0, 0, width, height);
            render();
        });

        BodyDef boundaryDef = new BodyDef();
        boundaryDef.position.set(0, -0.5f);
        boundaryDef.type = BodyType.STATIC;
        Body boundary = world.createBody(boundaryDef);
        PolygonShape boundaryShape = new PolygonShape();
        boundaryShape.setAsBox(20, 01f);
        boundary.createFixture(boundaryShape, 0.0f);
    }

    private void createBodyAt(float x, float y) {
        ElementBody<Element> elementBody;
        List<Vec2> vertices = new LinkedList<>();
        elementBody = new ElementBody<>(elementsGrid);
        elementBody.createBox2dBody(world, x, y, 0, vertices, 0.05f);
        bodyVertices.put(elementBody.body, vertices);
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input();
        if (inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_1)) {
            createBodyAt((float) inputTool.mouseX() * 10, (float) inputTool.mouseY() * 10);
        }
    }

    @Override
    public void update() {
        long start = System.nanoTime();
        world.step(1/60f, 8, 3);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6);
    }

    @Override
    public void render() {
        lineDrawer.setProjection(new Matrix4f().ortho(-window.ratio(), window.ratio(), -1, 1, -1, 1));
        lineDrawer.setView(new Matrix4f().ortho(-10, 10, -10, 10, -1, 1));
        lineDrawer.setModel(new Matrix4f().identity());
        for(Map.Entry<Body, List<Vec2>> entry: bodyVertices.entrySet()){
            Body body = entry.getKey();
            List<Vec2> vertices = entry.getValue();
            for (int i = 0; i < vertices.size(); i++) {
                Vec2 v1 = vertices.get(i);
                Vec2 v2 = vertices.get((i + 1) % vertices.size());
                Transform transform = body.getTransform();
                Vec2 v1t = new Vec2();
                Vec2 v2t = new Vec2();
                Transform.mulToOut(transform, v1, v1t);
                Transform.mulToOut(transform, v2, v2t);
                if(body.isAwake()) {
                    lineDrawer.draw(v1t.x, v1t.y, v2t.x, v2t.y, 1, 1, 1, 1);
                }else {
                    lineDrawer.draw(v1t.x, v1t.y, v2t.x, v2t.y, 1, 0, 0, 1);

                }
            }
        }
        lineDrawer.flush();
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        window.dispose();
        lineDrawer.dispose();
        glfwTerminate();
    }

    @Override
    public boolean running() {
        return !glfwWindowShouldClose(window.id());
    }

    @Override
    public void pause() {

    }
}
