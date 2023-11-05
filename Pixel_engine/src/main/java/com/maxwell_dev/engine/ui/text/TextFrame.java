package com.maxwell_dev.engine.ui.text;

import com.maxwell_dev.engine.render.Visible;

import java.util.LinkedList;
import java.util.List;

public class TextFrame{
    public static class Vector{
        public Text text;
        public float x;
        public float y;

        public void setPos(float x, float y){
            this.x = x;
            this.y = y;
        }
    }

    private final List<Vector> vectors;

    public TextFrame(){
        vectors = new LinkedList<>();
    }

    public void addText(Text text, float x, float y){
        Vector vector = new Vector();
        vector.text = text;
        vector.x = x;
        vector.y = y;
        vectors.add(vector);
    }

    public void removeText(Text text){
        for(Vector vector : vectors){
            if(vector.text == text){
                vectors.remove(vector);
                return;
            }
        }
    }

    public void removeText(int index){
        vectors.remove(index);
    }

    public void clear(){
        vectors.clear();
    }

    public List<Vector> getVectors(){
        return vectors;
    }
}
