package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Powder;

public class Sand extends Powder<ElementID> {

    public Sand() {
        color = new float[]{0.8f, 0.8f, 0.0f, 1f};
    }

    @Override
    public String name() {
        return "element:test:sand";
    }

    @Override
    public Element<ElementID> newInstance() {
        return new Sand();
    }

    @Override
    public ElementID id() {
        return ElementID.SAND;
    }

    @Override
    public boolean randomTick(Grid<?, ?, ElementID> grid, int x, int y, int tick, int intensity) {
        return false;
    }

    @Override
    public float density() {
        return 3;
    }

    @Override
    public float friction() {
        return 0.2f;
    }

    @Override
    public float restitution() {
        return 0.2f;
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
