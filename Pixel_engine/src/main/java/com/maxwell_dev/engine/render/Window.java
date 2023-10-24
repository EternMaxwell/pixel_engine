package com.maxwell_dev.engine.render;

import org.lwjgl.glfw.*;

import com.maxwell_dev.engine.Node;
import com.maxwell_dev.engine.NodeType;

import static org.lwjgl.glfw.GLFW.*;

public class Window extends Node{
    private long window;
    private int width;
    private int height;
    private String title;
    //all the possible window hint name in glfw.Not in array.Not final.
    private int resizable, visible, decorated, focused, auto_iconify, floating,
            maximized, center_cursor, transparent_framebuffer, focus_on_show,
            scale_to_monitor, red_bits, green_bits, blue_bits, alpha_bits, depth_bits,
            stencil_bits, accum_red_bits, accum_green_bits, accum_blue_bits,
            accum_alpha_bits, aux_buffers, samples, refresh_rate, stereo, srgb_capable,
            doublebuffer, client_api, context_version_major, context_version_minor,
            context_revision, context_robustness, opengl_forward_compat, opengl_debug_context,
            opengl_profile, context_release_behavior, context_no_error, context_creation_api,
            cocoa_retina_framebuffer, cocoa_frame_name, cocoa_graphics_switching,
            x11_class_name, x11_instance_name, no_api, opengl_api, opengl_es_api,
            no_robustness, no_reset_notification, lose_context_on_reset, opengl_any_profile,
            opengl_core_profile, opengl_compat_profile, cursor, sticky_keys, sticky_mouse_buttons,
            lock_key_mods, raw_mouse_motion, cursor_normal, cursor_hidden, cursor_disabled,
            any_release_behavior, release_behavior_flush, release_behavior_none,
            native_context_api, egl_context_api, osmesa_context_api, arrow_cursor,
            ibeam_cursor, crosshair_cursor, hand_cursor, hresize_cursor, vresize_cursor,
            version_major, version_minor, version_revision;

    public void createWindow() {
        glfwDefaultWindowHints();
        window = glfwCreateWindow(width, height, title, 0, 0);
        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            if(window != this.window)
                return;
            this.width = width;
            this.height = height;
        });
    }

    public Window(String name, int width, int height, String title) {
        super(NodeType.WINDOW, name);
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /**
     * set the window position
     * @param xpos the x position of the window
     * @param ypos the y position of the window
     */
    public void setPosition(int xpos, int ypos) {
        glfwSetWindowPos(window, xpos, ypos);
    }

    /**
     * set the window size
     * @param width the width of the window
     * @param height the height of the window
     */
    public void setSize(int width, int height) {
        glfwSetWindowSize(window, width, height);
    }

    /**
     * set the window title
     * @param title the title of the window
     */
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
    }

    /**
     * set the window monitor
     * @param monitor the monitor to set
     * @param xpos the x position of the window
     * @param ypos the y position of the window
     * @param width the width of the window
     * @param height the height of the window
     * @param refreshRate the refresh rate of the window
     */
    public void setMonitor(long monitor, int xpos, int ypos, int width, int height, int refreshRate) {
        glfwSetWindowMonitor(window, monitor, xpos, ypos, width, height, refreshRate);
    }

    /**
     * get the id of the window
     * @return the id of the window
     */
    public long id(){
        return window;
    }

    /**
     * get the width of the window
     * @return the width of the window
     */
    public int width(){
        return width;
    }

    /**
     * get the height of the window
     * @return the height of the window
     */
    public int height() {
        return height;
    }
    
    /**
     * get the title of the window
     * @return the title of the window
     */
    public String title() {
        return title;
    }
}
