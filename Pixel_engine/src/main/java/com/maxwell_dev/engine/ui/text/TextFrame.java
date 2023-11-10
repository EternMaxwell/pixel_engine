package com.maxwell_dev.engine.ui.text;

import com.maxwell_dev.engine.render.DrawPipeline;
import com.maxwell_dev.engine.render.RenderStream;
import com.maxwell_dev.engine.render.Visible;
import com.maxwell_dev.globj.Buffer;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.Program;
import com.maxwell_dev.globj.Shader;
import com.maxwell_dev.globj.VertexArray;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;

public class TextFrame{
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
    private final List<RenderStream> renderStreams;

    public TextFrame(Context context){
        vectors = new LinkedList<>();
        Program program = new Program();
        Shader vertexShader = new Shader(GL_VERTEX_SHADER, "Pixel_engine/src/main/resources/shaders/textShaders/text.vert");
        Shader fragmentShader = new Shader(GL_FRAGMENT_SHADER, "Pixel_engine/src/main/resources/shaders/textShaders/text.frag");
        program.vertexShader().attach(vertexShader);
        program.fragmentShader().attach(fragmentShader);
        program.linkProgram();
        pipeline = new DrawPipeline(context);
        pipeline.program(program);
        pipeline.vertexArray(new VertexArray());
        pipeline.vertexStride(8 * Float.BYTES);

        glBindVertexArray(pipeline.vertexArray().id());
        Buffer temp = new Buffer();
        glBindBuffer(GL_ARRAY_BUFFER, temp.id());

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 8);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 16);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        temp.delete();

        renderStreams = new LinkedList<>();
    }

    public void addText(Text text, float x, float y){
    }

    public void removeText(Text text) {
        for (int i = 0; i < vectors.size(); i++) {
            if (vectors.get(i).text == text) {
                vectors.remove(i);
                renderStreams.remove(i);
                break;
            }
        }
    }

    public void removeText(int index){
        vectors.remove(index);
        renderStreams.remove(index);
    }

    public void clear(){
        vectors.clear();
        renderStreams.clear();
    }

    public List<RenderStream> getStreams(){
        return renderStreams;
    }

    public List<Vector> getVectors(){
        return vectors;
    }
}
