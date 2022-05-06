package com.diontryban.diorama.gfx;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public final class Window {
    private static long windowId;

    public static void init(int width, int height, String title) {
        System.out.println("LWJGL Version: " + Version.getVersion());

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        Window.windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (Window.windowId == 0) {
            throw new RuntimeException("Failed to create the GLFW window.");
        }

        GLFW.glfwMakeContextCurrent(Window.windowId);
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(Window.windowId);

        GL.createCapabilities();
        System.out.println("GL Version: " + GL11.glGetString(GL11.GL_VERSION));
    }

    public static void endFrame() {
        GLFW.glfwSwapBuffers(Window.windowId);
        GLFW.glfwPollEvents();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void clearColor(float red, float green, float blue) {
        GL11.glClearColor(red, green, blue, 0.0f);
    }

    public static boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(Window.windowId);
    }

    public static void dispose() {
        Callbacks.glfwFreeCallbacks(Window.windowId);
        GLFW.glfwDestroyWindow(Window.windowId);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
