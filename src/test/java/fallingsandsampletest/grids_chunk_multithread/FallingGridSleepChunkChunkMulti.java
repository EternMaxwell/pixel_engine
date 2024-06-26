package fallingsandsampletest.grids_chunk_multithread;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import fallingsandsampletest.Actions;
import fallingsandsampletest.ElementID;
import render.Render;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

public class FallingGridSleepChunkChunkMulti extends com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid<Render, Actions, ElementID>{

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
        public Chunk(int x, int y){
            this.x = x;
            this.y = y;
            elements = new Element[64][64];
            awake = new boolean[64/sleepChunkSize][64/sleepChunkSize];
            awakeNext = new boolean[64/sleepChunkSize][64/sleepChunkSize];
        }

        public void set(int x, int y, Element<ElementID> element){
            elements[x][y] = element;
        }

        public void awake(int x, int y){
            awake[x / sleepChunkSize][y / sleepChunkSize] = true;
            awakeNext[x / sleepChunkSize][y / sleepChunkSize] = true;
        }

        public boolean awakeAt(int x, int y){
//            return true;
            return awake[x / sleepChunkSize][y / sleepChunkSize];
        }

        public Element<ElementID> get(int x, int y){
            return elements[x][y];
        }

        public void reset(){
            awake = awakeNext;
            awakeNext = new boolean[64/sleepChunkSize][64/sleepChunkSize];
        }

        public void stepAll(Grid grid, int chunk_x, int chunk_y, int tick){
            for(int sy = 0; sy < 64 / sleepChunkSize; sy++){
                for (int sx = 0; sx < 64 / sleepChunkSize; sx++){
                    if(awake[sx][sy]){
                        for(int y = sy * sleepChunkSize; y < (sy + 1) * sleepChunkSize; y++){
                            for(int x = sx * sleepChunkSize; x < (sx + 1) * sleepChunkSize; x++){
                                Element<ElementID> element = get(x, y);
                                if(element != null){
                                    element.step(grid, x + chunk_x * 64, y + chunk_y * 64, tick);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void stepAllReverse(Grid grid, int chunk_x, int chunk_y, int tick){
            for(int sy = 0; sy < 64 / sleepChunkSize; sy++){
                for (int sx = 64 / sleepChunkSize - 1; sx >= 0; sx--){
                    if(awake[sx][sy]){
                        for(int y = sy * sleepChunkSize; y < (sy + 1) * sleepChunkSize; y++){
                            for(int x = (sx + 1) * sleepChunkSize - 1; x >= sx * sleepChunkSize; x--){
                                Element<ElementID> element = get(x, y);
                                if(element != null){
                                    element.step(grid, x + chunk_x * 64, y + chunk_y * 64, tick);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public FallingGridSleepChunkChunkMulti(int xc, int yc) {
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
        FallingGridSleepChunkChunkMulti.Chunk chunk = chunks[x >> 6][y >> 6];
        if(chunk == null){
            chunk = new FallingGridSleepChunkChunkMulti.Chunk(x >> 6, y >> 6);
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

    ExecutorService executor = new ThreadPoolExecutor(2, 8,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(256)
    );
    Set<Future> futures = new HashSet<>();

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
//        for (int y = 0; y < chunks[0].length * 64; y++) {
//            if(inverse){
//                for (int x = 0; x < chunks.length * 64; x++) {
//                    Chunk chunk = chunks[x >> 6][y >> 6];
//                    Element<ElementID> element = get(x, y);
//                    if(!chunk.awakeAt(x & flag, y & flag)){
//                        x += Chunk.sleepChunkSize - 1;
//                        continue;
//                    }
//                    if (element != null && chunk.awakeAt(x & flag, y & flag)) {
//                        element.step(this, x, y, tick);
//                    }
//                }
//            }else {
//                for (int x = chunks.length * 64 - 1; x >= 0; x--) {
//                    Chunk chunk = chunks[x >> 6][y >> 6];
//                    Element<ElementID> element = get(x, y);
//                    if(!chunk.awakeAt(x & flag, y & flag)){
//                        x -= Chunk.sleepChunkSize - 1;
//                        continue;
//                    }
//                    if (element != null && chunk.awakeAt(x & flag, y & flag)) {
//                        element.step(this, x, y, tick);
//                    }
//                }
//            }
//        }
        int[] oy = new int[]{0,1};
        if(Math.random() > 0.5){
            oy[0] = 1;
            oy[1] = 0;
        }
        int[] ox = new int[]{0,1};
        if(Math.random() > 0.5){
            ox[0] = 1;
            ox[1] = 0;
        }
        for(int sy: oy){
            for(int sx: ox){
                for (int cy = sy; cy < chunks[0].length; cy += 2){
                    for(int cx = sx; cx < chunks.length; cx += 2){
                        Chunk chunk = chunks[cx][cy];
                        if(chunk == null){
                            continue;
                        }
                        int y = cy << 6;
                        int finalCy = cy;
                        int finalCx = cx;
                        futures.add(executor.submit(() -> {
                            if (inverse) {
                                chunk.stepAll(this, finalCx, finalCy, tick);
                            } else {
                                chunk.stepAllReverse(this, finalCx, finalCy, tick);
                            }
                        }));
                    }
                }
                for(Future future: futures){
                    try {
                        future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                futures.clear();
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
                    float alpha2 = 0.1f;

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
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_xm, rect_y, 0, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_y, rect_x, rect_ym, 0, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_xm, rect_y, rect_xm, rect_ym, 0, 1, 0, alpha2);
                                renderer.lineDrawer.draw(rect_x, rect_ym, rect_xm, rect_ym, 0, 1, 0, alpha2);
                            }
                        }
                    }
                }
            }
        }
    }

    public void dispose(){
        executor.shutdown();
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
