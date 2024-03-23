package com.maxwell_dev.pixel_engine.world.box2d.sample;

import com.maxwell_dev.pixel_engine.util.Util;
import com.maxwell_dev.pixel_engine.world.box2d.Body;
import com.maxwell_dev.pixel_engine.world.falling_sand.Element;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.pooling.arrays.Vec2Array;

import java.util.Collection;

public class ElementBody<T extends Element> extends Body {
    private T[][] grid;
    public ElementBody(T[][] grid) {
        this.grid = grid;
    }

    @Override
    public org.jbox2d.dynamics.Body createBox2dBody(World world, float x, float y, float angle, Collection<Vec2> verticesRetriever, float pixelSize) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.allowSleep = true;
        bodyDef.type = BodyType.DYNAMIC;
        PolygonShape shape = new PolygonShape();
        float[][] outline = Util.mesh.marching_squares_outline_single(grid, pixelSize);
        outline = Util.mesh.line_simplification(outline, pixelSize);
        Vec2[] vertices = new Vec2[outline.length];
        for (int i = 0; i < outline.length; i++) {
            vertices[i] = new Vec2(outline[i][0], outline[i][1]);
        }
        if(verticesRetriever != null) verticesRetriever.addAll(java.util.Arrays.asList(vertices));
        shape.set(vertices, vertices.length);
        bodyDef.angle = angle;
        bodyDef.position.set(x, y);
        org.jbox2d.dynamics.Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = averageDensity();
        fixtureDef.friction = averageFriction();
        fixtureDef.restitution = averageRestitution();
        body.createFixture(fixtureDef);
        this.body = body;
        return body;
    }

    public T[][] getGrid() {
        return grid;
    }

    private float averageDensity() {
        float sum = 0;
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null) {
                    sum += grid[i][j].density();
                    count++;
                }
            }
        }
        return sum / count;
    }

    private float averageFriction() {
        float sum = 0;
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null && !Util.mesh.nearby_full_in_target_grid(grid, i, j)) {
                    sum += grid[i][j].friction();
                    count++;
                }
            }
        }
        return sum / count;
    }

    private float averageRestitution() {
        float sum = 0;
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != null && !Util.mesh.nearby_full_in_target_grid(grid, i, j)) {
                    sum += grid[i][j].restitution();
                    count++;
                }
            }
        }
        return sum / count;
    }
}
