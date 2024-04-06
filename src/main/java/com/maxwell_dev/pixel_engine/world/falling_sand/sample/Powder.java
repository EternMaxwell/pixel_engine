package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Powder<ElementID> extends Element<ElementID> {
    private int lastTick = -1;
    private float velocityX;
    private float velocityY;
    private float thresholdX = 0.0f;
    private float thresholdY = 0.0f;
    private boolean falling = true;
    private float sinkProcess = 0.0f;
    private boolean sinkTried = false;

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
        sinkTried = false;

        if(falling){
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
                    if(blockDirSameToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])){
                        float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
                        float downX = grid.gravity_x() / gravity / grid.pixelSize();
                        float downY = grid.gravity_y() / gravity / grid.pixelSize();
                        int shouldBe[] = new int[2];
                        tryLBandRB(grid, lastAvailable[0], lastAvailable[1], shouldBe);
                        if (shouldBe[0] != lastAvailable[0] || shouldBe[1] != lastAvailable[1]) {
                            grid.set(lastAvailable[0], lastAvailable[1], grid.get(shouldBe[0], shouldBe[1]));
                            grid.set(shouldBe[0], shouldBe[1], this);
                            moved = true;
                        }
                        if (gravity != 0) {
                            int belowX = Math.round(shouldBe[0] + downX);
                            int belowY = Math.round(shouldBe[1] + downY);
                            Element below = grid.get(belowX, belowY);
                            if (below != null && below.type() == ElementType.LIQUID) {
                                if (below.density() < density()) {
                                    sinkProcess += gravity * grid.tickTime() / grid.pixelSize() * (density() - below.density()) / density() / 2;
                                    if (sinkProcess > 1) {
                                        grid.set(shouldBe[0], shouldBe[1], below);
                                        grid.set(belowX, belowY, this);
                                        moved = true;
                                        sinkProcess = 0;
                                    }
                                    sinkTried = true;
                                }
                            }else{
                                if(Math.random() < 0.5){
                                    Element side = grid.get(Math.round(shouldBe[0] + downX - downY), Math.round(shouldBe[1] + downY + downX));
                                    if(side != null && side.type() == ElementType.LIQUID && side.density() < density()){
                                        sinkProcess += (density() - side.density()) / density() * grid.tickTime() / grid.pixelSize() * gravity / 2;
                                        if (sinkProcess > 1) {
                                            grid.set(shouldBe[0], shouldBe[1], side);
                                            grid.set(Math.round(shouldBe[0] + downX - downY), Math.round(shouldBe[1] + downY + downX), this);
                                            moved = true;
                                            sinkProcess = 0;
                                        }
                                        sinkTried = true;
                                    }else{
                                        side = grid.get(Math.round(shouldBe[0] + downX + downY), Math.round(shouldBe[1] + downY - downX));
                                        if(side != null && side.type() == ElementType.LIQUID && side.density() < density()) {
                                            sinkProcess += (density() - side.density()) / density() * grid.tickTime() / grid.pixelSize() * gravity / 2;
                                            if (sinkProcess > 1) {
                                                grid.set(shouldBe[0], shouldBe[1], side);
                                                grid.set(Math.round(shouldBe[0] + downX + downY), Math.round(shouldBe[1] + downY - downX), this);
                                                moved = true;
                                                sinkProcess = 0;
                                            }
                                            sinkTried = true;
                                        }
                                    }
                                }else{
                                    Element side = grid.get(Math.round(shouldBe[0] + downX + downY), Math.round(shouldBe[1] + downY - downX));
                                    if(side != null && side.type() == ElementType.LIQUID && side.density() < density()){
                                        sinkProcess += (density() - side.density()) / density() * grid.tickTime() / grid.pixelSize() * gravity / 2;
                                        if (sinkProcess > 1) {
                                            grid.set(shouldBe[0], shouldBe[1], side);
                                            grid.set(Math.round(shouldBe[0] + downX + downY), Math.round(shouldBe[1] + downY - downX), this);
                                            moved = true;
                                            sinkProcess = 0;
                                        }
                                        sinkTried = true;
                                    }else{
                                        side = grid.get(Math.round(shouldBe[0] + downX - downY), Math.round(shouldBe[1] + downY + downX));
                                        if(side != null && side.type() == ElementType.LIQUID && side.density() < density()) {
                                            sinkProcess += (density() - side.density()) / density() * grid.tickTime() / grid.pixelSize() * gravity / 2;
                                            if (sinkProcess > 1) {
                                                grid.set(shouldBe[0], shouldBe[1], side);
                                                grid.set(Math.round(shouldBe[0] + downX - downY), Math.round(shouldBe[1] + downY + downX), this);
                                                moved = true;
                                                sinkProcess = 0;
                                            }
                                            sinkTried = true;
                                        }
                                    }
                                }
                            }
                            if ((below != null && below.type() != ElementType.GAS) && !below.freeFall() && !sinkTried) {
                                float slideV = velocityX * downY - velocityY * downX;
                                if (Math.abs(slideV) > 0.001f * grid.pixelSize() / grid.tickTime()) {
                                    if (slideV > 0) {
                                        float extraX = shouldBe[0] - downY;
                                        int extraXInt = Math.round(extraX);
                                        int extraYInt = Math.round(shouldBe[1] + downX);
                                        if (extraXInt != shouldBe[0] || extraYInt != shouldBe[1]) {
                                            Element extra = grid.get(extraXInt, extraYInt);
                                            if (extra == null || extra.type() == ElementType.GAS) {
                                                grid.set(shouldBe[0], shouldBe[1], extra);
                                                grid.set(extraXInt, extraYInt, this);
                                                moved = true;
                                            }
                                        }
                                    } else {
                                        float extraX = shouldBe[0] + downY;
                                        int extraXInt = Math.round(extraX);
                                        int extraYInt = Math.round(shouldBe[1] - downX);
                                        if (extraXInt != shouldBe[0] || extraYInt != shouldBe[1]) {
                                            Element extra = grid.get(extraXInt, extraYInt);
                                            if (extra == null || extra.type() == ElementType.GAS) {
                                                grid.set(shouldBe[0], shouldBe[1], extra);
                                                grid.set(extraXInt, extraYInt, this);
                                                moved = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Element block = grid.get(blocked[0], blocked[1]);
                        if (!block.freeFall() && !moved && !sinkTried) {
                            falling = false;
                            velocityY = grid.default_vy();
                            velocityX = grid.default_vx();
                        }
                    }
                }
            }
        }else{
            float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
            if(gravity == 0)
                return false;
            float downX = grid.gravity_x() / gravity / grid.pixelSize();
            float downY = grid.gravity_y() / gravity / grid.pixelSize();
            int belowX = Math.round(x + downX);
            int belowY = Math.round(y + downY);
            Element below = grid.get(belowX, belowY);
            if (below == null || below.type() == ElementType.LIQUID || below.type() == ElementType.GAS) {
                falling = true;
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
        Element leftBelow = grid.get(leftBelowX, leftBelowY);
        Element rightBelow = grid.get(rightBelowX, rightBelowY);
        if(Math.random() < 0.5){
            if(leftBelow == null || leftBelow.type() == ElementType.GAS){
                shouldBe[0] = leftBelowX;
                shouldBe[1] = leftBelowY;
                return false;
            }
            if(rightBelow == null || rightBelow.type() == ElementType.GAS){
                shouldBe[0] = rightBelowX;
                shouldBe[1] = rightBelowY;
                return false;
            }
        }else {
            if(rightBelow == null || rightBelow.type() == ElementType.GAS){
                shouldBe[0] = rightBelowX;
                shouldBe[1] = rightBelowY;
                return false;
            }
            if(leftBelow == null || leftBelow.type() == ElementType.GAS){
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
            float restitution = (float)(1 - Math.sqrt((1 - block.restitution()) * (1 - restitution())));
            float friction = (float) Math.sqrt(block.friction() * friction());
            if (blocked[0] == lastAvailable[0]) {
                velocityX = -velocityX * restitution;
                velocityY = velocityY * friction;
            } else {
                velocityY = -velocityY * restitution;
                velocityX = velocityX * friction;
            }
        } else if (block.type() == ElementType.POWDER || block.type() == ElementType.LIQUID) {
            float restitution = (float)(1 - Math.sqrt((1 - block.restitution()) * (1 - restitution())));
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
                    Element target = grid.get(lastX, lastY);
                    if(target == null || target.type() == ElementType.GAS){
                        grid.set(lastAvailable[0], lastAvailable[1], target);
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
                    Element target = grid.get(lastX, lastY);
                    if(target == null || target.type() == ElementType.GAS){
                        grid.set(lastAvailable[0], lastAvailable[1], target);
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
                    Element target = grid.get(lastX, lastY);
                    if(target == null || target.type() == ElementType.GAS){
                        grid.set(lastAvailable[0], lastAvailable[1], target);
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
                    Element target = grid.get(lastX, lastY);
                    if(target == null || target.type() == ElementType.GAS){
                        grid.set(lastAvailable[0], lastAvailable[1], target);
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

//    public boolean try_slide_to(Grid<?, ?, ElementID> grid, int x, int y, int xMove, int yMove, int xMod, int yMod, int[] blocked, int[] lastAvailable){
//        int lastX = x;
//        int lastY = y;
//        lastAvailable[0] = x;
//        lastAvailable[1] = y;
//        int targetX = x + xMod * xMove;
//        int targetY = y + yMod * yMove;
//        boolean xLarger = xMove > yMove;
//        float ratio = xLarger ? (float) yMove / xMove : (float) xMove / yMove;
//        int xMin = Math.min(x, targetX);
//        int xMax = Math.max(x, targetX);
//        int yMin = Math.min(y, targetY);
//        int yMax = Math.max(y, targetY);
//        while ((lastX != targetX || lastY != targetY) && (lastX >= xMin && lastX <= xMax && lastY >= yMin && lastY <= yMax)) {
//            if(xLarger) {
//                lastY = Math.round(y + (lastX - x) * ratio * yMod);
//                lastX += xMod;
//                if (grid.get(lastX, lastY) == null) {
//                    lastAvailable[0] = lastX;
//                    lastAvailable[1] = lastY;
//                    grid.set(lastX, lastY, this);
//                } else {
//                    blocked[0] = lastX;
//                    blocked[1] = lastY;
//                    return false;
//                }
//            }else {
//                lastX = Math.round(x + (lastY - y) * ratio * xMod);
//                lastY += yMod;
//                if (grid.get(lastX, lastY) == null) {
//                    lastAvailable[0] = lastX;
//                    lastAvailable[1] = lastY;
//                    grid.set(lastX, lastY, this);
//                } else {
//                    blocked[0] = lastX;
//                    blocked[1] = lastY;
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

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
