package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Liquid<ElementID> extends Element<ElementID>{
    boolean falling = true;
    int lastTick = -1;
    float sinkingProcess = 0.0f;
    float velocity = 0.7f;
    int dir = Math.random() > 0.5? 1: -1;
    int lastBlocked = 0;
    @Override
    public ElementType type() {
        return ElementType.LIQUID;
    }

    /**
     * @return the dispersion rate.
     */
    public abstract int dispersionRate();

    /**
     * get if the fluid is free-falling.
     * @return true if the fluid is free-falling.
     */
    public boolean freeFall() {
        return falling;
    }

    @Override
    public boolean step(Grid<?,?,ElementID> grid, int x, int y, int tick) {
        if(lastTick == tick)
            return false;

        boolean moved = false;

        int distance = velocity > 1 ? (int) velocity : 1;
        int iy = y;
        //falling
        for (int i = 0; i < distance; i++) {
            Element<ElementID> below = grid.get(x, iy - 1);
            if ((below == null || below.type() == ElementType.GAS) && (grid.valid(x, iy - 1) || !grid.invalidAsWall())) {
                falling = true;
                moved = true;
                grid.set(x, iy -1, this);
                grid.set(x, iy, below);
                lastTick = tick;
                iy--;
            }else if(below != null){
                falling = grid.get(x, iy - 1).freeFall();
                if(!falling)
                    velocity = 0.7f;
                break;
            }else {
                falling = false;
                lastTick = tick;
                velocity = 0.7f;
                return moved;
            }
        }
        //if moved then update velocity and return true
        if(moved){
            lastTick = tick;
            velocity += 0.1f;
            velocity *= 0.985f;
            return true;
        }

        //if didn't move then check the block below
        //if the block below is liquid then move to it
        Element<ElementID> below = grid.get(x, iy - 1);
        if(below!=null && (below.type() == ElementType.LIQUID || below.type() == ElementType.POWDER)){
            if(below.density() >= density()){
                sinkingProcess = 0.0f;
            }else {
                sinkingProcess += (density() - below.density())/density();
                lastTick = tick;
                grid.set(x, iy, this);
                if(sinkingProcess >= 1) {
                    grid.set(x, iy, grid.get(x, iy - 1));
                    grid.set(x, iy - 1, this);
                    sinkingProcess = 0.0f;
                    return true;
                }
            }
        }
        //check the block at the bottom left and right
        //if the block is empty then move to it
        Element<ElementID> diagnose = grid.get(x + dir, iy - 1);
        if ((diagnose == null || diagnose.type() == ElementType.GAS) && (grid.valid(x + dir, iy - 1) || !grid.invalidAsWall())) {
            grid.set(x + dir, iy - 1, this);
            grid.set(x, iy, diagnose);
            moved = true;
        } else {
            diagnose = grid.get(x - dir, iy - 1);
            if ((diagnose == null || diagnose.type() == ElementType.GAS) && (grid.valid(x - dir, iy - 1) || !grid.invalidAsWall())) {
                grid.set(x - dir, iy - 1, this);
                grid.set(x, iy, diagnose);
                moved = true;
            }
        }
        //if the block at the bottom left and right is liquid then try sinking
        if (!moved) {
            diagnose = grid.get(x + dir, iy-1);
            if(diagnose != null && (diagnose.type() == ElementType.LIQUID || diagnose.type() == ElementType.POWDER)){
                if(diagnose.density() >= density()){
                    sinkingProcess = 0.0f;
                }else {
                    sinkingProcess += (density() - diagnose.density()) / density();
                    lastTick = tick;
                    grid.set(x, iy, this);
                    if (sinkingProcess >= 1) {
                        grid.set(x + dir, iy - 1, this);
                        grid.set(x, iy, diagnose);
                        sinkingProcess = 0.0f;
                        moved = true;
                    }
                }
            }else {
                diagnose = grid.get(x - dir, iy-1);
                if(diagnose != null && (diagnose.type() == ElementType.LIQUID || diagnose.type() == ElementType.POWDER)){
                    if(diagnose.density() >= density()){
                        sinkingProcess = 0.0f;
                    }else {
                        sinkingProcess += (density() - diagnose.density()) / density();
                        lastTick = tick;
                        grid.set(x, iy, this);
                        if (sinkingProcess >= 1) {
                            grid.set(x - dir, iy - 1, this);
                            grid.set(x, iy, diagnose);
                            sinkingProcess = 0.0f;
                            moved = true;
                        }
                    }
                }
            }
        }
        //check the left and right blocks
        if (!moved) {
            int dirL = 0;
            boolean dirBlock = false;
            Element<ElementID> side;
            for(int i = 1; i <= dispersionRate(); i++) {
                side = grid.get(x + dir * i, iy);
                if ((side == null || side.type() == ElementType.GAS) && (grid.valid(x + dir * i, iy) || !grid.invalidAsWall())) {
                    dirL = i;
                } else {
                    if(side != null && side.type() == ElementType.LIQUID && side.density() < density()){
//                        Element sideAbove = grid.get(x + dir * i, iy + 1);
                        if(Math.random() > .4) {
                            dirL = i;
                            dirBlock = true;
                        }
                    }else
                        dirBlock = true;
                    break;
                }
            }
            if(dirL > 0) {
                side = grid.get(x + dir * dirL, iy);
                grid.set(x + dir * dirL, iy, this);
                grid.set(x, iy, side);
                moved = true;
            }else{
                if(lastBlocked > 0 && dirBlock){
                    lastBlocked = 0;
                    dir = -dir;
                    for(int i = 1; i <= dispersionRate(); i++){
                        side = grid.get(x + dir * i, iy);
                        if ((side == null || side.type() == ElementType.GAS) && (grid.valid(x + dir * i, iy) || !grid.invalidAsWall())) {
                            dirL = i;
                        } else {
                            break;
                        }
                    }
                    if(dirL > 0) {
                        side = grid.get(x + dir * dirL, iy);
                        grid.set(x + dir * dirL, iy, this);
                        grid.set(x, iy, side);
                        moved = true;
                    }
                }else {
                    grid.set(x, iy, this);
                }
            }
            lastBlocked = dirBlock? lastBlocked + 1: 0;
        }
        //if moved then update lastTick
        if(moved){
            lastTick = tick;
        }

        return moved;
    }
}
