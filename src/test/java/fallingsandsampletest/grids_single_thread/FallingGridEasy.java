package fallingsandsampletest.grids_single_thread;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import fallingsandsampletest.Actions;
import fallingsandsampletest.ElementID;
import render.Render;

public class FallingGridEasy extends com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid<Render, Actions, ElementID>{

    Element<ElementID>[][] grid;
    int tick = 0;
    boolean inverse = false;

    public int tick(){
        return tick;
    }

    public FallingGridEasy(int xc, int yc) {
        pixelSize = 1;
        setGravity_x(0);
        setGravity_y(-100f);
        grid = new Element[xc * 64][yc * 64];
    }
    @Override
    public Element<ElementID> get(int x, int y) {
        if(x < 0 || y < 0 || x >= grid.length || y >= grid[0].length){
            return null;
        }
        return grid[x][y];
    }

    @Override
    public boolean valid(int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
    }

    @Override
    public boolean invalidAsWall() {
        return false;
    }

    @Override
    public void set(int x, int y, Element<ElementID> element) {
        if(x < 0 || y < 0 || x >= grid.length || y >= grid[0].length){
            return;
        }
        grid[x][y] = element;
        Element<ElementID> side = get(x + 1, y);
        if(side != null){
            side.touch(this, x + 1, y);
        }
        side = get(x - 1, y);
        if(side != null){
            side.touch(this, x - 1, y);
        }
        side = get(x, y + 1);
        if(side != null){
            side.touch(this, x, y + 1);
        }
        side = get(x, y - 1);
        if(side != null){
            side.touch(this, x, y - 1);
        }
        side = get(x + 1, y + 1);
        if(side != null){
            side.touch(this, x + 1, y + 1);
        }
        side = get(x - 1, y - 1);
        if(side != null){
            side.touch(this, x - 1, y - 1);
        }
        side = get(x - 1, y + 1);
        if(side != null){
            side.touch(this, x - 1, y + 1);
        }
        side = get(x + 1, y - 1);
        if(side != null){
            side.touch(this, x + 1, y - 1);
        }
    }

    @Override
    public void removeElementAt(int x, int y) {
        try {
            grid[x][y] = null;
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    @Override
    public Element<ElementID> popElementAt(int x, int y) {
        Element<ElementID> element = grid[x][y];
        grid[x][y] = null;
        return element;
    }

    @Override
    public double step() {
        long start = System.nanoTime();
        for (int y = 0; y < grid.length; y++) {
            if(inverse){
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[x][y] != null) {
                        grid[x][y].step(this, x, y, tick);
                    }
                }
            }else {
                for (int x = grid[y].length - 1; x >= 0; x--) {
                    if (grid[x][y] != null) {
                        grid[x][y].step(this, x, y, tick);
                    }
                }
            }
        }
        inverse = !inverse;
        tick++;
        return (System.nanoTime() - start) / 1e6;
    }

    @Override
    public void render(Render renderer) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] != null) {
                    float[] color = grid[x][y].color().clone();
                    if(!grid[x][y].freeFall()){
                        color[0] *= 0.5F;
                        color[1] *= 0.5F;
                        color[2] *= 0.5F;
                    }
                    renderer.pixelDrawer.draw(x * pixelSize + pixelSize / 2, y * pixelSize + pixelSize / 2, pixelSize, color[0], color[1], color[2], color[3]);
                }
            }
        }
        renderer.pixelDrawer.flush();
    }

    @Override
    public int[] basePos() {
        return new int[]{0,0};
    }

    @Override
    public void action(double x, double y, Actions action, double[] arguments) {

    }

    @Override
    public float airDensity() {
        return 0.028f;
    }

    @Override
    public float default_vx() {
        return 0;
    }

    @Override
    public float default_vy() {
        return 0;
    }

    @Override
    public float tickTime() {
        return 1/60f;
    }

    @Override
    public float airResistance() {
        return 0.98f;
    }
}
