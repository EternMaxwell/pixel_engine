package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Solid;

public class Stone extends Solid<ElementID> {

    public Stone(Grid grid) {
        super(grid);
        color = new float[]{0.5f, 0.5f, 0.5f, 1f};
    }

    @Override
    public String name() {
        return "element:test:stone";
    }

    @Override
    public Element<ElementID> newInstance(Grid grid) {
        return new Stone(grid);
    }

    @Override
    public ElementID id() {
        return ElementID.STONE;
    }

    @Override
    public boolean randomTick(Grid<?, ?, ElementID> grid, int x, int y, int tick, int intensity) {
        return false;
    }

    @Override
    public float velocityX() {
        return 0;
    }

    @Override
    public float velocityY() {
        return 0;
    }

    @Override
    public float density() {
        return 5;
    }

    @Override
    public float friction() {
        return 0.3f;
    }

    @Override
    public float restitution() {
        return 0.1f;
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
