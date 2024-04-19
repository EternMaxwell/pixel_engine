package com.maxwell_dev.pixel_engine.core;

import com.maxwell_dev.pixel_engine.render.Window;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;

public class InputTool {

    private Window window;
    private static final int STATE_RELEASE = 0;
    private static final int STATE_PRESS = 1;
    private static final int STATE_JUST_RELEASED = 2;
    private static final int STATE_JUST_PRESSED = 3;

    private final Map<Integer, Integer> keyMap;
    private final Map<Integer, Integer> mouseMap;
    private final Set<Integer> keyJustPressed;
    private final Set<Integer> mouseJustPressed;
    private final double[] mousePos = new double[2];
    private final double[] mousePosLast = new double[2];
    private final double[] scroll = new double[2];
    private boolean scrollChanged = false;

    public InputTool(Window window) {
        keyMap = new HashMap<>();
        mouseMap = new HashMap<>();
        keyJustPressed = new HashSet<>();
        mouseJustPressed = new HashSet<>();
        this.window = window;
        glfwSetScrollCallback(window.id(), new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scroll[0] = xoffset;
                scroll[1] = yoffset;
                scrollChanged = true;
            }
        });
    }

    public void input(){
        if(scrollChanged){
            scrollChanged = false;
        }else {
            scroll[0] = 0;
            scroll[1] = 0;
        }
        keyJustPressed.clear();
        mouseJustPressed.clear();
        for(Map.Entry<Integer, Integer> entry : keyMap.entrySet()){
            int value = entry.getValue();
            if(glfwGetKey(window.id(), entry.getKey()) == GLFW_PRESS) {
                if (value == STATE_RELEASE || value == STATE_JUST_RELEASED) {
                    entry.setValue(STATE_JUST_PRESSED);
                    keyJustPressed.add(entry.getKey());
                }else
                    entry.setValue(STATE_PRESS);
            }else{
                if(value == STATE_PRESS || value == STATE_JUST_PRESSED)
                    entry.setValue(STATE_JUST_RELEASED);
                else
                    entry.setValue(STATE_RELEASE);
            }
        }
        for(Map.Entry<Integer, Integer> entry : mouseMap.entrySet()){
            int value = entry.getValue();
            if(glfwGetMouseButton(window.id(), entry.getKey()) == GLFW_PRESS) {
                if (value == STATE_RELEASE || value == STATE_JUST_RELEASED) {
                    entry.setValue(STATE_JUST_PRESSED);
                    mouseJustPressed.add(entry.getKey());
                } else
                    entry.setValue(STATE_PRESS);
            }else{
                if(value == STATE_PRESS || value == STATE_JUST_PRESSED)
                    entry.setValue(STATE_JUST_RELEASED);
                else
                    entry.setValue(STATE_RELEASE);
            }
        }
        double[] posX = new double[1];
        double[] posY = new double[1];
        glfwGetCursorPos(window.id(), posX, posY);
        mousePosLast[0] = mousePos[0];
        mousePosLast[1] = mousePos[1];
        mousePos[0] = posX[0] / window.width() * 2 - 1;
        mousePos[1] = 1 - posY[0] / window.height() * 2;
    }

    public boolean isKeyPressed(int key){
        keyMap.putIfAbsent(key, 0);
        return keyMap.get(key) == STATE_PRESS || keyMap.get(key) == STATE_JUST_PRESSED;
    }

    public boolean isKeyJustPressed(int key){
        keyMap.putIfAbsent(key, 0);
        return keyMap.get(key) == STATE_JUST_PRESSED;
    }

    public boolean isKeyReleased(int key){
        keyMap.putIfAbsent(key, 0);
        return keyMap.get(key) == STATE_RELEASE || keyMap.get(key) == STATE_JUST_RELEASED;
    }

    public boolean isKeyJustReleased(int key){
        keyMap.putIfAbsent(key, 0);
        return keyMap.get(key) == STATE_JUST_RELEASED;
    }

    public boolean isMousePressed(int key){
        mouseMap.putIfAbsent(key, 0);
        return mouseMap.get(key) == STATE_PRESS || mouseMap.get(key) == STATE_JUST_PRESSED;
    }

    public boolean isMouseJustPressed(int key){
        mouseMap.putIfAbsent(key, 0);
        return mouseMap.get(key) == STATE_JUST_PRESSED;
    }

    public boolean isMouseReleased(int key){
        mouseMap.putIfAbsent(key, 0);
        return mouseMap.get(key) == STATE_RELEASE || mouseMap.get(key) == STATE_JUST_RELEASED;
    }

    public boolean isMouseJustReleased(int key){
        mouseMap.putIfAbsent(key, 0);
        return mouseMap.get(key) == STATE_JUST_RELEASED;
    }

    public Set<Integer> keyJustPressed(){
        return keyJustPressed;
    }

    public Set<Integer> mouseJustPressed(){
        return mouseJustPressed;
    }

    public double[] mousePos(){
        return mousePos;
    }

    public double mouseX(){
        return mousePos[0];
    }

    public double mouseXRatio(){
        return mousePos[0] * window.ratio();
    }

    public double mouseY(){
        return mousePos[1];
    }

    public double[] mousePosLast(){
        return mousePosLast;
    }

    public double mouseXLast(){
        return mousePosLast[0];
    }

    public double mouseYLast(){
        return mousePosLast[1];
    }

    public double[] scroll(){
        return scroll;
    }

    public double scrollX(){
        return scroll[0];
    }

    public double scrollY(){
        return scroll[1];
    }

    public Window window() {
        return window;
    }
}
