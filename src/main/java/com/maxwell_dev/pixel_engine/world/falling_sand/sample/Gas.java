package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Gas<ElementID> extends Element<ElementID>{
    int existenceTime;
    int lastStepTick = -1;
    float sinkingProcess = 0.0f;
    boolean sinkingTried = false;

    public Gas(int existenceTime) {
        this.existenceTime = existenceTime;
    }

    @Override
    public ElementType type() {
        return ElementType.GAS;
    }

    public boolean step(Grid<?,?,ElementID> grid, int x, int y, int tick) {
        if (lastStepTick == tick)
            return false;

//        grid.set(x, y, this);

        boolean moved = false;
        sinkingTried = false;
        //check the above block
        Element<ElementID> above = grid.get(x, y + 1);
        if (above == null && (grid.valid(x, y + 1) || !grid.invalidAsWall())) {
            sinkingProcess += 1 - density() / grid.airDensity();
            sinkingTried = true;
            lastStepTick = tick;
            if (sinkingProcess >= 1) {
                sinkingProcess = 0;
                moved = true;
                grid.set(x, y + 1, this);
                grid.set(x, y, above);
            } else {
                grid.set(x, y, this);
            }
        } else if (above != null && above.type() == ElementType.GAS) {
            if (above.density() > density()) {
                sinkingProcess += (above.density() - density()) / above.density();
                sinkingTried = true;
                lastStepTick = tick;
                if (sinkingProcess >= 1) {
                    sinkingProcess = 0;
                    moved = true;
                    grid.set(x, y + 1, this);
                    grid.set(x, y, above);
                } else {
                    grid.set(x, y, this);
                }
            }
        }
        if (moved)
            return true;
        int dir = Math.random() > 0.5 ? 1 : -1;
        //check the diagonal blocks
        Element<ElementID> diagonal = grid.get(x + dir, y + 1);
        if (diagonal == null && (grid.valid(x + dir, y + 1) || !grid.invalidAsWall())) {
            sinkingProcess += 1 - density() / grid.airDensity();
            sinkingTried = true;
            lastStepTick = tick;
            if (sinkingProcess >= 1) {
                sinkingProcess = 0;
                moved = true;
                grid.set(x + dir, y + 1, this);
                grid.set(x, y, null);
            } else {
                grid.set(x, y, this);
            }
        } else {
            diagonal = grid.get(x - dir, y + 1);
            if (diagonal == null && (grid.valid(x - dir, y + 1) || !grid.invalidAsWall())) {
                sinkingProcess += 1 - density() / grid.airDensity();
                sinkingTried = true;
                lastStepTick = tick;
                if (sinkingProcess >= 1) {
                    sinkingProcess = 0;
                    moved = true;
                    grid.set(x - dir, y + 1, this);
                    grid.set(x, y, null);
                } else {
                    grid.set(x, y, this);
                }
            }
        }
        //if the diagonal blocks are gas then try to move to them
        if (!moved) {
            diagonal = grid.get(x + dir, y + 1);
            if (diagonal != null && diagonal.type() == ElementType.GAS) {
                if (diagonal.density() > density()) {
                    sinkingProcess += (diagonal.density() - density()) / diagonal.density();
                    sinkingTried = true;
                    if (sinkingProcess >= 1) {
                        sinkingProcess = 0;
                        moved = true;
                        lastStepTick = tick;
                        grid.set(x + dir, y + 1, this);
                        grid.set(x, y, diagonal);
                    } else {
                        grid.set(x, y, this);
                    }
                }
            } else {
                diagonal = grid.get(x - dir, y + 1);
                if (diagonal != null && diagonal.type() == ElementType.GAS) {
                    if (diagonal.density() > density()) {
                        sinkingProcess += (diagonal.density() - density()) / diagonal.density();
                        sinkingTried = true;
                        lastStepTick = tick;
                        if (sinkingProcess >= 1) {
                            sinkingProcess = 0;
                            moved = true;
                            grid.set(x - dir, y + 1, this);
                            grid.set(x, y, diagonal);
                        } else {
                            grid.set(x, y, this);
                        }
                    }
                }
            }
        }
        //check the side blocks
        if (!moved && !sinkingTried) {
            Element<ElementID> side = grid.get(x + dir, y);
            if (side == null && (grid.valid(x + dir, y) || !grid.invalidAsWall())) {
                sinkingProcess += 1 - density() / 2 * grid.airDensity();
                sinkingTried = true;
                lastStepTick = tick;
                if (sinkingProcess >= 1) {
                    sinkingProcess = 0;
                    moved = true;
                    grid.set(x + dir, y, this);
                    grid.set(x, y, null);
                }
            } else {
                side = grid.get(x - dir, y);
                if (side == null && (grid.valid(x - dir, y) || !grid.invalidAsWall())) {
                    sinkingProcess += 1 - density() / 2 * grid.airDensity();
                    sinkingTried = true;
                    lastStepTick = tick;
                    if (sinkingProcess >= 1) {
                        sinkingProcess = 0;
                        moved = true;
                        grid.set(x - dir, y, this);
                        grid.set(x, y, null);
                    }
                }
            }
        }
        if (!moved && !sinkingTried) {
            Element<ElementID> side = grid.get(x + dir, y);
            if (side != null && side.type() == ElementType.GAS) {
                if (side.density() > density()) {
                    sinkingProcess += 1 - density() / 2 * side.density();
                    sinkingTried = true;
                    lastStepTick = tick;
                    if (sinkingProcess >= 1) {
                        sinkingProcess = 0;
                        moved = true;
                        grid.set(x + dir, y, this);
                        grid.set(x, y, side);
                    }
                }
            } else {
                side = grid.get(x - dir, y);
                if (side != null && side.type() == ElementType.GAS) {
                    if (side.density() > density()) {
                        sinkingProcess += 1 - density() / 2 * side.density();
                        sinkingTried = true;
                        lastStepTick = tick;
                        if (sinkingProcess >= 1) {
                            sinkingProcess = 0;
                            moved = true;
                            grid.set(x - dir, y, this);
                            grid.set(x, y, side);
                        }
                    }
                }
            }
        }
        return moved;
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
