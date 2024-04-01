package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.stage.Stage;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class FallingSandStage extends Stage<Render, InputTool> {

    FallingGrid grid;

    @Override
    public void init() {
        grid = new FallingGrid();
        for(int i = 500; i < 600; i++){
            for(int j = 500; j < 600; j++){
                grid.set(i, j, new Sand());
            }
        }
    }

    @Override
    public void input(InputTool inputTool) {
        float x = (float) inputTool.mouseX();
        float y = (float) inputTool.mouseY();
        int putX = (int) x * 512 + 512;
        int putY = (int) y * 512 + 512;
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
            for(int i = -5; i < 5; i++){
                for(int j = -5; j < 5; j++){
                    grid.set(putX + i, putY + j, new Sand());
                }
            }
        }
    }

    @Override
    public void update() {
        grid.step();
    }

    @Override
    public void render(Render renderer) {
        renderer.pixelDrawer.setProjection(new Matrix4f().ortho(0, 1024f, 0, 1024f, -1, 1));
        grid.render(renderer);
    }

    @Override
    public void dispose() {

    }
}
