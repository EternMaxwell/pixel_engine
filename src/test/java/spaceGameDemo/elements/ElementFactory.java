package spaceGameDemo.elements;

import spaceGameDemo.body.SpaceBody;

public class ElementFactory {
    public static BodyElement steel(SpaceBody body, int x, int y){
        return new BodyElement(x, y, body, new float[]{0.5f, 0.5f, 0.5f, 1.0f}, 273, 1000, 100, 100, 8, 0.5f, 0.1f);
    }

    public static BodyElement stone(SpaceBody body, int x, int y){
        return new BodyElement(x, y, body, new float[]{0.5f, 0.5f, 0.3f, 1.0f}, 273, 2500, 200, 100, 5, 0.7f, 0.1f);
    }
}
