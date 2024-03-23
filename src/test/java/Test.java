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
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input();
        if (inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
    }

    @Override
    public void update() {
        long start = System.nanoTime();
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1e6);
    }

    @Override
    public void render() {
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
