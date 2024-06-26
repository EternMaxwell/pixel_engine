package fallingsandsampletest.grids_single_thread;

import com.maxwell_dev.pixel_engine.world.falling_sand.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import fallingsandsampletest.Actions;
import fallingsandsampletest.ElementID;
import render.Render;

import java.util.Random;

public class FallingGridMinorRectChunk extends com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid<Render, Actions, ElementID>{

    Chunk[][] chunks;
    int tick = 0;
    boolean inverse = false;
    double resetTag = 0;
    int resetThreshold = 16;
    int flag = 0x3f;

    public int tick(){
        return tick;
    }

    public class Chunk{
        public static final int sleepChunkSizeBit = 3;
        public static final int sleepChunkSize = 1 << sleepChunkSizeBit;
        Element<ElementID>[][] elements;
        boolean[][] awake;
        boolean[][] awakeNext;
        int x;
        int y;

        boolean notFullSleep = false;
        boolean notFullSleepNext = false;

        Rect[][] rects;

        public class Rect{
            public int x,y,xm,ym;
            public int x_next, y_next, xm_next, ym_next;

            public Rect(){
                x = sleepChunkSize;
                y = sleepChunkSize;
                xm = 0;
                ym = 0;
                x_next = sleepChunkSize;
                y_next = sleepChunkSize;
                xm_next = 0;
                ym_next = 0;
            }

            public void reset(){
                x = x_next;
                y = y_next;
                xm = xm_next;
                ym = ym_next;
                x_next = sleepChunkSize;
                y_next = sleepChunkSize;
                xm_next = 0;
                ym_next = 0;
            }

            public void awake(int x, int y){
                this.x = Math.min(x, this.x);
                this.y = Math.min(y, this.y);
                x_next = Math.min(x_next, x);
                y_next = Math.min(y_next, y);
                xm = Math.max(x, this.xm);
                ym = Math.max(y, this.ym);
                xm_next = Math.max(xm_next, x);
                ym_next = Math.max(ym_next, y);
            }
            public boolean awakeAt(int x, int y){
                return x >= this.x && x <= xm && y >= this.y && y <= ym;
            }
        }

        public boolean awake(){
            return notFullSleep;
        }

        public Chunk(int x, int y){
            this.x = x;
            this.y = y;
            elements = new Element[64][64];
            rects = new Rect[64/sleepChunkSize][64/sleepChunkSize];
            for(int i = 0; i < rects.length; i++){
                for(int j = 0; j < rects[0].length; j++){
                    rects[i][j] = new Rect();
                }
            }
            awake = new boolean[64/sleepChunkSize][64/sleepChunkSize];
            awakeNext = new boolean[64/sleepChunkSize][64/sleepChunkSize];
        }

        public void set(int x, int y, Element<ElementID> element){
            elements[x][y] = element;
        }

        public void awake(int x, int y){
            awake[x / sleepChunkSize][y / sleepChunkSize] = true;
            awakeNext[x / sleepChunkSize][y / sleepChunkSize] = true;
            rects[x / sleepChunkSize][y / sleepChunkSize].awake(x % sleepChunkSize, y % sleepChunkSize);
            notFullSleep = true;
            notFullSleepNext = true;
        }

        public boolean awakeAt(int x, int y){
//            return true;
            return awake[x / sleepChunkSize][y / sleepChunkSize];
        }

        public boolean inRectAt(int x, int y){
            return rects[x / sleepChunkSize][y / sleepChunkSize].awakeAt(x % sleepChunkSize, y % sleepChunkSize);
        }

        public Element<ElementID> get(int x, int y){
            return elements[x][y];
        }

        public void reset(){
            awake = awakeNext;
            awakeNext = new boolean[64/sleepChunkSize][64/sleepChunkSize];
            for (Rect[] row : rects) {
                for (Rect rect : row) {
                    rect.reset();
                }
            }
            notFullSleep = notFullSleepNext;
            notFullSleepNext = false;
        }

