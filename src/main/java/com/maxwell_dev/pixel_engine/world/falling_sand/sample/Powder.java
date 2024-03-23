package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

import org.jetbrains.annotations.NotNull;

public abstract class Powder<ElementID> extends Element<ElementID>{
    private int lastTick = -1;
    private float velocity = 0.7f;
    private float sinkingProcess = 0.0f;
    private float floatingProcess = 0.0f;
    private boolean falling = true;
    private boolean sinkInLargeDensity = true;

    @Override
    public ElementType type() {
        return ElementType.POWDER;
    }

    @Override
    public boolean step(Grid<?, ?, ElementID> grid, int x, int y, int tick) {
        boolean moved = false;

        int distance = velocity > 1 ? (int) velocity : 1;
        int iy = y;
        //falling
        for (int i = 0; i < distance; i++) {
            Element<ElementID> below = grid.get(x, iy - 1);
            if ((below == null || below.type() == ElementType.GAS) && (grid.valid(x, iy - 1) || !grid.invalidAsWall())) {
                falling = true;
                sinkInLargeDensity = true;
                moved = true;
                grid.set(x, iy - 1, this);
                grid.set(x, iy, below);
                lastTick = tick;
                iy--;
            } else if (below != null) {
                falling = grid.get(x, iy - 1).freeFall();
                if (!falling)
                    velocity = 0.7f;
                break;
            } else {
                falling = false;
                lastTick = tick;
                velocity = 0.7f;
                return moved;
            }
        }
        //if moved then update velocity and return true
        if (moved) {
            lastTick = tick;
            velocity += 0.1f;
            velocity *= 0.985f;
            return true;
        }

        Element<ElementID> above;
        above = grid.get(x, iy + 1);
        Element<ElementID> left = grid.get(x - 1, iy);
        Element<ElementID> right = grid.get(x + 1, iy);
        if (above != null && above.type() == ElementType.LIQUID) {
            if(above.density() > density()){
                floatingProcess += (above.density() - density()) / above.density();
                grid.set(x, iy, this);
                if (floatingProcess >= 1) {
                    grid.set(x, iy, above);
                    grid.set(x, iy + 1, this);
                    lastTick = tick;
                    floatingProcess = 0.0f;
                    return true;
                }
            }
        }else {
            if((left == null || (left.type() != ElementType.LIQUID || left.density() < density()))
                    && (right == null || (right.type() != ElementType.LIQUID || right.density() < density())))
                sinkInLargeDensity = true;
        }
        //if didn't move then check the block below
        //if the block below is liquid then move to it
        Element<ElementID> below = grid.get(x, iy - 1);
        above = grid.get(x, iy + 1);
        if (below != null && below.type() == ElementType.LIQUID) {
            if (below.density() >= density()) {
                if (sinkInLargeDensity && (above == null || !(left == null || (left.type() == ElementType.LIQUID && left.density() >= density()))
                        && !(right == null || (right.type() == ElementType.LIQUID && right.density() >= density())))) {
                    grid.set(x, iy - 1, this);
                    grid.set(x, iy, below);
                    lastTick = tick;
                    return true;
                }else {
                    if(above != null && above.type() == ElementType.POWDER)
                        sinkInLargeDensity = ((Powder<ElementID>) above).sinkInLargeDensity();
                    else
                        sinkInLargeDensity = false;
                }
            } else {
                sinkingProcess += (density() - below.density()) / density();
                grid.set(x, iy, this);
                if (sinkingProcess >= 1) {
                    grid.set(x, iy, grid.get(x, iy - 1));
                    grid.set(x, iy - 1, this);
                    lastTick = tick;
                    sinkingProcess = 0.0f;
                    return true;
                }
            }
            return false;
        }
        //check the block at the bottom left and right
        //if the block is empty then move to it
        int dir = Math.random() >= 0.5 ? 1 : -1;
        Element<ElementID> side = grid.get(x + dir, iy - 1);
        if ((side == null || side.type() == ElementType.GAS) && (grid.valid(x + dir, iy - 1) || !grid.invalidAsWall())) {
            grid.set(x + dir, iy - 1, this);
            grid.set(x, iy, side);
            moved = true;
        } else {
            side = grid.get(x - dir, iy - 1);
            if ((side == null || side.type() == ElementType.GAS) && (grid.valid(x - dir, iy - 1) || !grid.invalidAsWall())) {
                grid.set(x - dir, iy - 1, this);
                grid.set(x, iy, side);
                moved = true;
            }
        }
        //if the block at the bottom left and right is liquid then try sinking
        if (!moved) {
            side = grid.get(x + dir, iy - 1);
            if (side != null && side.type() == ElementType.LIQUID) {
                if (side.density() < density()) {
                    {
                        sinkingProcess += (density() - side.density()) / density();
                        lastTick = tick;
                        grid.set(x, iy, this);
                        if (sinkingProcess >= 1) {
                            grid.set(x + dir, iy - 1, this);
                            grid.set(x, iy, side);
                            sinkingProcess = 0.0f;
                            moved = true;
                        }
                    }
                } else {
                    side = grid.get(x - dir, iy - 1);
                    if (side != null && side.type() == ElementType.LIQUID) {
                        if (side.density() < density()) {
                            sinkingProcess += (density() - side.density()) / density();
                            lastTick = tick;
                            grid.set(x, iy, this);
                            if (sinkingProcess >= 1) {
                                grid.set(x - dir, iy - 1, this);
                                grid.set(x, iy, side);
                                sinkingProcess = 0.0f;
                                moved = true;
                            }
                        }
                    }
                }
            }
        }
        //if moved then update lastTick
        if (moved) {
            lastTick = tick;
        }

        return moved;
    }

    @Override
    public boolean freeFall() {
        return falling;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {

    }

    public boolean sinkInLargeDensity(){
        return sinkInLargeDensity;
    }
}
