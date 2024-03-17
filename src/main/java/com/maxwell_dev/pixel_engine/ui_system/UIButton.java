package com.maxwell_dev.pixel_engine.ui_system;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public abstract class UIButton<T extends Renderer, V extends InputTool> extends UIComponent<T, V> {

    public float x, y, width, height;

    public UIButton(UIManager<T, V> manager, float x, float y, float width, float height) {
        super(manager);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(V inputTool) {
        if(manager.isProjectionEnabled()){
            Matrix4f invProjectionMatrix = manager.invProjectionMatrix();
            Vector4f mousePos = new Vector4f((float) inputTool.mouseX(), (float) inputTool.mouseY(), 0, 1);
            invProjectionMatrix.transform(mousePos);
            return mousePos.x >= x && mousePos.x <= x + width && mousePos.y >= y && mousePos.y <= y + height;
        }else {
            return inputTool.mouseX() >= x && inputTool.mouseX() <= x + width && inputTool.mouseY() >= y && inputTool.mouseY() <= y + height;
        }
    }

    public boolean isClicked(V inputTool) {
        return isHovered(inputTool) && inputTool.isMouseJustPressed(GLFW_MOUSE_BUTTON_LEFT);
    }
}
