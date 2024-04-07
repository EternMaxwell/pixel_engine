package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.stage.Stage;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Liquid;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class FallingSandStage extends Stage<Render, InputTool> {

    Grid grid;
    Element[] elements;
    int index = 0;

    @Override
    public void init() {
        grid = new FallingGrid();
        elements = new Element[]{new Sand(grid), new Stone(grid), new Water(grid), new Oil(grid)};
    }

    @Override
    public void input(InputTool inputTool) {
        float x = (float) inputTool.mouseX();
        float y = (float) inputTool.mouseY();
        int putX = (int) (x * 256 + 256);
        int putY = (int) (y * 256 + 256);
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
            for(int i = -5; i < 5; i++){
                for(int j = -5; j < 5; j++){
                    grid.set(putX + i, putY + j, elements[index].newInstance(grid));
                }
            }
        }
        if(inputTool.isKeyJustPressed(GLFW_KEY_EQUAL)){
            index++;
            if(index >= elements.length){
                index = 0;
            }
        }
        if(inputTool.isKeyJustPressed(GLFW_KEY_MINUS)){
            index--;
            if(index < 0){
                index = elements.length - 1;
            }
        }
        if(inputTool.scrollY() > 0){
            grid.setGravity_y(grid.gravity_y() * 1.1f);
        }
        if(inputTool.scrollY() < 0){
            grid.setGravity_y(grid.gravity_y() * 0.9f);
        }
    }

    @Override
    public void update() {
        grid.step();
    }

    @Override
    public void render(Render renderer) {
        renderer.pixelDrawer.setProjection(new Matrix4f().ortho(0, 512, 0, 512, -1, 1));
        renderer.pixelDrawer.setModel(new Matrix4f().identity());
        renderer.pixelDrawer.setView(new Matrix4f().identity());
        grid.render(renderer);
    }

    @Override
    public void dispose() {

    }
}
