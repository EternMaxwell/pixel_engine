package spaceGameDemo.body;

import com.maxwell_dev.pixel_engine.util.Util;
import org.jbox2d.common.Vec2;
import spaceGameDemo.elements.BodyElement;
import spaceGameDemo.elements.ElementFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BodyFactory {
    public static Set<SpaceBody> randomAstroid(int size_expect, float x, float y, float angle, List<Vec2[]> triangleRetriever) {
        Set<SpaceBody> bodies = new HashSet<>();
        BodyElement[][] grid = new BodyElement[size_expect][size_expect];
        for (int i = 0; i < size_expect; i++) {
            for (int j = 0; j < size_expect; j++) {
                if (Math.random() < 0.5)
                    grid[i][j] = ElementFactory.stone(null, i, j);
            }
        }
        for (BodyElement[][] sub : Util.mesh.split(grid, BodyElement[][]::new, BodyElement[]::new)) {
            SpaceBody body = new SpaceBody(sub, x, y, angle, triangleRetriever == null ? null : new ArrayList<>());
            bodies.add(body);
        }
        return bodies;
    }

    public static SpaceBody square(int size, float x, float y, float angle, List<Vec2[]> triangleRetriever) {
        BodyElement[][] grid = new BodyElement[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ElementFactory.stone(null, i, j);
            }
        }
        return new SpaceBody(grid, x, y, angle, triangleRetriever == null ? null : new ArrayList<>());
    }
}
