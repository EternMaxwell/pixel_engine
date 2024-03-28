package spaceGameDemo.body;

import com.maxwell_dev.pixel_engine.util.Util;
import com.maxwell_dev.pixel_engine.world.box2d.sample.ElementBody;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Matrix2f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import spaceGameDemo.Render;
import spaceGameDemo.SpaceWorld;
import spaceGameDemo.elements.BodyElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SpaceBody extends ElementBody<BodyElement> {

    public float x;
    public float y;
    public float angle;
    private SpaceWorld world;
    private boolean shouldReset = false;
    private float pixelSize;
    private List<Vec2[]> verticesRetriever;

    public SpaceBody(BodyElement[][] grid, float x, float y, float angle, List<Vec2[]> verticesRetriever) {
        super(grid);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.verticesRetriever = verticesRetriever;
    }

    public Body createBox2dBody(SpaceWorld world, float pixelSize){
        super.createBox2dBody(world.box2dWorld, x, y, angle, verticesRetriever, pixelSize);
        world.addBody(this);
        this.world = world;
        this.pixelSize = pixelSize;
        return body;
    }

    public Matrix4f getView(){
        Vec2 center = body.getWorldCenter();
        float viewX = center.x;
        float viewY = center.y;
        angle = body.getAngle();
        return new Matrix4f().translate(viewX, viewY, 0).rotateZ(angle);
    }

    public SpaceWorld getWorld() {
        return world;
    }

    public void setElement(int x, int y, BodyElement element){
        getGrid()[x][y] = element;
        shouldReset = true;
    }

    public void reset(){
        if(shouldReset){
            removeBox2dBody();
            Set<BodyElement[][]> splits = Util.mesh.split(getGrid(), BodyElement[][]::new, BodyElement[]::new);
            if(splits.size() == 1)
                createBox2dBody(world, 1);
            else {
                for (BodyElement[][] split : splits) {
                    SpaceBody body = new SpaceBody(split, x, y, angle, new ArrayList<>());
                    body.createBox2dBody(world, pixelSize);
                }
            }
        }
        shouldReset = false;
    }

    public List<Vec2[]> getLocalVertices() {
        return verticesRetriever;
    }

    @Override
    public void removeBox2dBody() {
        angle = body.getAngle();
        x = body.getPosition().x;
        y = body.getPosition().y;
        super.removeBox2dBody();
    }

    public void render(Render render){
        x = body.getPosition().x;
        y = body.getPosition().y;
        angle = body.getAngle();
        Matrix4f transform = new Matrix4f().translate(x, y, 0).rotateZ(angle);
        render.pixelDrawer.setModel(transform);
        render.lineDrawer.setModel(transform);
        for(int i = 0; i < getGrid().length; i++){
            for(int j = 0; j < getGrid()[i].length; j++){
                BodyElement element = getGrid()[i][j];
                if(element != null){
                    Vector4f pos = new Vector4f(i*pixelSize, j*pixelSize, 0, 1);
                    float[] color = element.color();
                    render.pixelDrawer.draw(pos.x + pixelSize/2, pos.y + pixelSize/2, pixelSize, color[0], color[1], color[2], color[3]);
                }
            }
        }
        render.pixelDrawer.flush();
        for(Vec2[] v: getLocalVertices()){
            if (v != null && v.length > 0) {
                for (int i = 0; i < v.length; i++) {
                    Vec2 v1 = v[i];
                    Vec2 v2 = v[(i + 1) % v.length];
                    render.lineDrawer.draw(v1.x, v1.y, v2.x, v2.y, 1, 1, 1, 1);
                }
            }
        }
        render.lineDrawer.flush();
    }

    public float[] getPosition() {
        Vec2 center = body.getWorldCenter();
        return new float[]{center.x, center.y};
    }

    public float getCenterX() {
        return body.getWorldCenter().x;
    }

    public float getCenterY() {
        return body.getWorldCenter().y;
    }

    public float getAngle() {
        return body.getAngle();
    }
}
