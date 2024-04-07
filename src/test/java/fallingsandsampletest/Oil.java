package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.ElementType;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Liquid;

public class Oil extends Liquid<ElementID> {

    public Oil(Grid<?, ?, ElementID> grid) {
        super(grid);
        color = new float[]{0.2f, 0.2f, 0.2f, 1f};
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
        return "element:test:oil";
    }

    @Override
    public Element<Grid<?, ?, ElementID>, ElementType, ElementID> newInstance(Grid<?, ?, ElementID> grid) {
        return new Oil(grid);
    }

    @Override
    public ElementID id() {
        return ElementID.OIL;
    }

    @Override
    public boolean randomTick(Grid<?, ?, ElementID> grid, int x, int y, int tick, int intensity) {
        return false;
    }

    @Override
    public float density() {
        return 0.8f;
    }

    @Override
    public float friction() {
        return 0.02f;
    }

    @Override
    public float restitution() {
        return 0.01f;
    }

    @Override
    public boolean heat(Grid<?, ?, ElementID> grid, int x, int y, float heat) {
        return false;
    }

    @Override
    public boolean damage(Grid<?, ?, ElementID> grid, int x, int y, float damage) {
        return false;
    }
}
