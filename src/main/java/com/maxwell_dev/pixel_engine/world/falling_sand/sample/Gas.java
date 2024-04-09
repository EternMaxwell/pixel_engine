package com.maxwell_dev.pixel_engine.world.falling_sand.sample;

public abstract class Gas<ElementID> extends Element<ElementID>{
    int existenceTime;
    int lastStepTick = -1;
    float velocityX;
    float velocityY;
    float thresholdX = 0;
    float thresholdY = 0;
    float sinkingProcess = 0.0f;

    public Gas(Grid grid, int existenceTime) {
        super(grid);
        this.existenceTime = existenceTime;
        velocityX = grid.default_vx();
        velocityY = grid.default_vy();
    }

    @Override
    public ElementType type() {
        return ElementType.GAS;
    }

    @Override
    public boolean freeFall() {
        return true;
    }

    public boolean step(Grid<?,?,ElementID> grid, int x, int y, int tick) {
        if(lastStepTick == tick)
            return false;
        lastStepTick = tick;

        boolean moved = false;
        boolean sinkingTried = false;

        float accelerationX = grid.gravity_x() * (density() - grid.airDensity()) / density();
        float accelerationY = grid.gravity_y() * (density() - grid.airDensity()) / density();
        velocityX += accelerationX * grid.tickTime();
        velocityY += accelerationY * grid.tickTime();
        velocityY *= grid.airResistance() / 1.1f;
        velocityX *= grid.airResistance() / 1.1f;
        thresholdX += velocityX * grid.tickTime() / grid.pixelSize();
        thresholdY += velocityY * grid.tickTime() / grid.pixelSize();
        int xMod = thresholdX > 0 ? 1 : -1;
        int yMod = thresholdY > 0 ? 1 : -1;
        int xMove = (int) Math.abs(thresholdX);
        int yMove = (int) Math.abs(thresholdY);
        thresholdX -= xMove * xMod;
        thresholdY -= yMove * yMod;
        int[] blocked = new int[2];
        int[] lastAvailable = new int[2];
        if(xMove != 0 || yMove != 0){
            if(!try_move_to(grid, x, y, xMove, yMove, xMod, yMod, blocked, lastAvailable)){
                collideBlock(grid, blocked, lastAvailable);
                if(lastAvailable[0] != x || lastAvailable[1] != y){
                    moved = true;
                }
                if(blockDirOppositeToGravity(grid, blocked[0] - lastAvailable[0], blocked[1] - lastAvailable[1])){
                    int shouldBe[] = new int[]{lastAvailable[0], lastAvailable[1]};
                    tryLTandRT(grid, lastAvailable[0], lastAvailable[1], shouldBe);
                    if(shouldBe[0] != lastAvailable[0] || shouldBe[1] != lastAvailable[1]){
                        grid.set(lastAvailable[0], lastAvailable[1], null);
                        grid.set(shouldBe[0], shouldBe[1], this);
                        moved = true;
                    }
                    float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
                    float downX = grid.gravity_x() / gravity / grid.pixelSize();
                    float downY = grid.gravity_y() / gravity / grid.pixelSize();
                    int topX = Math.round(x - downX);
                    int topY = Math.round(y - downY);
                    if(!moved){
                        Element top = grid.get(topX, topY);
                        if(top != null && top.type() == ElementType.GAS){
                            if(top.density() > density()){
                                sinkingProcess += (top.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                sinkingTried = true;
                                grid.set(shouldBe[0], shouldBe[1], this);
                                if(sinkingProcess >= 1){
                                    grid.set(shouldBe[0], shouldBe[1], top);
                                    grid.set(topX, topY, this);
                                    moved = true;
                                }
                            }
                        } else {
                            int leftTopX = Math.round(x - downX + downY);
                            int leftTopY = Math.round(y - downY - downX);
                            int rightTopX = Math.round(x - downX - downY);
                            int rightTopY = Math.round(y - downY + downX);
                            if(Math.random() < 0.5){
                                Element side = grid.get(leftTopX, leftTopY);
                                if(side != null && side.type() == ElementType.GAS) {
                                    if (side.density() > density()) {
                                        sinkingProcess += (side.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                        sinkingTried = true;
                                        grid.set(shouldBe[0], shouldBe[1], this);
                                        if (sinkingProcess >= 1) {
                                            grid.set(shouldBe[0], shouldBe[1], side);
                                            grid.set(leftTopX, leftTopY, this);
                                            moved = true;
                                        }
                                    }
                                } else {
                                    side = grid.get(rightTopX, rightTopY);
                                    if(side != null && side.type() == ElementType.GAS) {
                                        if (side.density() > density()) {
                                            sinkingProcess += (side.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], side);
                                                grid.set(rightTopX, rightTopY, this);
                                                moved = true;
                                            }
                                        }
                                    }
                                }
                            }else{
                                Element side = grid.get(rightTopX, rightTopY);
                                if(side != null && side.type() == ElementType.GAS) {
                                    if (side.density() > density()) {
                                        sinkingProcess += (side.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                        sinkingTried = true;
                                        grid.set(shouldBe[0], shouldBe[1], this);
                                        if (sinkingProcess >= 1) {
                                            grid.set(shouldBe[0], shouldBe[1], side);
                                            grid.set(rightTopX, rightTopY, this);
                                            moved = true;
                                        }
                                    }
                                } else {
                                    side = grid.get(leftTopX, leftTopY);
                                    if(side != null && side.type() == ElementType.GAS) {
                                        if (side.density() > density()) {
                                            sinkingProcess += (side.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], side);
                                                grid.set(leftTopX, leftTopY, this);
                                                moved = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(!moved && !sinkingTried){
                            float leftX = shouldBe[0] - downY;
                            float leftY = shouldBe[1] + downX;
                            float rightX = shouldBe[0] + downY;
                            float rightY = shouldBe[1] - downX;
                            int leftXInt = Math.round(leftX);
                            int leftYInt = Math.round(leftY);
                            int rightXInt = Math.round(rightX);
                            int rightYInt = Math.round(rightY);
                            Element left = grid.get(leftXInt, leftYInt);
                            Element right = grid.get(rightXInt, rightYInt);
                            if(Math.random() < 0.5){
                                if(left == null) {
                                    grid.set(shouldBe[0], shouldBe[1], null);
                                    grid.set(leftXInt, leftYInt, this);
                                    moved = true;
                                }else if(right == null){
                                    grid.set(shouldBe[0], shouldBe[1], null);
                                    grid.set(rightXInt, rightYInt, this);
                                    moved = true;
                                }
                            }else {
                                if (right == null) {
                                    grid.set(shouldBe[0], shouldBe[1], null);
                                    grid.set(rightXInt, rightYInt, this);
                                    moved = true;
                                } else if (left == null) {
                                    grid.set(shouldBe[0], shouldBe[1], null);
                                    grid.set(leftXInt, leftYInt, this);
                                    moved = true;
                                }
                            }
                            if(!moved){
                                if(Math.random() < 0.5){
                                    if (left.type() == ElementType.GAS) {
                                        if (left.density() > density()) {
                                            sinkingProcess += (left.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], left);
                                                grid.set(leftXInt, leftYInt, this);
                                                moved = true;
                                            }
                                        }
                                    } else if (right.type() == ElementType.GAS) {
                                        if (right.density() > density()) {
                                            sinkingProcess += (right.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], right);
                                                grid.set(rightXInt, rightYInt, this);
                                                moved = true;
                                            }
                                        }
                                    }
                                }else{
                                    if (right.type() == ElementType.GAS) {
                                        if (right.density() > density()) {
                                            sinkingProcess += (right.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], right);
                                                grid.set(rightXInt, rightYInt, this);
                                                moved = true;
                                            }
                                        }
                                    } else if (left.type() == ElementType.GAS) {
                                        if (left.density() > density()) {
                                            sinkingProcess += (left.density() - density()) / density() * grid.tickTime() * gravity / grid.pixelSize();
                                            sinkingTried = true;
                                            grid.set(shouldBe[0], shouldBe[1], this);
                                            if (sinkingProcess >= 1) {
                                                grid.set(shouldBe[0], shouldBe[1], left);
                                                grid.set(leftXInt, leftYInt, this);
                                                moved = true;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }else{
                moved = true;
            }
        }

        return moved;
    }

    public boolean tryLTandRT(Grid<?, ?, ElementID> grid, int x, int y, int[] shouldBe){
        shouldBe[0] = x;
        shouldBe[1] = y;
        float gravity = (float) Math.sqrt(grid.gravity_x() * grid.gravity_x() + grid.gravity_y() * grid.gravity_y());
        float downX = grid.gravity_x() / gravity / grid.pixelSize();
        float downY = grid.gravity_y() / gravity / grid.pixelSize();
        int topX = Math.round(x - downX);
        int topY = Math.round(y - downY);
        if((grid.get(topX, topY) == null) && (grid.valid(topX, topY) || !grid.invalidAsWall())){
            shouldBe[0] = topX;
            shouldBe[1] = topY;
            return true;
        }
        int leftTopX = Math.round(x - downX + downY);
        int leftTopY = Math.round(y - downY - downX);
        int rightTopX = Math.round(x - downX - downY);
        int rightTopY = Math.round(y - downY + downX);
        Element leftTop = grid.get(leftTopX, leftTopY);
        Element rightTop = grid.get(rightTopX, rightTopY);
        if(Math.random() < 0.5){
            if((leftTop == null) && (grid.valid(leftTopX, leftTopY) || !grid.invalidAsWall())){
                shouldBe[0] = leftTopX;
                shouldBe[1] = leftTopY;
                return false;
            }
            if((rightTop == null) && (grid.valid(rightTopX, rightTopY) || !grid.invalidAsWall())){
                shouldBe[0] = rightTopX;
                shouldBe[1] = rightTopY;
                return false;
            }
        }else {
            if((rightTop == null) && (grid.valid(rightTopX, rightTopY) || !grid.invalidAsWall())){
                shouldBe[0] = rightTopX;
                shouldBe[1] = rightTopY;
                return false;
            }
            if((leftTop == null) && (grid.valid(leftTopX, leftTopY) || !grid.invalidAsWall())){
                shouldBe[0] = leftTopX;
                shouldBe[1] = leftTopY;
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
        } else if (block.type() == ElementType.GAS) {
            float restitution = (float)(1 - Math.sqrt((1 - block.restitution()) * (1 - restitution())));
            float friction = (float) Math.sqrt(block.friction() * friction());
            float impulseR;
            float impulseN;
            if (blocked[0] == lastAvailable[0]) {
                impulseR = ((restitution + 1) * density() * block.density() * (velocityY - block.velocityY())) / (density() + block.density());
                impulseN = (velocityX - block.velocityX() > 0 ? 1 : -1) * Math.min(Math.abs(friction * impulseR),
                        Math.abs(density() * block.density() * (velocityX - block.velocityX())) / (density() + block.density()));
                float random = (float) (Math.random() - 0.5) * 0.2f;
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
                float random = (float) (Math.random() - 0.5) * 0.2f;
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
                    if(!grid.valid(lastX, lastY)) {
                        if(grid.invalidAsWall()){
                            blocked[0] = lastX;
                            blocked[1] = lastY;
                            return false;
                        }else{
                            grid.set(lastAvailable[0], lastAvailable[1], null);
                            return true;
                        }
                    }else if(target == null){
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
                    if(!grid.valid(lastX, lastY)) {
                        if (grid.invalidAsWall()) {
                            blocked[0] = lastX;
                            blocked[1] = lastY;
                            return false;
                        } else {
                            grid.set(lastAvailable[0], lastAvailable[1], null);
                            return true;
                        }
                    }else if(target == null){
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
                    if(!grid.valid(lastX, lastY)) {
                        if (grid.invalidAsWall()) {
                            blocked[0] = lastX;
                            blocked[1] = lastY;
                            return false;
                        } else {
                            grid.set(lastAvailable[0], lastAvailable[1], null);
                            return true;
                        }
                    }else if(target == null){
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
                    if(!grid.valid(lastX, lastY)) {
                        if (grid.invalidAsWall()) {
                            blocked[0] = lastX;
                            blocked[1] = lastY;
                            return false;
                        } else {
                            grid.set(lastAvailable[0], lastAvailable[1], null);
                            return true;
                        }
                    }else if(target == null){
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

    public boolean blockDirOppositeToGravity(Grid<?, ?, ElementID> grid, float blockX, float blockY){
        float gravity_x = -grid.gravity_x();
        float gravity_y = -grid.gravity_y();
        if(gravity_x == 0 && gravity_y == 0)
            return false;
        double angle = Math.atan2(gravity_y, gravity_x);
        double angle2 = Math.atan2(blockY, blockX);
        return Math.abs(angle - angle2) <= Math.PI / 4;
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

    @Override
    public float velocityX() {
        return velocityX;
    }

    @Override
    public float velocityY() {
        return velocityY;
    }

    public abstract Element<ElementID> existTimeEndReplaceElement();

    @Override
    public void touch(Grid<?, ?, ElementID> grid, int x, int y) {
        //do nothing
    }

    @Override
    public void impulse(float x, float y, float pixelSize) {
        velocityX += x / density();
        velocityY += y / density();
    }
}
