package spaceGameDemo.elements;

import com.maxwell_dev.pixel_engine.world.DirectState;
import com.maxwell_dev.pixel_engine.world.ElementBase;
import spaceGameDemo.SpaceWorld;
import spaceGameDemo.body.SpaceBody;

public abstract class BodyElement implements ElementBase, DirectState {

    private SpaceWorld spaceWorld;
    private final int x;
    private final int y;
    private SpaceBody body;

    private float[] color;
    private float temperature;
    private final float meltPoint;
    private final float heatCapacity;
    private float health;
    private final float maxHealth;

    public BodyElement(int x, int y, SpaceBody body, float[] color, float temperature, float meltPoint, float heatCapacity, float health) {
        this.x = x;
        this.y = y;
        this.body = body;
        this.color = color;
        this.temperature = temperature;
        this.meltPoint = meltPoint;
        this.heatCapacity = heatCapacity;
        this.health = health;
        this.maxHealth = health;
    }

    @Override
    public float[] color() {
        return color;
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
        body.createBox2dBody(spaceWorld.box2dWorld, null, 0.1f);
    }

    @Override
    public void heal(float heal) {
        health += heal;
        health = Math.min(health, maxHealth);
    }
}
