package spaceGameDemo.elements;

import com.maxwell_dev.pixel_engine.world.DirectState;
import com.maxwell_dev.pixel_engine.world.ElementBase;
import org.jbox2d.dynamics.World;
import spaceGameDemo.SpaceWorld;
import spaceGameDemo.body.SpaceBody;

public class BodyElement implements ElementBase, DirectState {
    private final int x;
    private final int y;
    private SpaceBody body;

    private float[] color;
    private float temperature;
    private final float meltPoint;
    private final float heatCapacity;
    private float health;
    private final float maxHealth;

    private final float density;
    private final float friction;
    private final float restitution;

    public BodyElement(int x, int y, SpaceBody body, float[] color, float temperature, float meltPoint,
                       float heatCapacity, float health, float density, float friction, float restitution) {
        this.x = x;
        this.y = y;
        this.body = body;
        this.color = color;
        this.temperature = temperature;
        this.meltPoint = meltPoint;
        this.heatCapacity = heatCapacity;
        this.health = health;
        this.maxHealth = health;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    @Override
    public float[] color() {
        return color;
    }

    @Override
    public float density() {
        return density;
    }

    @Override
    public float friction() {
        return friction;
    }

    @Override
    public float restitution() {
        return restitution;
    }

    @Override
    public float health() {
        return health;
    }

    @Override
    public void damage(float damage) {
        health -= damage;
    }

    @Override
    public void heat(float heat) {
        temperature += heat / heatCapacity;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    private void destroy(){
        body.getGrid()[x][y] = null;
        body.removeBox2dBody();
        SpaceWorld box2dWorld = body.getWorld();
        body.createBox2dBody(box2dWorld, 0.1f);
    }

    @Override
    public void heal(float heal) {
        health += heal;
        health = Math.min(health, maxHealth);
    }
}
