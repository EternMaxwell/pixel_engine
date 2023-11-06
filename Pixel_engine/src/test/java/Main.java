import com.maxwell_dev.engine.Application;
import com.maxwell_dev.engine.FrameTimer;
import com.maxwell_dev.engine.TickTimer;
import com.maxwell_dev.engine.render.Renderer;
import com.maxwell_dev.engine.render.Window;
import com.maxwell_dev.engine.ui.text.Font;
import com.maxwell_dev.engine.ui.text.Text;
import com.maxwell_dev.engine.ui.text.TextFrame;
import com.maxwell_dev.globj.Context;
import com.maxwell_dev.globj.ViewPort;

import org.lwjgl.glfw.GLFWWindowRefreshCallback;

import javax.imageio.ImageIO;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;

public class Main extends Application {
    private Window window;
    private Context context;
    private Renderer renderer;

    private FrameTimer timer;
    private TickTimer tickTimer;

    private TextFrame textFrame;

    public Main(String name) {
        super(name);
    }

    @Override
    public void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }else {
            System.out.println("GLFW initialized");
        }

        timer = new FrameTimer(60);
        tickTimer = new TickTimer(20);

        window = new Window("test",400, 225, "test");
        window.setContextVersionMajor(4);
        window.setContextVersionMinor(6);
        window.setVisible(false);
        window.setFocused(true);
        window.createWindow();
        window.setSizeLimit(800,450,-1,-1);

        glfwMakeContextCurrent(window.id());
        context = new Context();
        glfwSwapBuffers(window.id());

        window.setWindowRefreshCallback(new GLFWWindowRefreshCallback() {
            @Override
            public void invoke(long window) {
                if(window == Main.this.window.id()){
                    timer.frame();
                    update();
                    render();
                }
            }
        });
        window.showWindow();

        renderer = new Renderer("renderer", window, context);

        textFrame = new TextFrame(context);
        Text text = new Text(new Font(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16)), "Hello World").setHeight(0.01f);

        try {
            ImageIO.write(text.bitmap(), "png", new java.io.File("Pixel_engine/src/test/output/test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textFrame.addText(text, 0, 0);

        renderer.addStream(textFrame.getStreams());
    }

    @Override
    public void update() {
        if(tickTimer.tick()){
            System.out.println("tick");
            //tick update
        }
    }

    @Override
    public void input() {
        glfwPollEvents();
    }

    @Override
    public void render() {
        System.out.println("render");
        context.viewPort().set(new ViewPort().set(0, 0, window.width(), window.height()));
        //render actual data
        renderer.executeStreams();
        renderer.endFrame();
    }

    @Override
    public void destroy() {
        window.dispose();
        glfwTerminate();
    }

    @Override
    public void run() {
        init();
        while (!glfwWindowShouldClose(window.id())) {
            input();
            update();
            timer.frame();
            render();
        }
        destroy();
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public static void main(String[] args) {
        Main main = new Main("test");
        main.run();
    }
}
