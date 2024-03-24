package com.maxwell_dev.pixel_engine.world.box2d.sample;

import com.maxwell_dev.pixel_engine.util.Util;
import com.maxwell_dev.pixel_engine.world.box2d.Body;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Collection;

public class GridBody<T extends Object> extends Body {
    private T[][] grid;
    private float density;
    private float friction;
    private float restitution;
    public GridBody(T[][] grid, float density, float friction, float restitution) {
        this.grid = grid;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
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
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        body.createFixture(fixtureDef);
        this.body = body;
        return body;
    }

    public T[][] getGrid() {
        return grid;
    }
}
