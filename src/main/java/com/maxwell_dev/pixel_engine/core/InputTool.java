package com.maxwell_dev.pixel_engine.core;

import com.maxwell_dev.pixel_engine.render.Window;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;

public class InputTool {

    private Map<Integer, Integer> keyMap;
    private Map<Integer, Integer> keyMapLast;
    private Map<Integer, Integer> mouseMap;
    private Map<Integer, Integer> mouseMapLast;
    private final double[] mousePos = new double[2];
    private final double[] mousePosLast = new double[2];
    private final double[] scroll = new double[2];

    public InputTool(Window window) {
        keyMap = new HashMap<>();
        keyMapLast = new HashMap<>();
        mouseMap = new HashMap<>();
        mouseMapLast = new HashMap<>();
        glfwSetScrollCallback(window.id(), new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scroll[0] = xoffset;
                scroll[1] = yoffset;
            }
        });
    }

    public void input(Window window){
        Map<Integer, Integer> temp = keyMapLast;
        keyMapLast = keyMap;
        keyMap = temp;
        temp = mouseMapLast;
        mouseMapLast = mouseMap;
        mouseMap = temp;
        for(Map.Entry<Integer, Integer> entry : keyMap.entrySet()){
            entry.setValue(glfwGetKey(window.id(), entry.getKey()));
        }
        for(Map.Entry<Integer, Integer> entry : mouseMap.entrySet()){
            entry.setValue(glfwGetMouseButton(window.id(), entry.getKey()));
        }
        double[] posX = new double[1];
        double[] posY = new double[1];
        glfwGetCursorPos(window.id(), posX, posY);
        mousePosLast[0] = mousePos[0];
        mousePosLast[1] = mousePos[1];
        mousePos[0] = (posX[0] / window.width() * 2 - 1) * window.ratio();
        mousePos[1] = 1 - posY[0] / window.height() * 2;
    }

    public boolean isKeyPressed(int key){
        keyMap.putIfAbsent(key, 0);
        keyMapLast.putIfAbsent(key, 0);
        return keyMap.get(key) == GLFW_PRESS;
    }

    public boolean isKeyJustPressed(int key){
        keyMap.putIfAbsent(key, 0);
        keyMapLast.putIfAbsent(key, 0);
        return keyMap.get(key) == GLFW_PRESS && keyMapLast.get(key) == GLFW_RELEASE;
    }

    public boolean isKeyReleased(int key){
        keyMap.putIfAbsent(key, 0);
        keyMapLast.putIfAbsent(key, 0);
        return keyMap.get(key) == GLFW_RELEASE;
    }

    public boolean isKeyJustReleased(int key){
        keyMap.putIfAbsent(key, 0);
        keyMapLast.putIfAbsent(key, 0);
        return keyMap.get(key) == GLFW_RELEASE && keyMapLast.get(key) == GLFW_PRESS;
    }

    public boolean isMousePressed(int key){
        mouseMap.putIfAbsent(key, 0);
        mouseMapLast.putIfAbsent(key, 0);
        return mouseMap.get(key) == GLFW_PRESS;
    }

    public boolean isMouseJustPressed(int key){
        mouseMap.putIfAbsent(key, 0);
        mouseMapLast.putIfAbsent(key, 0);
        return mouseMap.get(key) == GLFW_PRESS && mouseMapLast.get(key) == GLFW_RELEASE;
    }

    public boolean isMouseReleased(int key){
        mouseMap.putIfAbsent(key, 0);
        mouseMapLast.putIfAbsent(key, 0);
        return mouseMap.get(key) == GLFW_RELEASE;
    }

    public boolean isMouseJustReleased(int key){
        mouseMap.putIfAbsent(key, 0);
        mouseMapLast.putIfAbsent(key, 0);
        return mouseMap.get(key) == GLFW_RELEASE && mouseMapLast.get(key) == GLFW_PRESS;
    }

    public double[] mousePos(){
        return mousePos;
    }

    public double mouseX(){
        return mousePos[0];
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
}
