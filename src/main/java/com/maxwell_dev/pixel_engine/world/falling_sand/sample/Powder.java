package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

import org.jetbrains.annotations.NotNull;

public abstract class Powder<ElementID> extends Element<ElementID>{
    private int lastTick = -1;
    private float velocityX = 0.0f;
    private float velocityY = 0.0f;
    private float thresholdX = 0.0f;
    private float thresholdY = 0.0f;
    private boolean falling = true;

    @Override
    public ElementType type() {
        return ElementType.POWDER;
    }

    @Override
    public boolean step(Grid<?, ?, ElementID> grid, int x, int y, int tick) {
        if(lastTick == tick)
            return false;
        lastTick = tick;

        if(falling) {
            velocityY += grid.gravity_y() * grid.tickTime();
            velocityX += grid.gravity_x() * grid.tickTime();
            thresholdX += velocityX * grid.tickTime()/grid.pixelSize();
            thresholdY += velocityY * grid.tickTime()/grid.pixelSize();
            int xMod = thresholdX > 0 ? 1 : -1;
            int yMod = thresholdY > 0 ? 1 : -1;
            int xMove = (int) Math.abs(thresholdX);
            int yMove = (int) Math.abs(thresholdY);
            thresholdX = xMove == 0 ? thresholdX : 0;
            thresholdY = yMove == 0 ? thresholdY : 0;
            if (xMove != 0 || yMove != 0) {
                int[] blocked = new int[2];
                int[] lastAvailable = new int[2];
                if(tryGetTo(grid, x, y, xMove, yMove, xMod, yMod, blocked, lastAvailable)){
                    grid.set(x + xMod * xMove, y + yMod * yMove, this);
                    grid.set(x, y, null);
                }else{
                    grid.set(lastAvailable[0], lastAvailable[1], this);
                    grid.set(x, y, null);
                    if(blockDirSameToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])){
                        falling = false;
                    }
                }
            }
        }
        return false;
    }

    public boolean tryGetTo(Grid<?, ?, ElementID> grid, int x, int y, int xMove, int yMove, int xMod, int yMod, int[] blocked, int[] lastAvailable){
        int lastX = x;
        int lastY = y;
        lastAvailable[0] = x;
        lastAvailable[1] = y;
        int targetX = x + xMod * xMove;
        int targetY = y + yMod * yMove;
        boolean xLarger = xMove > yMove;
        float ratio = xLarger ? (float) yMove / xMove : (float) xMove / yMove;
        while (lastX != targetX || lastY != targetY) {
            if(xLarger){
                int shouldBeY = Math.round(y + (lastX - x) * ratio * yMod);
                if(shouldBeY != lastY){
                    int thisY = lastY + yMod;
                    if(grid.get(lastX, thisY) == null || grid.get(lastX, thisY).type() == ElementType.GAS){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = thisY;
                        lastY = thisY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = thisY;
                        return false;
                    }
                }else{
                    int thisX = lastX + xMod;
                    if(grid.get(thisX, lastY) == null || grid.get(thisX, lastY).type() == ElementType.GAS){
                        lastAvailable[0] = thisX;
                        lastAvailable[1] = lastY;
                        lastX = thisX;
                    }else {
                        blocked[0] = thisX;
                        blocked[1] = lastY;
                        return false;
                    }
                }
            }else{
                int shouldBeX = Math.round(x + (lastY - y) * ratio * xMod);
                if(shouldBeX != lastX){
                    int thisX = lastX + xMod;
                    if(grid.get(thisX, lastY) == null || grid.get(thisX, lastY).type() == ElementType.GAS){
                        lastAvailable[0] = thisX;
                        lastAvailable[1] = lastY;
                        lastX = thisX;
                    }else {
                        blocked[0] = thisX;
                        blocked[1] = lastY;
                        return false;
                    }
                }else{
                    int thisY = lastY + yMod;
                    if(grid.get(lastX, thisY) == null || grid.get(lastX, thisY).type() == ElementType.GAS){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = thisY;
                        lastY = thisY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = thisY;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean blockDirSameToGravity(Grid<?, ?, ElementID> grid, float blockX, float blockY){
        float gravity_x = grid.gravity_x();
        float gravity_y = grid.gravity_y();
        double angle = Math.atan2(gravity_y, gravity_x);
        double angle2 = Math.atan2(blockY, blockX);
        return Math.abs(angle - angle2) < Math.PI / 4;
    }

    @Override
    public boolean freeFall() {
        return falling;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {

    }
}
