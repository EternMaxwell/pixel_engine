package com.maxwell_dev.engine.ui.text;

import com.maxwell_dev.engine.render.DrawPipeline;
import com.maxwell_dev.engine.render.Pipeline;
import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Program;
import com.maxwell_dev.globj.Shader;
import com.maxwell_dev.globj.VertexArray;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class TextFrame{

    public final RenderStream renderStream;
    private final DrawPipeline pipeline;


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

    public TextFrame(Context context){
        vectors = new LinkedList<>();
        Program program = new Program();
        Shader vertexShader = new Shader(GL_VERTEX_SHADER, "shaders/textShaders/text.vert");
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER, "shaders/textShaders/text.frag");
        program.vertexShader().attach(vertexShader);
        program.fragmentShader().attach(fragmentShader);
        program.linkProgram();
        pipeline = new DrawPipeline(context);
        pipeline.program(program);
        pipeline.vertexArray(new VertexArray());

        renderStream = new RenderStream(pipeline, 1024, 1024, 1024);
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
