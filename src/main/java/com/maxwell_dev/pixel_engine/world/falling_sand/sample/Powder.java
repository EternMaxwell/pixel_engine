package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Powder<ElementID> extends Element<ElementID> {
    private int lastTick = -1;
    private float velocityX;
    private float velocityY;
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
        if (lastTick == tick)
            return false;
        lastTick = tick;

        boolean moved = false;

        if (falling) {
            velocityY += grid.gravity_y() * grid.tickTime();
            velocityX += grid.gravity_x() * grid.tickTime();
            velocityY *= grid.airResistance();
            velocityX *= grid.airResistance();
            thresholdX += velocityX * grid.tickTime() / grid.pixelSize();
            thresholdY += velocityY * grid.tickTime() / grid.pixelSize();
            int xMod = thresholdX > 0 ? 1 : -1;
            int yMod = thresholdY > 0 ? 1 : -1;
            int xMove = (int) Math.abs(thresholdX);
            int yMove = (int) Math.abs(thresholdY);
//            thresholdX = xMove == 0 ? thresholdX : 0;
//            thresholdY = yMove == 0 ? thresholdY : 0;
            thresholdX -= xMove * xMod;
            thresholdY -= yMove * yMod;
            if (xMove != 0 || yMove != 0) {
                int[] lastAvailable = new int[2];
                int[] blocked = new int[2];
                if(tryGetTo(grid, x, y, xMove, yMove, xMod, yMod, blocked, lastAvailable)) {
                    if(x != lastAvailable[0] || y != lastAvailable[1]){
                        grid.set(x, y, null);
                        grid.set(lastAvailable[0], lastAvailable[1], this);
                        moved = true;
                    }
                }else{
                    if(x != lastAvailable[0] || y != lastAvailable[1]){
                        grid.set(x, y, null);
                        grid.set(lastAvailable[0], lastAvailable[1], this);
                        moved = true;
                    }
                    collideBlock(grid, blocked, lastAvailable);
                    if(blockDirSameToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])){
                        int[] shouldBe = new int[2];
                        tryLBandRB(grid, lastAvailable[0], lastAvailable[1], shouldBe);
                        if((lastAvailable[0] != shouldBe[0] || lastAvailable[1] != shouldBe[1])){
                            grid.set(lastAvailable[0], lastAvailable[1], null);
                            grid.set(shouldBe[0], shouldBe[1], this);
                            moved = true;
                        }else{
                            falling = grid.get(blocked[0], blocked[1]).freeFall();
                            if(!falling){
                                velocityX = grid.default_vx();
                                velocityY = grid.default_vy();
                            }
                            shouldBe(grid, lastAvailable[0], lastAvailable[1], shouldBe);
                            if((lastAvailable[0] != shouldBe[0] || lastAvailable[1] != shouldBe[1])){
                                grid.set(lastAvailable[0], lastAvailable[1], null);
                                grid.set(shouldBe[0], shouldBe[1], this);
                                moved = true;
                            }
                        }
                        if(!grid.get(blocked[0], blocked[1]).freeFall()){
                            //TODO: give spread speed here
                            float gravityLength = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
                            float random = (float) Math.random();
                            float velocity = ((float) Math.sqrt(velocityX * velocityX * random + velocityY * velocityY) + 0.4f * grid.pixelSize() / grid.tickTime() * (1 - random));
                            float dot = (grid.gravity_x() * velocityY - grid.gravity_y() * velocityX) / (gravityLength * velocity);
                            int[] dir = new int[2];
                            float factor = 1f;
                            if(dot <= 0.3f){
                                if(Math.random() > 0.5) {
                                    dir[0] = -1;
                                    dir[1] = 1;
                                }else{
                                    dir[0] = 1;
                                    dir[1] = -1;
                                }
                                float newVx = dir[0] * grid.gravity_y() / gravityLength * velocity;
                                float newVy = dir[1] * grid.gravity_x() / gravityLength * velocity;
                                velocityX = newVx * factor;
                                velocityY = newVy * factor;
                            }else {
                                if (dot > 0) {
                                    dir[0] = -1;
                                    dir[1] = 1;
                                } else {
                                    dir[0] = 1;
                                    dir[1] = -1;
                                }
                                velocityX = dir[0] * velocity * grid.gravity_y() / gravityLength * factor;
                                velocityY = dir[1] * velocity * grid.gravity_x() / gravityLength * factor;
                            }
                        }
                    }
                }
            }
        }else{
            float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
            if(gravity == 0)
                return false;
            float downX = grid.gravity_x() / gravity;
            float downY = grid.gravity_y() / gravity;
            int belowX = Math.round(x + downX);
            int belowY = Math.round(y + downY);
            if(grid.get(belowX, belowY) == null){
                grid.set(x, y, null);
                grid.set(belowX, belowY, this);
                moved = true;
                falling = true;
            }
        }
        return moved;
    }

    public boolean tryLBandRB(Grid<?, ?, ElementID> grid, int x, int y, int[] shouldBe){
        shouldBe[0] = x;
        shouldBe[1] = y;
        float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
        float downX = grid.gravity_x() / gravity;
        float downY = grid.gravity_y() / gravity;
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
        } else if (block.type() == ElementType.POWDER) {
            float restitution = (float) Math.sqrt(block.restitution() * restitution());
            float friction = (float) Math.sqrt(block.friction() * friction());
            if (blocked[0] == lastAvailable[0]) {
                float impulseY = ((restitution + 1) * density() * block.density() * (velocityY - ((Powder) block).velocityY())) / (density() + block.density());
                float impulseX = (velocityX - ((Powder) block).velocityX() > 0 ? 1 : -1) * Math.max(Math.abs(friction * impulseY),
                        Math.abs(density() * block.density() * (velocityX - ((Powder) block).velocityX())) / (density() + block.density()));
                block.impulse(impulseX, impulseY, grid.pixelSize());
                impulse(-impulseX, -impulseY, grid.pixelSize());
            } else {
                float impulseX = ((restitution + 1) * density() * block.density() * (velocityX - ((Powder) block).velocityX())) / (density() + block.density());
                float impulseY = (velocityY - ((Powder) block).velocityY() > 0 ? 1 : -1) * Math.max(Math.abs(friction * impulseX),
                        Math.abs(density() * block.density() * (velocityY - ((Powder) block).velocityY())) / (density() + block.density()));
                block.impulse(impulseX, impulseY, grid.pixelSize());
                impulse(-impulseX, -impulseY, grid.pixelSize());
            }
        } else if (block.type() == ElementType.LIQUID) {
            float restitution = (float) Math.sqrt(block.restitution() * restitution());
            float friction = (float) Math.sqrt(block.friction() * friction());
            if (blocked[0] == lastAvailable[0]) {
                float impulseY = ((restitution + 1) * density() * block.density() * (velocityY - ((Liquid) block).velocityY())) / (density() + block.density());
                float impulseX = (velocityX - ((Liquid) block).velocityX() > 0 ? 1 : -1) * Math.max(Math.abs(friction * impulseY),
                        Math.abs(density() * block.density() * (velocityX - ((Liquid) block).velocityX())) / (density() + block.density()));
                block.impulse(impulseX, impulseY, grid.pixelSize());
                impulse(-impulseX, -impulseY, grid.pixelSize());
            } else {
                float impulseX = ((restitution + 1) * density() * block.density() * (velocityX - ((Liquid) block).velocityX())) / (density() + block.density());
                float impulseY = (velocityY - ((Liquid) block).velocityY() > 0 ? 1 : -1) * Math.max(Math.abs(friction * impulseX),
                        Math.abs(density() * block.density() * (velocityY - ((Liquid) block).velocityY())) / (density() + block.density()));
                block.impulse(impulseX, impulseY, grid.pixelSize());
                impulse(-impulseX, -impulseY, grid.pixelSize());
            }
        }
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
                        lastAvailable[0] = lastX;
                        lastAvailable[1] = lastY;
                        grid.set(lastX, lastY, grid.get(lastX, lastY));
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
                        grid.set(lastX, lastY, grid.get(lastX, lastY));
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
                        grid.set(lastX, lastY, grid.get(lastX, lastY));
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
                        grid.set(lastX, lastY, grid.get(lastX, lastY));
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

    /**
     * @return if need to be set to free falling.
     */
    public boolean shouldBe(Grid grid, int x, int y, int[] shouldBe){
        velocityY += grid.gravity_y() * grid.tickTime();
        velocityX += grid.gravity_x() * grid.tickTime();
        thresholdX += velocityX * grid.tickTime()/grid.pixelSize();
        thresholdY += velocityY * grid.tickTime()/grid.pixelSize();
        int xMod = thresholdX > 0 ? 1 : -1;
        int yMod = thresholdY > 0 ? 1 : -1;
        int xMove = (int) Math.abs(thresholdX);
        int yMove = (int) Math.abs(thresholdY);
//        thresholdX = xMove == 0 ? thresholdX : 0;
//        thresholdY = yMove == 0 ? thresholdY : 0;
        thresholdX -= xMove * xMod;
        thresholdY -= yMove * yMod;
        if(xMove == 0 && yMove == 0){
            shouldBe[0] = x;
            shouldBe[1] = y;
            return false;
        }else{
            int lastX = x;
            int lastY = y;
            shouldBe[0] = x;
            shouldBe[1] = y;
            int totalMove = Math.round((float) Math.sqrt(xMove * xMove + yMove * yMove));
            float velocity = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            float gravityLength = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
            if(gravityLength == 0)
                return true;
            float normalizedVx = velocityX / velocity;
            float normalizedVy = velocityY / velocity;
            float downX = grid.gravity_x() / gravityLength;
            float downY = grid.gravity_y() / gravityLength;
            float currentX = x;
            float currentY = y;
            for(int i = 0; i < totalMove; i++){
                int belowX = Math.round(currentX + downX);
                int belowY = Math.round(currentY + downY);
                if(grid.get(belowX, belowY) == null){
                    shouldBe[0] = belowX;
                    shouldBe[1] = belowY;
                    currentY = belowY;
                    currentX = belowX;
                }
                currentX += normalizedVx;
                currentY += normalizedVy;
                belowX = Math.round(currentX + downX);
                belowY = Math.round(currentY + downY);
                int roundX = Math.round(currentX);
                int roundY = Math.round(currentY);
                if(grid.get(belowX, belowY) == null){
                    shouldBe[0] = belowX;
                    shouldBe[1] = belowY;
                    currentY = belowY;
                    currentX = belowX;
                    belowX = Math.round(currentX + downX);
                    belowY = Math.round(currentY + downY);
                    if(grid.get(belowX, belowY) == null){
                        return true;
                    }
                }else if(grid.get(roundX, roundY) == null){
                    shouldBe[0] = roundX;
                    shouldBe[1] = roundY;
                    return false;
                }else{
                    return false;
                }
            }
            return false;
        }
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

    @Override
    public void touch(Grid<?, ?, ElementID> grid, int x, int y) {
        if(Math.random() < freeFallPossibility())
            falling = true;
    }

    public abstract float freeFallPossibility();
}
