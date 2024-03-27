package spaceGameDemo.body;

import com.maxwell_dev.pixel_engine.world.box2d.sample.ElementBody;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Matrix4f;
import spaceGameDemo.elements.BodyElement;

import java.util.Collection;

public class SpaceBody extends ElementBody<BodyElement> {

    public float x;
    public float y;
    public float angle;

    public SpaceBody(BodyElement[][] grid, float x, float y, float angle) {
        super(grid);
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Body createBox2dBody(World world, Collection<Vec2> verticesRetriever, float pixelSize){
        super.createBox2dBody(world, x, y, angle, verticesRetriever, pixelSize);
        return body;
    }

    public Matrix4f getView(){
        Vec2 center = body.getWorldCenter();
        float viewX = center.x;
        float viewY = center.y;
        angle = body.getAngle();
        return new Matrix4f().translate(viewX, viewY, 0).rotateZ(angle);
    }

    @Override
    public void removeBox2dBody() {
        angle = body.getAngle();
        x = body.getPosition().x;
        y = body.getPosition().y;
        super.removeBox2dBody();
    }
}
