package spaceGameDemo;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import spaceGameDemo.body.SpaceBody;

import java.util.HashSet;
import java.util.Set;

public class SpaceWorld {
    public World box2dWorld;
    public Set<SpaceBody> bodies;

    public SpaceWorld() {
        box2dWorld = new World(new org.jbox2d.common.Vec2(0, 0));
        box2dWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {
                SpaceBody bodyA = (SpaceBody) contact.getFixtureA().getUserData();
                SpaceBody bodyB = (SpaceBody) contact.getFixtureB().getUserData();
                WorldManifold worldManifold = new WorldManifold();
                contact.getWorldManifold(worldManifold);
                Vec2[] points = worldManifold.points;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        bodies = new HashSet<>();
    }

    public void addBody(SpaceBody body){
        bodies.add(body);
    }

    public void removeBody(SpaceBody body){
        bodies.remove(body);
    }

    public void input(InputTool inputTool){
    }

    public void update(){
        box2dWorld.step(1/60f, 8, 3);
    }

    public void render(Render renderer, Camera camera){
    }
}
