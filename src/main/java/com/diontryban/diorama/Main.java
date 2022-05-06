package com.diontryban.diorama;

import com.diontryban.diorama.gfx.Shader;
import com.diontryban.diorama.gfx.Window;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Main {
    public static void main(String[] args) {
        Window.init(1280, 720, "Diorama");
        Window.clearColor(0.5f, 0.0f, 0.5f);

        String vertexShaderSource = """
                #version 330
                layout (location=0) in vec3 position;
                void main() {
                    gl_Position = vec4(position, 1.0);
                   }
                """;
        String fragmentShaderSource = """
                #version 330
                out vec4 fragColor;
                void main() {
                    fragColor = vec4(0.0, 0.5, 0.5, 1.0);
                }
                """;
        var shader = new Shader(vertexShaderSource, fragmentShaderSource);

        float[] vertices = new float[] {
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vaoId = GL41.glGenVertexArrays();
        GL41.glBindVertexArray(vaoId);

        int vboId = GL41.glGenBuffers();
        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, vboId);
        GL41.glBufferData(GL41.GL_ARRAY_BUFFER, verticesBuffer, GL41.GL_STATIC_DRAW);
        MemoryUtil.memFree(verticesBuffer);
        GL41.glVertexAttribPointer(0, 3, GL41.GL_FLOAT, false, 0, 0);

        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, 0);
        GL41.glBindVertexArray(0);

        while (!Window.shouldClose()) {
            shader.bind();
            GL41.glBindVertexArray(vaoId);
            GL41.glEnableVertexAttribArray(0);

            GL41.glDrawArrays(GL41.GL_TRIANGLES, 0, 3);

            GL41.glDisableVertexAttribArray(0);
            GL41.glBindVertexArray(0);

            Window.endFrame();
        }

        shader.dispose();
        GL41.glDisableVertexAttribArray(0);

        GL41.glBindBuffer(GL41.GL_ARRAY_BUFFER, 0);
        GL41.glDeleteBuffers(vboId);

        GL41.glBindVertexArray(0);
        GL41.glDeleteVertexArrays(vaoId);

        Window.dispose();
    }
}
