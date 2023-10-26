import com.maxwell_dev.engine.Application;
import com.maxwell_dev.engine.render.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Application {
    private Window window;

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

        window = new Window("test",800, 600, "test");
        window.createWindow();
    }

    @Override
    public void update() {

    }

    @Override
    public void input() {
        glfwPollEvents();
    }

    @Override
    public void render() {
        glfwSwapBuffers(window.id());
    }

    @Override
    public void destroy() {
        glfwTerminate();
    }

    @Override
    public void run() {
        init();
        while (!glfwWindowShouldClose(window.id())) {
            input();
            update();
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
