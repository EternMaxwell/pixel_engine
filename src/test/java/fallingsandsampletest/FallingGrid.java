package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.ElementType;

public class FallingGrid extends com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid<Render, Actions, ElementID>{

    Chunk[][] chunks;
    int tick = 0;
    boolean inverse = false;
    int resetTag = 0;
    int resetThreshold = 30;

    public class Chunk{
        Element<ElementID>[][] elements;
        int x;
        int y;

        int rect_x;
        int rect_y;
        int rect_xm;
        int rect_ym;

        int rect_x_next;
        int rect_y_next;
        int rect_xm_next;
        int rect_ym_next;

        public Chunk(int x, int y){
            this.x = x;
            this.y = y;
            elements = new Element[64][64];
            rect_x = 64;
            rect_y = 64;
            rect_xm = 0;
            rect_ym = 0;
            rect_x_next = 64;
            rect_y_next = 64;
            rect_xm_next = 0;
            rect_ym_next = 0;
        }

        public void set(int x, int y, Element<ElementID> element){
            elements[x][y] = element;
        }

        public void awake(int x, int y){
            if(x < rect_x_next){
                rect_x_next = x;
            }
            if(y < rect_y_next){
                rect_y_next = y;
            }
            if(x > rect_xm_next){
                rect_xm_next = x;
            }
            if(y > rect_ym_next){
                rect_ym_next = y;
            }
            if(x < rect_x){
                rect_x = x;
            }
            if(y < rect_y){
                rect_y = y;
            }
            if(x > rect_xm){
                rect_xm = x;
            }
            if(y > rect_ym){
                rect_ym = y;
            }
        }

        public boolean in_rect(int x, int y){
//            return true;
            return x >= rect_x && x <= rect_xm && y >= rect_y && y <= rect_ym;
        }

        public Element<ElementID> get(int x, int y){
            return elements[x][y];
        }

        public void reset(){
            rect_x = rect_x_next;
            rect_y = rect_y_next;
            rect_xm = rect_xm_next;
            rect_ym = rect_ym_next;
            rect_x_next = 64;
            rect_y_next = 64;
            rect_xm_next = 0;
            rect_ym_next = 0;
        }
    }

    public FallingGrid() {
        gravity_x = 0;
        gravity_y = -100f;
        pixelSize = 1;
        chunks = new Chunk[8][8];
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
        Chunk chunk = chunks[x / 64][y / 64];
        if(chunk == null){
            return null;
        }
        return chunk.get(x % 64, y % 64);
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
        Chunk chunk = chunks[x / 64][y / 64];
        if(chunk == null){
            chunk = new Chunk(x / 64, y / 64);
            chunks[x / 64][y / 64] = chunk;
        }
        chunk.awake(x % 64, y % 64);
    }

    @Override
    public void set(int x, int y, Element<ElementID> element) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return;
        }
        Chunk chunk = chunks[x / 64][y / 64];
        if(chunk == null){
            chunk = new Chunk(x / 64, y / 64);
            chunks[x / 64][y / 64] = chunk;
        }
        chunk.set(x % 64, y % 64, element);

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
        Chunk chunk = chunks[x / 64][y / 64];
        if(chunk == null){
            return;
        }
        chunk.set(x % 64, y % 64, null);
    }

    @Override
    public Element<ElementID> popElementAt(int x, int y) {
        if(x < 0 || y < 0 || x >= chunks.length * 64 || y >= chunks[0].length * 64){
            return null;
        }
        Chunk chunk = chunks[x / 64][y / 64];
        if(chunk == null){
            return null;
        }
        Element<ElementID> element = chunk.get(x % 64, y % 64);
        chunk.set(x % 64, y % 64, null);
        return element;
    }

    @Override
    public double step() {
        long start = System.nanoTime();
        if(resetTag++ > resetThreshold){
            resetTag = 0;
            for (Chunk[] row : chunks) {
                for (Chunk chunk : row) {
                    chunk.reset();
                }
            }
        }
        for (int y = 0; y < chunks[0].length * 64; y++) {
            if(inverse){
                for (int x = 0; x < chunks.length * 64; x++) {
                    Chunk chunk = chunks[x / 64][y / 64];
                    Element<ElementID> element = get(x, y);
                    if (element != null && chunk.in_rect(x % 64, y % 64)) {
                        element.step(this, x, y, tick);
                    }
                }
            }else {
                for (int x = chunks.length * 64 - 1; x >= 0; x--) {
                    Chunk chunk = chunks[x / 64][y / 64];
                    Element<ElementID> element = get(x, y);
                    if (element != null && chunk.in_rect(x % 64, y % 64)) {
                        element.step(this, x, y, tick);
                    }
                }
            }
        }
        inverse = !inverse;
        tick++;
        return (System.nanoTime() - start) / 1e6;
    }

    @Override
    public void render(Render renderer) {
        for (int x = 0; x < chunks.length * 64; x++) {
            for (int y = 0; y < chunks[0].length * 64; y++) {
                Element<ElementID> element = get(x, y);
                if (element != null) {
                    float[] color = element.color().clone();
                    if(!element.freeFall()){
                        color[0] *= 0.5F;
                        color[1] *= 0.5F;
                        color[2] *= 0.5F;
                    }
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
