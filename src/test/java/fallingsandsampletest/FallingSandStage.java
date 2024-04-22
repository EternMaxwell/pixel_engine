package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.stage.Stage;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import render.Render;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class FallingSandStage extends Stage<Render, InputTool> {

    public Grid grid;
    Element[] elements;
    int index = 0;
    Camera camera;
    FileWriter fileWriter;

    @Override
    public void init() {
        grid = new FallingGridMinorRectChunk();
        elements = new Element[]{new Sand(grid), new Stone(grid), new Water(grid), new Oil(grid), new Smoke(grid, 2500), new Steam(grid, 2500)};
        camera = new Camera();
        camera.projectionOrtho(-1, 1, -1, 1, -1, 1);
        camera.setScale(256f);
        camera.move(256,256);
        try {
            fileWriter = new FileWriter(grid.getClass().getName() + ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void input(InputTool inputTool) {
        camera.projectionOrtho(-inputTool.window().ratio(), inputTool.window().ratio(), -1, 1, -1, 1);
        float x = (float) inputTool.mouseX();
        float y = (float) inputTool.mouseY();
        Vector4f pos = new Vector4f(x, y, 0, 1);
        pos.mul(camera.projectionMatrix(new Matrix4f()).invert());
        pos.mul(camera.viewMatrix(new Matrix4f()).invert());
        int putX = (int) pos.x;
        int putY = (int) pos.y;
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
            for (int i = -5; i < 5; i++) {
                for (int j = -5; j < 5; j++) {
                    grid.set(putX + i, putY + j, elements[index].newInstance(grid));
                }
            }
        }
        if(inputTool.isMousePressed(GLFW_MOUSE_BUTTON_RIGHT)){
            for (int i = -5; i < 5; i++) {
                for (int j = -5; j < 5; j++) {
                    Element element = grid.get(putX + i, putY + j);
                    if(element != null){
                        element.heat(grid, putX + i, putY + j, 100);
                    }
                }
            }
        }
        if (inputTool.isKeyJustPressed(GLFW_KEY_EQUAL)) {
            index++;
            if (index >= elements.length) {
                index = 0;
            }
        }
        if (inputTool.isKeyJustPressed(GLFW_KEY_MINUS)) {
            index--;
            if (index < 0) {
                index = elements.length - 1;
            }
        }
        if (inputTool.scrollY() > 0) {
            grid.setGravity_y(grid.gravity_y() * 1.1f);
            grid.setGravity_x(grid.gravity_x() * 1.1f);
        }
        if (inputTool.scrollY() < 0) {
            grid.setGravity_y(grid.gravity_y() * 0.9f);
            grid.setGravity_x(grid.gravity_x() * 0.9f);
        }
        if (inputTool.isKeyPressed(GLFW_KEY_LEFT)) {
            float newGravityX = (float) (Math.cos(Math.toRadians(1)) * grid.gravity_x() - Math.sin(Math.toRadians(1)) * grid.gravity_y());
            float newGravityY = (float) (Math.sin(Math.toRadians(1)) * grid.gravity_x() + Math.cos(Math.toRadians(1)) * grid.gravity_y());
            grid.setGravity_x(newGravityX);
            grid.setGravity_y(newGravityY);
        }
        if (inputTool.isKeyPressed(GLFW_KEY_RIGHT)) {
            float newGravityX = (float) (Math.cos(Math.toRadians(-1)) * grid.gravity_x() - Math.sin(Math.toRadians(-1)) * grid.gravity_y());
            float newGravityY = (float) (Math.sin(Math.toRadians(-1)) * grid.gravity_x() + Math.cos(Math.toRadians(-1)) * grid.gravity_y());
            grid.setGravity_x(newGravityX);
            grid.setGravity_y(newGravityY);
        }
    }

    double lastTime = 0;
    double rate = 0.2;

    @Override
    public void update() {
        long start = System.nanoTime();
        grid.step();
        long end = System.nanoTime();
        double time = rate * ((end - start) / 1e6) + (1 - rate) * lastTime;
        lastTime = time;
        try {
            fileWriter.write(grid.getClass().getDeclaredField("tick").getInt(grid) + " " + String.format("%2.4f",time) + "\n");
        } catch (IOException|NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Render renderer) {
        camera.projectionOrtho(-renderer.window().ratio(), renderer.window().ratio(), -1, 1, -1, 1);
        renderer.pixelDrawer.setProjection(camera.projectionMatrix(new Matrix4f()));
        renderer.pixelDrawer.setModel(camera.viewMatrix(new Matrix4f()));
        renderer.pixelDrawer.setView(new Matrix4f().identity());
        renderer.lineDrawer.setProjection(camera.projectionMatrix(new Matrix4f()));
        renderer.lineDrawer.setModel(camera.viewMatrix(new Matrix4f()));
        renderer.lineDrawer.setView(new Matrix4f().identity());
        grid.render(renderer);
    }

    @Override
    public void dispose() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
