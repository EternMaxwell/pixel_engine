package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.ElementType;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Gas;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;

public class Steam extends Gas<ElementID> {
    public Steam(Grid grid, int existenceTime) {
        super(grid, existenceTime);
        color = new float[]{0.5f, 0.5f, 0.5f, 0.5f};
    }

    @Override
    public Element<ElementID> existTimeEndReplaceElement() {
        return null;
    }

    @Override
    public String name() {
        return "element:test:steam";
    }

    @Override
    public com.maxwell_dev.pixel_engine.world.falling_sand.Element<Grid<?, ?, ElementID>, ElementType, ElementID> newInstance(Grid<?, ?, ElementID> grid) {
        return new Steam(grid, 2500);
    }

    @Override
    public ElementID id() {
        return ElementID.STEAM;
    }

    @Override
    public float density() {
        return 0.020f;
    }

    @Override
    public float friction() {
        return 0.01f;
    }

    @Override
    public float restitution() {
        return 0;
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
