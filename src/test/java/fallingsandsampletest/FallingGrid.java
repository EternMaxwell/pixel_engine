package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;

public class FallingGrid extends com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid<Render, Actions, ElementID>{

    Element<ElementID>[][] grid;
    int tick = 0;
    boolean inverse = false;

    public FallingGrid() {
        gravity_x = 0;
        gravity_y = -10f;
        pixelSize = 1;
        grid = new Element[1024][1024];
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
        return true;
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
                    float[] color = grid[x][y].color();
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
        return -0.7f;
    }

    @Override
    public float tickTime() {
        return 1/60f;
    }
}
