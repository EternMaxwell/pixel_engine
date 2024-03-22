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
    public org.jbox2d.dynamics.Body createBox2dBody(World world, float x, float y, float angle, Collection<Vec2> verticesRetriever) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.allowSleep = true;
        bodyDef.type = BodyType.DYNAMIC;
        PolygonShape shape = new PolygonShape();
        float[][] outline = Util.mesh.marching_squares_outline_single(grid, 0.1f);
        outline = Util.mesh.line_simplification(outline, 0.08f);
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
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        this.body = body;
        return body;
    }
}
