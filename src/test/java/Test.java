import com.maxwell_dev.pixel_engine.core.Application;
import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.util.Util;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.lwjgl.opengl.GL;

import java.util.Arrays;
import java.util.Set;

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

    Object name = new Object();

    Object[][] grid = new Object[][]{
            {name, name, name, null, null, null, null, null, null, null},
            {name, null, name, name, name, name, name, null, null, null},
            {name, null, null, null, name, null, name, null, null, null},
            {name, name, name, name, name, null, name, null, null, null},
            {null, null, null, name, name, name, name, null, null, null},
            {null, null, null, name, null, null, name, null, null, null},
            {null, null, null, name, name, name, name, null, null, null},
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
        if (inputTool.isKeyPressed(GLFW_KEY_ESCAPE))
            glfwSetWindowShouldClose(window.id(), true);
        if (inputTool.scrollY() > 0) {
            height += 0.01f;
        }
        if (inputTool.scrollY() < 0) {
            height -= 0.01f;
        }
        height = height < 0 ? 0 : height;
        height = height > 0.2f ? 0.2f : height;
    }

    @Override
    public void render() {

//        imageDrawer.draw(image);
//        for(int i = 0; i < line_to_simplify.length - 1; i++) {
//            lineDrawer.draw(line_to_simplify[i][0], line_to_simplify[i][1], line_to_simplify[i + 1][0], line_to_simplify[i + 1][1], 1.0f, 1,1, 1.0f);
//        }
//        Set<float[][]> result = Util.mesh.marching_squares_outline(grid, 0.05f, Object[][]::new, Object[]::new);
//        System.out.println(result.size());
//        for(float[][] result2 : result){
//            float[][] leftHalf = Arrays.copyOfRange(result2,0, result2.length/2+1);
//            float[][] rightHalf = Arrays.copyOfRange(result2, result2.length/2, result2.length + 1);
//            rightHalf[rightHalf.length-1] = result2[0];
//            float[][] result3 = Util.mesh.line_simplification(leftHalf, height);
//            float[][] result4 = Util.mesh.line_simplification(rightHalf, height);
//            for (int i = 0; i < result3.length-1; i++) {
//                lineDrawer.draw(result3[i][0], result3[i][1], result3[(i + 1) % result3.length][0], result3[(i + 1) % result3.length][1], 1.0f, 1.0f, 1.0f, 1.0f);
//            }
//            for (int i = 0; i < result4.length-1; i++) {
//                lineDrawer.draw(result4[i][0], result4[i][1], result4[(i + 1) % result4.length][0], result4[(i + 1) % result4.length][1], 1.0f, 1.0f, 1.0f, 1.0f);
//            }
//        }
        for(Object[][] single: Util.mesh.split(grid, Object[][]::new, Object[]::new)){
            float[][] outline_single = Util.mesh.marching_squares_outline_single(single, 0.05f);
            float[][] leftHalf = Arrays.copyOfRange(outline_single, 0, outline_single.length/2+1);
            float[][] rightHalf = Arrays.copyOfRange(outline_single, outline_single.length/2, outline_single.length+1);
            rightHalf[rightHalf.length-1] = outline_single[0];
            leftHalf = Util.mesh.line_simplification(leftHalf, height);
            rightHalf = Util.mesh.line_simplification(rightHalf, height);
            for (int i = 0; i < leftHalf.length-1; i++){
                lineDrawer.draw(leftHalf[i][0], leftHalf[i][1], leftHalf[(i + 1) % leftHalf.length][0], leftHalf[(i + 1) % leftHalf.length][1], 1.0f, 1.0f, 1.0f, 1.0f);
            }
            for (int i = 0; i < rightHalf.length-1; i++) {
                lineDrawer.draw(rightHalf[i][0], rightHalf[i][1], rightHalf[(i + 1) % rightHalf.length][0], rightHalf[(i + 1) % rightHalf.length][1], 1.0f, 1.0f, 1.0f, 1.0f);
            }
            for(float[][] holes: Util.mesh.holes_outline_single(single, 0.05f)){
                float[][] leftHalfHole = Arrays.copyOfRange(holes, 0, holes.length/2+1);
                float[][] rightHalfHole = Arrays.copyOfRange(holes, holes.length/2, holes.length+1);
                rightHalfHole[rightHalfHole.length-1] = holes[0];
                leftHalfHole = Util.mesh.line_simplification(leftHalfHole, height);
                rightHalfHole = Util.mesh.line_simplification(rightHalfHole, height);
                for (int i = 0; i < leftHalfHole.length-1; i++){
                    lineDrawer.draw(leftHalfHole[i][0], leftHalfHole[i][1], leftHalfHole[(i + 1) % leftHalfHole.length][0], leftHalfHole[(i + 1) % leftHalfHole.length][1], 0, 0, 1.0f, 1.0f);
                }
                for (int i = 0; i < rightHalfHole.length-1; i++) {
                    lineDrawer.draw(rightHalfHole[i][0], rightHalfHole[i][1], rightHalfHole[(i + 1) % rightHalfHole.length][0], rightHalfHole[(i + 1) % rightHalfHole.length][1], 0, 0, 1.0f, 1.0f);
                }

            }
        }
//        Set<Object[][]> result = Util.mesh.holesInGrid(grid);
//        for(Object[][] result2 : result){
//            float[][] outline = Util.mesh.marching_squares_outline_single(result2, 0.05f);
//            float[][] simplified = Util.mesh.line_simplification(outline, height);
//            for(int i = 0; i < simplified.length; i++){
//                lineDrawer.draw(simplified[i][0], simplified[i][1], simplified[(i+1)%simplified.length][0], simplified[(i+1)%simplified.length][1], 0,0,1,1);
//            }
//        }
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
