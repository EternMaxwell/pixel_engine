package spaceGameDemo;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.ManifoldPoint;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import spaceGameDemo.body.SpaceBody;

public class SpaceWorld {
    public World box2dWorld;

    public SpaceWorld() {
        box2dWorld = new World(new org.jbox2d.common.Vec2(0, 0));
        box2dWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {
                Object bodyA = contact.getFixtureA().getUserData();
                Object bodyB = contact.getFixtureB().getUserData();
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
    }
}
