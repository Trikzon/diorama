package com.diontryban.diorama;

import com.diontryban.diorama.gfx.Window;

public class Main {
    public static void main(String[] args) {
        Window.init(1280, 720, "Diorama");
        Window.clearColor(0.5f, 0.0f, 0.5f);

        while (!Window.shouldClose()) {

            Window.endFrame();
        }
    }
}
