package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Liquid<ElementID> extends Element<ElementID>{
    int lastTick = -1;
    float velocityX = 0.0f;
    float velocityY = 0.0f;
    int dir = Math.random() > 0.5? 1: -1;
    int lastBlocked = 0;
    boolean falling = true;
    boolean left;

    float thresholdX = 0;
    float thresholdY = 0;

    public Liquid(Grid<?, ?, ElementID> grid) {
        super(grid);
        velocityY = grid.default_vy();
        velocityX = grid.default_vx();
        left = Math.random() > 0.5;
    }

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
        if (lastTick == tick)
            return false;
        lastTick = tick;

        boolean moved = false;

        velocityY += grid.gravity_y() * grid.tickTime();
        velocityX += grid.gravity_x() * grid.tickTime();
        velocityY *= grid.airResistance();
        velocityX *= grid.airResistance();
        thresholdY += velocityY * grid.tickTime();
        thresholdX += velocityX * grid.tickTime();
        int xMod = thresholdX > 0 ? 1 : -1;
        int yMod = thresholdY > 0 ? 1 : -1;
        int xMove = (int) Math.abs(thresholdX);
        int yMove = (int) Math.abs(thresholdY);
        thresholdX -= xMove * xMod;
        thresholdY -= yMove * yMod;
        int[] blocked = new int[2];
        int[] lastAvailable = new int[2];
        if (xMove != 0 || yMove != 0) {
            if (try_move_to(grid, x, y, xMove, yMove, xMod, yMod, blocked, lastAvailable)) {
                moved = true;
            } else {
                collideBlock(grid, blocked, lastAvailable);
                if (blockDirSameToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])) {
                    int shouldBe[] = new int[2];
                    tryLBandRB(grid, lastAvailable[0], lastAvailable[1], shouldBe);
                    if (shouldBe[0] != lastAvailable[0] || shouldBe[1] != lastAvailable[1]) {
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        grid.set(shouldBe[0], shouldBe[1], this);
                        moved = true;
                        lastAvailable[0] = shouldBe[0];
                        lastAvailable[1] = shouldBe[1];
                    }
                    Element block = grid.get(blocked[0], blocked[1]);
                    if (!block.freeFall()) {
                        velocityY = grid.default_vy();
                        velocityX = grid.default_vx();
                        falling = false;
                    }
                }
                float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
                if (gravity != 0) {
                    float downX = grid.gravity_x() / gravity / grid.pixelSize();
                    float downY = grid.gravity_y() / gravity / grid.pixelSize();
                    int belowX = Math.round(lastAvailable[0] + downX);
                    int belowY = Math.round(lastAvailable[1] + downY);
                    Element below = grid.get(belowX, belowY);
                    if (below != null) {
                        float dirX;
                        float dirY;
                        if(!left) {
                            dirX = -downY;
                            dirY = downX;
                        }else{
                            dirX = downY;
                            dirY = -downX;
                        }
                        int i = 0;
                        while(i < dispersionRate()){
                            i++;
                            int targetX = Math.round(lastAvailable[0] + dirX);
                            int targetY = Math.round(lastAvailable[1] + dirY);
                            if(targetY == lastAvailable[1] && targetX == lastAvailable[0]){
                                continue;
                            }
                            if(grid.get(targetX, targetY) == null) {
                                grid.set(lastAvailable[0], lastAvailable[1], null);
                                grid.set(targetX, targetY, this);
                                lastAvailable[0] = targetX;
                                lastAvailable[1] = targetY;
                            }else{
                                if(lastBlocked > 0){
                                    left = !left;
                                    lastBlocked = 0;
                                }
                                lastBlocked++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(!falling){
            float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
            if (gravity != 0) {
                float downX = grid.gravity_x() / gravity / grid.pixelSize();
                float downY = grid.gravity_y() / gravity / grid.pixelSize();
                int belowX = Math.round(lastAvailable[0] + downX);
                int belowY = Math.round(lastAvailable[1] + downY);
                Element below = grid.get(belowX, belowY);
                if (below == null) {
                    grid.set(lastAvailable[0], lastAvailable[1], null);
                    grid.set(belowX, belowY, this);
                    falling = true;
                }
            }
        }
        return moved;
    }

    public boolean tryLBandRB(Grid<?, ?, ElementID> grid, int x, int y, int[] shouldBe){
        shouldBe[0] = x;
        shouldBe[1] = y;
        float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
        float downX = grid.gravity_x() / gravity / grid.pixelSize();
        float downY = grid.gravity_y() / gravity / grid.pixelSize();
        int belowX = Math.round(x + downX);
        int belowY = Math.round(y + downY);
        if(grid.get(belowX, belowY) == null){
            shouldBe[0] = belowX;
            shouldBe[1] = belowY;
            return true;
        }
        int leftBelowX = Math.round(x + downX + downY);
        int leftBelowY = Math.round(y + downY - downX);
        int rightBelowX = Math.round(x + downX - downY);
        int rightBelowY = Math.round(y + downY + downX);
        if(Math.random() < 0.5){
            if(grid.get(leftBelowX, leftBelowY) == null){
                shouldBe[0] = leftBelowX;
                shouldBe[1] = leftBelowY;
                return false;
            }
            if(grid.get(rightBelowX, rightBelowY) == null){
                shouldBe[0] = rightBelowX;
                shouldBe[1] = rightBelowY;
                return false;
            }
        }else {
            if(grid.get(rightBelowX, rightBelowY) == null){
                shouldBe[0] = rightBelowX;
                shouldBe[1] = rightBelowY;
                return false;
            }
            if(grid.get(leftBelowX, leftBelowY) == null){
                shouldBe[0] = leftBelowX;
                shouldBe[1] = leftBelowY;
                return false;
            }
        }
        return false;
    }

    public void collideBlock(Grid<?, ?, ElementID> grid, int[] blocked, int[] lastAvailable) {
        Element block = grid.get(blocked[0], blocked[1]);
        if (block.type() == ElementType.SOLID) {
            float restitution = (float) Math.sqrt(block.restitution() * restitution());
            float friction = (float) Math.sqrt(block.friction() * friction());
            if (blocked[0] == lastAvailable[0]) {
                velocityX = -velocityX * restitution;
                velocityY = velocityY * friction;
            } else {
                velocityY = -velocityY * restitution;
                velocityX = velocityX * friction;
            }
        } else if (block.type() == ElementType.POWDER || block.type() == ElementType.LIQUID) {
            float restitution = (float) Math.sqrt(block.restitution() * restitution());
            float friction = (float) Math.sqrt(block.friction() * friction());
            float impulseR;
            float impulseN;
            if (blocked[0] == lastAvailable[0]) {
                impulseR = ((restitution + 1) * density() * block.density() * (velocityY - block.velocityY())) / (density() + block.density());
                impulseN = (velocityX - block.velocityX() > 0 ? 1 : -1) * Math.min(Math.abs(friction * impulseR),
                        Math.abs(density() * block.density() * (velocityX - block.velocityX())) / (density() + block.density()));
                float random = (float) (Math.random() - 0.5) * 0.8f;
                float sin = (float) Math.sin(random);
                float cos = (float) Math.cos(random);
                float temp = impulseN * cos - impulseR * sin;
                impulseR = impulseN * sin + impulseR * cos;
                impulseN = temp;
                block.impulse(impulseN, impulseR, grid.pixelSize());
                impulse(-impulseN, -impulseR, grid.pixelSize());
            } else {
                impulseR = ((restitution + 1) * density() * block.density() * (velocityX - block.velocityX())) / (density() + block.density());
                impulseN = (velocityY - block.velocityY() > 0 ? 1 : -1) * Math.min(Math.abs(friction * impulseR),
                        Math.abs(density() * block.density() * (velocityY - block.velocityY())) / (density() + block.density()));
                float random = (float) (Math.random() - 0.5) * 0.8f;
                float sin = (float) Math.sin(random);
                float cos = (float) Math.cos(random);
                float temp = impulseN * cos - impulseR * sin;
                impulseR = impulseN * sin + impulseR * cos;
                impulseN = temp;
                block.impulse(impulseR, impulseN, grid.pixelSize());
                impulse(-impulseR, -impulseN, grid.pixelSize());
            }
        }
    }

    public boolean try_move_to(Grid<?, ?, ElementID> grid, int x, int y, int xMove, int yMove, int xMod, int yMod, int[] blocked, int[] lastAvailable){
        int lastX = x;
        int lastY = y;
        lastAvailable[0] = x;
        lastAvailable[1] = y;
        int targetX = x + xMod * xMove;
        int targetY = y + yMod * yMove;
        boolean xLarger = xMove > yMove;
        float ratio = xLarger ? (float) yMove / xMove : (float) xMove / yMove;
        int xMin = Math.min(x, targetX);
        int xMax = Math.max(x, targetX);
        int yMin = Math.min(y, targetY);
        int yMax = Math.max(y, targetY);
        while ((lastX != targetX || lastY != targetY) && (lastX >= xMin && lastX <= xMax && lastY >= yMin && lastY <= yMax)) {
            if(xLarger){
                int shouldBeY = Math.round(y + (lastX - x) * ratio * yMod);
                if(shouldBeY != lastY){
                    lastY += yMod;
                    if(grid.get(lastX, lastY) == null){
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                        grid.set(lastX, lastY, this);
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
                        return false;
                    }
                }else{
                    lastX += xMod;
                    if(grid.get(lastX, lastY) == null){
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                        grid.set(lastX, lastY, this);
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
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                        grid.set(lastX, lastY, this);
                    }else {
                        blocked[0] = lastX;
                        blocked[1] = lastY;
                        return false;
                    }
                }else{
                    lastY += yMod;
                    if(grid.get(lastX, lastY) == null){
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                        grid.set(lastX, lastY, this);
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

    public boolean try_slide_to(Grid<?, ?, ElementID> grid, int x, int y, int xMove, int yMove, int xMod, int yMod, int[] blocked, int[] lastAvailable){
        int lastX = x;
        int lastY = y;
        lastAvailable[0] = x;
        lastAvailable[1] = y;
        int targetX = x + xMod * xMove;
        int targetY = y + yMod * yMove;
        boolean xLarger = xMove > yMove;
        float ratio = xLarger ? (float) yMove / xMove : (float) xMove / yMove;
        int xMin = Math.min(x, targetX);
        int xMax = Math.max(x, targetX);
        int yMin = Math.min(y, targetY);
        int yMax = Math.max(y, targetY);
        while ((lastX != targetX || lastY != targetY) && (lastX >= xMin && lastX <= xMax && lastY >= yMin && lastY <= yMax)) {
            if(xLarger) {
                lastY = Math.round(y + (lastX - x) * ratio * yMod);
                lastX += xMod;
                if (grid.get(lastX, lastY) == null) {
                    lastAvailable[0] = lastX;
                    lastAvailable[1] = lastY;
                    grid.set(lastX, lastY, this);
                } else {
                    blocked[0] = lastX;
                    blocked[1] = lastY;
                    return false;
                }
            }else {
                lastX = Math.round(x + (lastY - y) * ratio * xMod);
                lastY += yMod;
                if (grid.get(lastX, lastY) == null) {
                    lastAvailable[0] = lastX;
                    lastAvailable[1] = lastY;
                    grid.set(lastX, lastY, this);
                } else {
                    blocked[0] = lastX;
                    blocked[1] = lastY;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean blockDirSameToGravity(Grid<?, ?, ElementID> grid, float blockX, float blockY){
        float gravity_x = grid.gravity_x();
        float gravity_y = grid.gravity_y();
        if(gravity_x == 0 && gravity_y == 0)
            return false;
        double angle = Math.atan2(gravity_y, gravity_x);
        double angle2 = Math.atan2(blockY, blockX);
        return Math.abs(angle - angle2) <= Math.PI / 4;
    }

    public float velocityX() {
        return velocityX;
    }

    public float velocityY() {
        return velocityY;
    }

    @Override
    public void touch(Grid<?, ?, ElementID> grid, int x, int y) {
    }

    @Override
    public void impulse(float x, float y, float pixel_size) {
        velocityX += x / density();
        velocityY += y / density();
    }
}
