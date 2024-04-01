package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Gas<ElementID> extends Element<ElementID>{
    int existenceTime;
    int lastStepTick = -1;
    float sinkingProcess = 0.0f;
    boolean sinkingTried = false;

    public Gas(Grid grid, int existenceTime) {
        super(grid);
        this.existenceTime = existenceTime;
    }

    @Override
    public ElementType type() {
        return ElementType.GAS;
    }

    public boolean step(Grid<?,?,ElementID> grid, int x, int y, int tick) {
        return false;
    }

    @Override
    public boolean randomTick(Grid<?,?,ElementID> grid, int x, int y, int tick, int intensity) {
        existenceTime -= 32;
        if (existenceTime <= 0) {
            grid.set(x, y, existTimeEndReplaceElement());
            return true;
        }
        return false;
    }

    public abstract Element<ElementID> existTimeEndReplaceElement();
}
