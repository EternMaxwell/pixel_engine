import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.util.Util;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Test extends Application {
    Window window;
    InputTool inputTool;
    ImageDrawer imageDrawer;
    Image image;
    LineDrawer lineDrawer;
    float height = 0.0f;
    float[][] line_to_simplify = new float[][]{
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()},
            {(float) Math.random(), (float) Math.random()}
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
        imageDrawer = new ImageDrawer();
        lineDrawer = new LineDrawer();
        image = new Image("src/test/resources/textures/test.jpg");
        image.samplerParameteri(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        image.samplerParameteri(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        image.samplerParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        image.samplerParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glfwSetWindowSizeCallback(window.id(), (window, width, height) -> {
            glViewport(0, 0, width, height);
            render();
        });
    }

    @Override
    public void input() {
        glfwPollEvents();
        inputTool.input();
    }

    @Override
    public void update() {
        float[][] simplified;
        if(inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
        if(inputTool.isKeyJustPressed(GLFW_KEY_SPACE)) {
            simplified = Util.line_simplification(line_to_simplify, height);
            height += 0.1f;
            line_to_simplify = simplified;
            System.out.println("Simplified");
        }
    }

    @Override
    public void render() {

        //imageDrawer.draw(image);
        for(int i = 0; i < line_to_simplify.length - 1; i++) {
            lineDrawer.draw(line_to_simplify[i][0], line_to_simplify[i][1], line_to_simplify[i + 1][0], line_to_simplify[i + 1][1], 1.0f, 1,1, 1.0f);
        }
        glfwSwapBuffers(window.id());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        window.dispose();
        image.dispose();
        imageDrawer.dispose();
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
