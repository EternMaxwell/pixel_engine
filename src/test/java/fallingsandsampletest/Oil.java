package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.ElementType;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Grid;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Liquid;

public class Oil extends Liquid<ElementID> {

    private static float catchFireThreshold = 50;
    private static float heatTimeRatio = 30;
    private float life = 50;

    private float timeToFire = 0;

    public Oil(Grid<?, ?, ElementID> grid) {
        super(grid);
        color = new float[]{0.2f, 0.2f, 0.2f, 1f};
    }

    @Override
    public float[] color() {
        if (timeToFire <= 0)
            return color;
        return new float[]{1, 0.5f, 0, 1};
    }

    /**
     * @return the dispersion rate.
     */
    @Override
    public int dispersionRate() {
        return 3;
    }

    @Override
    public String name() {
        return "element:test:oil";
    }

    @Override
    public Element<Grid<?, ?, ElementID>, ElementType, ElementID> newInstance(Grid<?, ?, ElementID> grid) {
        return new Oil(grid);
    }

    @Override
    public ElementID id() {
        return ElementID.OIL;
    }

    @Override
    public boolean randomTick(Grid<?, ?, ElementID> grid, int x, int y, int tick, int intensity) {
        if (timeToFire > 0){
            timeToFire = Math.max(0, timeToFire - 1);
            life -= 1;
            if(life <= 0)
                grid.set(x, y, null);
            float heat = 0;
            if (intensity >= 5)
                heat += 20f;
            if (intensity >= 4) {
                Element above = grid.get(x, y + 1);
                if (above == null){
                    grid.set(x, y + 1, new Smoke(grid, 2500));
                }else{
                    Element first = grid.get(x - 1, y);
                    boolean left = true;
                    if (Math.random() > 0.5){
                        left = false;
                        first = grid.get(x + 1, y);
                    }
                    if (first == null){
                        grid.set(left ? x - 1 : x + 1, y, new Smoke(grid, 2500));
                    }else {
                        Element second = grid.get(left ? x + 1 : x - 1, y);
                        if (second == null) {
                            grid.set(left ? x + 1 : x - 1, y, new Smoke(grid, 2500));
                        }
                    }
                }
                heat += 10f;
            }
            if (intensity >= 3)
                heat += 20f;
            if (intensity >= 2)
                heat += 20f;
            if (intensity >= 1)
                heat += 60f;
            Element element = grid.get(x - 1, y);
            if (element != null)
                element.heat(grid, x - 1, y, heat);
            element = grid.get(x + 1, y);
            if (element != null)
                element.heat(grid, x + 1, y, heat);
            element = grid.get(x, y - 1);
            if (element != null)
                element.heat(grid, x, y - 1, heat);
            element = grid.get(x, y + 1);
            if (element != null)
                element.heat(grid, x, y + 1, heat);
        }
        return true;
    }

    @Override
    public float density() {
        return 0.8f;
    }

    @Override
    public float friction() {
        return 0.1f;
    }

    @Override
    public float restitution() {
        return 0.01f;
    }

    @Override
    public boolean heat(Grid<?, ?, ElementID> grid, int x, int y, float heat) {
        if(heat >= catchFireThreshold){
            timeToFire += heatTimeRatio * (heat - catchFireThreshold);
            return true;
        }
        return false;
    }

    @Override
    public boolean damage(Grid<?, ?, ElementID> grid, int x, int y, float damage) {
        return false;
    }
}
