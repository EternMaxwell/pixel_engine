package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.ElementType;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Liquid;

public class Water extends Liquid<ElementID> {
    public Water(Grid<?, ?, ElementID> grid) {
        super(grid);
        color = new float[]{0, 0, 1, 0.8f};
    }

    /**
     * @return the dispersion rate.
     */
    @Override
    public int dispersionRate() {
        return 3;
    }

    @Override
    public String name() {
        return "element:test:water";
    }

    @Override
    public Element<Grid<?, ?, ElementID>, ElementType, ElementID> newInstance(Grid<?, ?, ElementID> grid) {
        return new Water(grid);
    }

    @Override
    public ElementID id() {
        return ElementID.WATER;
    }

    @Override
    public boolean randomTick(Grid<?, ?, ElementID> grid, int x, int y, int tick, int intensity) {
        return false;
    }

    @Override
    public float density() {
        return 1;
    }

    @Override
    public float friction() {
        return 0.1f;
    }

    @Override
    public float restitution() {
        return 0.01f;
    }

    @Override
    public boolean heat(Grid<?, ?, ElementID> grid, int x, int y, float heat) {
        if(heat >= 100f){
            grid.set(x, y, new Steam(grid, 5192));
            return true;
        }
        return false;
    }

    @Override
    public boolean damage(Grid<?, ?, ElementID> grid, int x, int y, float damage) {
        return false;
    }
}
