package com.maxwell_dev.pixel_engine.world.box2d.sample;

import com.maxwell_dev.pixel_engine.util.Util;
import com.maxwell_dev.pixel_engine.world.box2d.Body;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    public org.jbox2d.dynamics.Body createBox2dBody(World world, float x, float y, float angle, Collection<Vec2[]> trianglesRetrieve, float pixelSize) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.allowSleep = true;
        bodyDef.type = BodyType.DYNAMIC;
        float[][] outline = Util.mesh.marching_squares_outline_single(grid, pixelSize);
        Set<float[][]> holes = Util.mesh.holes_outline_single(grid, pixelSize);
        Set<float[][]> holes_simplified = new java.util.HashSet<>();
        for (float[][] hole : holes) {
            holes_simplified.add(Util.mesh.line_simplification(hole, pixelSize * .7f));
        }
        outline = Util.mesh.line_simplification(outline, pixelSize * .7f);
        List<float[][]> triangles = Util.mesh.ear_cut_triangulation(outline, holes_simplified.stream().toList());
        List<Vec2[]> trianglesInVec = new ArrayList<>();
        for (float[][] triangle : triangles) {
            Vec2[] singleTriangle = new Vec2[3];
            for (int i = 0; i < 3; i++) {
                singleTriangle[i] = new Vec2(triangle[i][0], triangle[i][1]);
            }
            trianglesInVec.add(singleTriangle);
            if(trianglesRetrieve != null)
                trianglesRetrieve.add(singleTriangle);
        }
        bodyDef.angle = angle;
        bodyDef.position.set(x, y);
        org.jbox2d.dynamics.Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        for(Vec2[] triangle: trianglesInVec){
            PolygonShape shape = new PolygonShape();
            shape.set(triangle, 3);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(this);
        }
        this.body = body;
        return body;
    }

    public T[][] getGrid() {
        return grid;
    }
}