        public void stepY(Grid grid, int y, int chunk_x, int tick){
            int in_y = y % 64;
            for(int cx = 0; cx < 64 / sleepChunkSize; cx++){
                if(!awake[cx][in_y / sleepChunkSize]){
                    continue;
                }
                for(int xx = rects[cx][in_y / sleepChunkSize].x; xx <= rects[cx][in_y / sleepChunkSize].xm; xx++){
                    int in_x = cx * sleepChunkSize + xx;
                    int targetX = chunk_x * 64 + in_x;
                    Element target = elements[in_x][in_y];
                    if(target != null){
                        target.step(grid, targetX, y, tick);
                    }
                }
            }
        }

        public void stepYReverse(Grid grid, int y, int chunk_x, int tick) {
            int in_y = y % 64;
            for (int cx = 64 / sleepChunkSize - 1; cx >= 0; cx--) {
                if (!awake[cx][in_y / sleepChunkSize]) {
                    continue;
                }
                for (int xx = rects[cx][in_y / sleepChunkSize].xm; xx >= rects[cx][in_y / sleepChunkSize].x; xx--) {
                    int in_x = cx * sleepChunkSize + xx;
                    int targetX = chunk_x * 64 + in_x;
                    Element target = elements[in_x][in_y];
                    if (target != null) {
                        target.step(grid, targetX, y, tick);
                    }
                }
            }
        }
    }

