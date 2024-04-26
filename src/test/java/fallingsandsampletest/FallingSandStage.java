package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.stage.Stage;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import fallingsandsampletest.grids_chunk_multithread.FallingGridChunkMulti;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import render.Render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.lwjgl.glfw.GLFW.*;

public class FallingSandStage extends Stage<Render, InputTool> {

    public Grid grid;
    Element[] elements;
    int index = 0;
    Camera camera;
    FileWriter fileWriter;

    @Override
    public void init() {
        grid = new FallingGridChunkMulti();
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
    double rate = 0.1;

    @Override
    public void update() {
        long start = System.nanoTime();
        grid.step();
        long end = System.nanoTime();
        double time = rate * ((end - start) / 1e6) + (1 - rate) * lastTime;
        lastTime = time;
        try {
            fileWriter.write(grid.tick() + " " + String.format("%2.4f",time) + "\n");
        } catch (IOException e) {
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
        //draw the collected data to a graph
        int height = 2048;
        BufferedImage image = new BufferedImage(10240, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 10240, height);
        graphics.setColor(Color.BLACK);
        try {
            String[] data = getStrings();
            double max = 10;
            for (String s : data) {
                String[] split = s.split(" ");
                double time = Double.parseDouble(split[1]);
            }
            for (int i = 0; i < data.length - 1; i++) {
                String[] split = data[i].split(" ");
                double time = Double.parseDouble(split[1]);
                double nextTime = Double.parseDouble(data[i + 1].split(" ")[1]);
                graphics.drawLine(i, height - (int) (time / max * height), i + 1, height - (int) (nextTime / max * height));
            }
            graphics.dispose();
            ImageIO.write(image, "png", new File(grid.getClass().getName() + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        grid.dispose();
    }

    private String @NotNull [] getStrings() throws FileNotFoundException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(grid.getClass().getName() + ".txt"));
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                if ((c = bufferedInputStream.read()) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stringBuilder.append((char) c);
        }
        String[] data = stringBuilder.toString().split("\n");
        return data;
    }
}
