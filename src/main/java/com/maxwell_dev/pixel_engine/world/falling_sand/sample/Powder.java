package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

import org.jetbrains.annotations.NotNull;

public abstract class Powder<ElementID> extends Element<ElementID>{
    private int lastTick = -1;
    private float velocityX = 0.0f;
    private float velocityY = 0.0f;
    private float thresholdX = 0.0f;
    private float thresholdY = 0.0f;
    private boolean falling = true;

    public Powder(Grid<?, ?, ElementID> grid) {
        super(grid);
        velocityY = grid.default_vy();
        velocityX = grid.default_vx();
    }

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
                    grid.set(x, y, null);
                    grid.set(x + xMod * xMove, y + yMod * yMove, this);
                }else{
                    grid.set(x, y, null);
                    grid.set(lastAvailable[0], lastAvailable[1], this);
                    Element block = grid.get(blocked[0], blocked[1]);
                    if(blockDirSameToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])){
                        if(!block.freeFall()){
                            falling = false;
                            float dot = grid.gravity_x() * velocityY - grid.gravity_y() * velocityX;
                            //TODO: random spread speed when blocked and no longer free falling.
                            if(dot == 0){

                            }else if(dot > 0){

                            }else{

                            }
                        }
                    }else{
                        if(block.type() == ElementType.SOLID){
                            float restitution = (float) Math.sqrt(block.restitution() * restitution());
                            float friction = (float) Math.sqrt(block.friction() * friction());
                            if(blocked[0] == lastAvailable[0]) {
                                velocityX = -velocityX * restitution;
                                velocityY = velocityY * friction;
                            }else{
                                velocityY = -velocityY * restitution;
                                velocityX = velocityX * friction;
                            }
                        }else if(block.type() == ElementType.POWDER){
                            float restitution = (float) Math.sqrt(block.restitution() * restitution());
                            float friction = (float) Math.sqrt(block.friction() * friction());
                            if(blocked[0] == lastAvailable[0]) {
                                float impulseY = ((restitution + 1) * density() * block.density() * (velocityY - ((Powder)block).velocityY())) / (density() + block.density());
                                float impulseX = (velocityX - ((Powder)block).velocityX() > 0? 1: -1) * Math.max(Math.abs(friction * impulseY),
                                        Math.abs(density() * block.density() * (velocityX - ((Powder)block).velocityX())) / (density() + block.density()));
                                block.impulse(impulseX, impulseY, grid.pixelSize());
                                impulse(-impulseX, -impulseY, grid.pixelSize());
                            }else{
                                float impulseX = ((restitution + 1) * density() * block.density() * (velocityX - ((Powder)block).velocityX())) / (density() + block.density());
                                float impulseY = (velocityY - ((Powder)block).velocityY() > 0? 1: -1) * Math.max(Math.abs(friction * impulseX),
                                        Math.abs(density() * block.density() * (velocityY - ((Powder)block).velocityY())) / (density() + block.density()));
                                block.impulse(impulseX, impulseY, grid.pixelSize());
                                impulse(-impulseX, -impulseY, grid.pixelSize());
                            }
                        }else if(block.type() == ElementType.LIQUID){
                            float restitution = (float) Math.sqrt(block.restitution() * restitution());
                            float friction = (float) Math.sqrt(block.friction() * friction());
                            if(blocked[0] == lastAvailable[0]) {
                                float impulseY = ((restitution + 1) * density() * block.density() * (velocityY - ((Liquid)block).velocityY())) / (density() + block.density());
                                float impulseX = (velocityX - ((Liquid)block).velocityX() > 0? 1: -1) * Math.max(Math.abs(friction * impulseY),
                                        Math.abs(density() * block.density() * (velocityX - ((Liquid)block).velocityX())) / (density() + block.density()));
                                block.impulse(impulseX, impulseY, grid.pixelSize());
                                impulse(-impulseX, -impulseY, grid.pixelSize());
                            }else{
                                float impulseX = ((restitution + 1) * density() * block.density() * (velocityX - ((Liquid)block).velocityX())) / (density() + block.density());
                                float impulseY = (velocityY - ((Liquid)block).velocityY() > 0? 1: -1) * Math.max(Math.abs(friction * impulseX),
                                        Math.abs(density() * block.density() * (velocityY - ((Liquid)block).velocityY())) / (density() + block.density()));
                                block.impulse(impulseX, impulseY, grid.pixelSize());
                                impulse(-impulseX, -impulseY, grid.pixelSize());
                            }
                        }
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
                    lastY += yMod;
                    if(grid.get(lastX, lastY) == null){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
                        return false;
                    }
                }else{
                    lastX += xMod;
                    if(grid.get(lastX, lastY) == null){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
                        return false;
                    }
                }
            }else{
                int shouldBeX = Math.round(x + (lastY - y) * ratio * xMod);
                if(shouldBeX != lastX){
                    lastX += xMod;
                    if(grid.get(lastX, lastY) == null){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
                        return false;
                    }
                }else{
                    lastY += yMod;
                    if(grid.get(lastX, lastY) == null){
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
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

    public float velocityX() {
        return velocityX;
    }

    public float velocityY() {
        return velocityY;
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {
        velocityX += x / density();
        velocityY += y / density();
    }
}