    public FallingGridMinorRectChunk(int xc, int yc) {
        pixelSize = 1;
        setGravity_x(0);
        setGravity_y(-100f);
        chunks = new Chunk[xc][yc];
        for(int x = 0; x < chunks.length; x++){
            for(int y = 0; y < chunks[0].length; y++){
                chunks[x][y] = new Chunk(x, y);
            }
        }
    }
    @Override
    public Element<ElementID> get(int x, int y) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return null;
        }
        Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            return null;
        }
        return chunk.get(x & flag, y & flag);
    }

    @Override
    public boolean valid(int x, int y) {
        return x >= 0 && y >= 0 && x < chunks.length * 64 && y < chunks[0].length * 64;
    }

    @Override
    public boolean invalidAsWall() {
        return false;
    }

    private void awake(int x, int y){
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return;
        }
        Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            chunk = new Chunk(x >> 6, y >> 6);
            chunks[x >> 6][y >> 6] = chunk;
        }
        chunk.awake(x & flag, y & flag);
    }

    @Override
    public void set(int x, int y, Element<ElementID> element) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return;
        }
        Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            chunk = new Chunk(x >> 6, y >> 6);
            chunks[x >> 6][y >> 6] = chunk;
        }
        chunk.set(x & flag, y & flag, element);

        Element<ElementID> side = get(x + 1, y);
        awake(x + 1, y);
        if(side != null){
            side.touch(this, x + 1, y);
        }
        side = get(x - 1, y);
        awake(x - 1, y);
        if(side != null){
            side.touch(this, x - 1, y);
        }
        side = get(x, y + 1);
        awake(x, y + 1);
        if(side != null){
            side.touch(this, x, y + 1);
        }
        side = get(x, y - 1);
        awake(x, y - 1);
        if(side != null){
            side.touch(this, x, y - 1);
        }
        side = get(x + 1, y + 1);
        awake(x + 1, y + 1);
        if(side != null){
            side.touch(this, x + 1, y + 1);
        }
        side = get(x - 1, y - 1);
        awake(x - 1, y - 1);
        if(side != null){
            side.touch(this, x - 1, y - 1);
        }
        side = get(x - 1, y + 1);
        awake(x - 1, y + 1);
        if(side != null){
            side.touch(this, x - 1, y + 1);
        }
        side = get(x + 1, y - 1);
        awake(x + 1, y - 1);
        if(side != null){
            side.touch(this, x + 1, y - 1);
        }
    }

    @Override
    public void removeElementAt(int x, int y) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return;
        }
        Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            return;
        }
        chunk.set(x & flag, y & flag, null);
    }

    @Override
    public Element<ElementID> popElementAt(int x, int y) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return null;
        }
        Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            return null;
        }
        Element<ElementID> element = chunk.get(x & flag, y & flag);
        chunk.set(x & flag, y & flag, null);
        return element;
    }

    @Override
    public double step() {
        long start = System.nanoTime();
        double gravity = Math.sqrt(gravity_x * gravity_x + gravity_y * gravity_y);
        if(resetTag > resetThreshold){
            resetTag = 0;
            for (Chunk[] row : chunks) {
                for (Chunk chunk : row) {
                    chunk.reset();
                }
            }
        }else{
            resetTag += gravity / 100;
        }
        for (int y = 0; y < chunks[0].length * 64; y++) {
//            if(inverse){
//                for (int x = 0; x < chunks.length * 64; x++) {
//                    Chunk chunk = chunks[x >> 6][y >> 6];
//                    if(!chunk.awake()){
//                        x += 63;
//                        continue;
//                    }
//                    if(!chunk.awakeAt(x & flag, y & flag)){
//                        x += Chunk.sleepChunkSize - 1;
//                        continue;
//                    }
//                    Element<ElementID> element = get(x, y);
//                    if (element != null && chunk.inRectAt(x & flag, y & flag)) {
//                        element.step(this, x, y, tick);
//                    }
//                }
//            }else {
//                for (int x = chunks.length * 64 - 1; x >= 0; x--) {
//                    Chunk chunk = chunks[x >> 6][y >> 6];
//                    if(!chunk.awake()){
//                        x -= 63;
//                        continue;
//                    }
//                    if(!chunk.awakeAt(x & flag, y & flag)){
//                        x -= Chunk.sleepChunkSize - 1;
//                        continue;
//                    }
//                    Element<ElementID> element = get(x, y);
//                    if (element != null && chunk.inRectAt(x & flag, y & flag)) {
//                        element.step(this, x, y, tick);
//                    }
//                }
//            }
            if(inverse){
                for (int x = 0; x < chunks.length; x++) {
                    Chunk chunk = chunks[x][y >> 6];
                    if(chunk == null || !chunk.awake()){
                        continue;
                    }
                    chunk.stepY(this, y, x, tick);
                }
            }else{
                for (int x = chunks.length - 1; x >= 0; x--) {
                    Chunk chunk = chunks[x][y >> 6];
                    if(chunk == null || !chunk.awake()){
                        continue;
                    }
                    chunk.stepYReverse(this, y, x, tick);
                }
            }
        }
        randomTick();
        inverse = !inverse;
        tick++;
        return (System.nanoTime() - start) / 1e6;
    }

    private void randomTick() {
        Random random = new Random();
        for (Chunk[] row : chunks) {
            for (Chunk chunk : row) {
                if (chunk != null) {
                    for (int i = 0; i < 64; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 1);
                        }
                    }
                    for (int i = 0; i < 32; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 2);
                        }
                    }
                    for (int i = 0; i < 16; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 3);
                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 4);
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 5);
                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        int targetX = random.nextInt(64);
                        int targetY = random.nextInt(64);
                        Element<ElementID> element = chunk.get(targetX, targetY);
                        if (element != null) {
                            element.randomTick(this, targetX + chunk.x * 64, targetY + chunk.y * 64, tick, 6);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Render renderer) {
        renderElements(renderer);
        renderLine(renderer);
    }

    private void renderElements(Render renderer){
        for (int x = 0; x < chunks.length * 64; x++) {
            for (int y = 0; y < chunks[0].length * 64; y++) {
                Element<ElementID> element = get(x, y);
                if (element != null) {
                    float[] color = element.color().clone();
//                    if(!element.freeFall()){
//                        color[0] *= 0.5F;
//                        color[1] *= 0.5F;
//                        color[2] *= 0.5F;
//                    }
//                    if(element.type() == ElementType.LIQUID){
//                        color[0] = -element.velocityY() * 0.01f + 0.5f;
//                        color[2] = element.velocityX() * 0.01f + 0.5f;
//                    }
                    renderer.pixelDrawer.draw(x * pixelSize + pixelSize / 2, y * pixelSize + pixelSize / 2, pixelSize, color[0], color[1], color[2], color[3]);
                }
            }
        }
        renderer.pixelDrawer.flush();
    }

    private void renderLine(Render renderer){
        for(int x = 0; x < chunks.length; x++){
            for(int y = 0; y < chunks[0].length; y++){
                Chunk chunk = chunks[x][y];
                if(chunk != null){
                    float alpha = 0.2f;
                    float alpha2 = 0.2f;

                    float lb_x = x * 64 * pixelSize;
                    float lb_y = y * 64 * pixelSize;
                    float rt_x = (x + 1) * 64 * pixelSize;
                    float rt_y = (y + 1) * 64 * pixelSize;

//                    float rect_x = chunk.rect_x * pixelSize + lb_x;
//                    float rect_y = chunk.rect_y * pixelSize + lb_y;
//                    float rect_xm = (chunk.rect_xm + 1) * pixelSize + lb_x;
//                    float rect_ym = (chunk.rect_ym + 1) * pixelSize + lb_y;

                    renderer.lineDrawer.draw(lb_x, lb_y, rt_x, lb_y, 1, 1, 1, alpha);
                    renderer.lineDrawer.draw(lb_x, lb_y, lb_x, rt_y, 1, 1, 1, alpha);
                    renderer.lineDrawer.draw(rt_x, lb_y, rt_x, rt_y, 1, 1, 1, alpha);
                    renderer.lineDrawer.draw(lb_x, rt_y, rt_x, rt_y, 1, 1, 1, alpha);

//                    if(chunk.rect_x != 64 && chunk.rect_y != 64 && chunk.rect_xm != 0 && chunk.rect_ym != 0){
//                        renderer.lineDrawer.draw(rect_x, rect_y, rect_xm, rect_y, 0, 0, 1, alpha2);
//                        renderer.lineDrawer.draw(rect_x, rect_y, rect_x, rect_ym, 0, 0, 1, alpha2);
//                        renderer.lineDrawer.draw(rect_xm, rect_y, rect_xm, rect_ym, 0, 0, 1, alpha2);
//                        renderer.lineDrawer.draw(rect_x, rect_ym, rect_xm, rect_ym, 0, 0, 1, alpha2);
//                    }

                    for(int sx = 0; sx < 64 / Chunk.sleepChunkSize; sx++){
                        for(int sy = 0; sy < 64 / Chunk.sleepChunkSize; sy++){
                            if(chunk.awake[sx][sy]){
                                float rect_x = sx * Chunk.sleepChunkSize * pixelSize + lb_x;
                                float rect_y = sy * Chunk.sleepChunkSize * pixelSize + lb_y;
                                float rect_xm = (sx + 1) * Chunk.sleepChunkSize * pixelSize + lb_x;
                                float rect_ym = (sy + 1) * Chunk.sleepChunkSize * pixelSize + lb_y;
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_xm, rect_y, 1, 0, 1, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_x, rect_ym, 1, 0, 1, alpha2);
                                renderer.lineDrawer.draw(rect_xm, rect_y, rect_xm, rect_ym, 1, 0, 1, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_ym, rect_xm, rect_ym, 1, 0, 1, alpha2);
                            }
                        }
                    }

                    for(int sx = 0; sx < 64 / Chunk.sleepChunkSize; sx++){
                        for(int sy = 0; sy < 64 / Chunk.sleepChunkSize; sy++){
                            if(chunk.awake[sx][sy]){
                                float rect_x = sx * Chunk.sleepChunkSize * pixelSize + lb_x + chunk.rects[sx][sy].x * pixelSize;
                                float rect_y = sy * Chunk.sleepChunkSize * pixelSize + lb_y + chunk.rects[sx][sy].y * pixelSize;
                                float rect_xm = sx * Chunk.sleepChunkSize * pixelSize + lb_x + chunk.rects[sx][sy].xm * pixelSize + pixelSize;
                                float rect_ym = sy * Chunk.sleepChunkSize * pixelSize + lb_y + chunk.rects[sx][sy].ym * pixelSize + pixelSize;
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_xm, rect_y, 1, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_x, rect_ym, 1, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_xm, rect_y, rect_xm, rect_ym, 1, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_ym, rect_xm, rect_ym, 1, 1, 0, alpha2);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int[] basePos() {
        return new int[]{0,0};
    }

    @Override
    public void action(double x, double y, Actions action, double[] arguments) {

    }

    @Override
    public float airDensity() {
        return 0.028f;
    }

    @Override
    public float default_vx() {
        return 0;
    }

    @Override
    public float default_vy() {
        return 0;
    }

    @Override
    public float tickTime() {
        return 1/60f;
    }

    @Override
    public float airResistance() {
        return 0.98f;
    }
}
