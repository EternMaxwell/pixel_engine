package fallingsandsampletest;

import com.maxwell_dev.pixel_engine.core.InputTool;
import com.maxwell_dev.pixel_engine.render.Camera;
import com.maxwell_dev.pixel_engine.render.opengl.FrameBuffer;
import com.maxwell_dev.pixel_engine.render.opengl.Image;
import com.maxwell_dev.pixel_engine.render.opengl.Window;
import com.maxwell_dev.pixel_engine.stage.Stage;
import com.maxwell_dev.pixel_engine.world.falling_sand.sample.Element;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class FallingSandStage extends Stage<Render, InputTool> {

    Camera camera = new Camera();

    FallingGrid grid;

    Element<ElementID>[] elements = new Element[]{new Sand(), new Stone()};
    int index = 0;
    Image lightMap;
    FrameBuffer frameBuffer;
    ByteBuffer buffer;

    float orientation = 0;

    int texture;
    int texture2;

    float mouseX;
    float mouseY;
    @Override
    public void init() {
        grid = new FallingGrid();
        frameBuffer = new FrameBuffer(64, 64);
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTextureStorage2D(texture, 1, GL_RGBA8, 64, 64);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        texture2 = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture2);
        glTextureStorage2D(texture2, 1, GL_RGBA8, 64, 64);
        lightMap = new Image(texture);
        lightMap.samplerParameteri(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        lightMap.samplerParameteri(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        lightMap.samplerParameteri(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        lightMap.samplerParameteri(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        frameBuffer.attachColor(0, texture);
        frameBuffer.drawBuffer(0);
//        int depthRenderBuffer = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, 512, 512);
//        frameBuffer.renderBuffer(GL_DEPTH_ATTACHMENT, depthRenderBuffer);

        frameBuffer.check();

        camera.projectionOrtho(0, 64, 0, 64, -1, 1);

        buffer = MemoryUtil.memAlloc(64 * 64 * 4 * 4);
        for(int i = 0; i < 64 * 64; i++) {
            int x = i / 64;
            int y = i % 64;
            buffer.putFloat((x / 32f) * (x / 32f));
            buffer.putFloat((y / 32f) * (y / 32f));
        }
        for(int i = 0; i < 64 * 64; i++){
            buffer.putFloat((float) Math.random() * 0.02f + 0.2f);
        }
        for(int i = 0; i < 64 * 64; i++){
            int x = i % 64;
            int y = i / 64;
            if(x > 32 || y > 32){
                buffer.putInt(1);
                continue;
            }
            buffer.putInt(0);
        }
        buffer.flip();
    }

    @Override
    public void input(InputTool inputTool) {
        if (inputTool.isMousePressed(GLFW_MOUSE_BUTTON_1)){
            double x = inputTool.mouseX();
            double y = inputTool.mouseY();
            int gridX = (int) ((1 + x) * 32);
            int gridY = (int) ((1 + y) * 32);
            for(int i = -5; i <= 5; i++)
                for(int j = -5; j <= 5; j++)
                    grid.set(gridX + i, gridY + j, (Element<ElementID>) elements[index].newInstance());
        }
        if(inputTool.scrollY() > 0)
            index = (index + 1) % elements.length;
        if(inputTool.scrollY() < 0)
            index = (index - 1 + elements.length) % elements.length;
        mouseX = (float) inputTool.mouseX() / inputTool.window().ratio();
        mouseY = (float) inputTool.mouseY();
        if(inputTool.scrollX() > 0)
            orientation += 0.05f;
        if(inputTool.scrollX() < 0)
            orientation -= 0.05f;
        if(orientation < 0)
            orientation = 0;
        if(orientation > 1)
            orientation = 1;
        if(inputTool.isKeyPressed(GLFW_KEY_W)){
            camera.move(0, 0.1f);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_S)){
            camera.move(0, -0.1f);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_A)){
            camera.move(-0.1f, 0);
        }
        if(inputTool.isKeyPressed(GLFW_KEY_D)){
            camera.move(0.1f, 0);
        }
    }

    @Override
    public void update() {
        grid.step();
    }

    @Override
    public void render(Render render) {
        render.pixelLightDrawer.setNormalMap(buffer);

        render.pixelDrawer.setProjection(camera.cameraMatrix(new Matrix4f()));
        render.pixelDrawer.setView(new Matrix4f().identity());
        render.pixelDrawer.setModel(new Matrix4f().identity());
        render.pixelLightDrawer.setProjection(camera.cameraMatrix(new Matrix4f()));
        render.pixelLightDrawer.setView(new Matrix4f().identity());
        render.pixelLightDrawer.setModel(new Matrix4f().identity());

        Vector4f mouseInWorld = new Vector4f(mouseX, mouseY, 0, 1);
        camera.cameraMatrix(new Matrix4f()).invert().transform(mouseInWorld);
        float inX = mouseInWorld.x;
        float inY = mouseInWorld.y;

        frameBuffer.bind();
        render.pixelLightDrawer.drawLightMap(inX, inY,
                (float) (Math.PI * 5 / 4),orientation,1,1,1,1,1);
        frameBuffer.bindAsRead();
//        glCopyTextureSubImage2D(texture2, 0, 0, 0, 0, 0, 64, 64);

        frameBuffer.unbind((Window) render.window());
        render.imageDrawer.draw(lightMap);

        grid.render(render);
        frameBuffer.clear((Window) render.window());
    }

    @Override
    public void dispose() {
        frameBuffer.dispose();
    }
}
