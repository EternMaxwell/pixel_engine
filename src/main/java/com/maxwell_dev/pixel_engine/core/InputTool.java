package com.maxwell_dev.pixel_engine.core;

import com.maxwell_dev.pixel_engine.render.Window;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputTool {

    private Map<Integer, Integer> keyMap;
    private Map<Integer, Integer> keyMapLast;
    private Map<Integer, Integer> mouseMap;
    private Map<Integer, Integer> mouseMapLast;

    public InputTool() {
        keyMap = new HashMap<>();
        keyMapLast = new HashMap<>();
        mouseMap = new HashMap<>();
        mouseMapLast = new HashMap<>();
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
}
