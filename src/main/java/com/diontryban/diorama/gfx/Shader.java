package com.diontryban.diorama.gfx;

import org.lwjgl.opengl.GL20;

public class Shader {
    private final int programId;

    public Shader(String vertexShaderSourceCode, String fragmentShaderSourceCode) throws RuntimeException {
        this.programId = GL20.glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Failed to create Shader.");
        }

        int vertexShaderId = this.createShader(vertexShaderSourceCode, GL20.GL_VERTEX_SHADER);
        int fragmentShaderId = this.createShader(fragmentShaderSourceCode, GL20.GL_FRAGMENT_SHADER);

        GL20.glLinkProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, GL20.GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shader code: " + GL20.glGetProgramInfoLog(this.programId));
        }

        if (vertexShaderId != 0) {
            GL20.glDetachShader(this.programId, vertexShaderId);
            GL20.glDeleteShader(vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            GL20.glDetachShader(this.programId, fragmentShaderId);
            GL20.glDeleteShader(fragmentShaderId);
        }

        GL20.glValidateProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(this.programId));
        }
    }

    private int createShader(String shaderSourceCode, int shaderType) throws RuntimeException {
        int shaderId = GL20.glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new RuntimeException("Failed to create shader. Type: " + shaderType);
        }

        GL20.glShaderSource(shaderId, shaderSourceCode);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId));
        }

        GL20.glAttachShader(this.programId, shaderId);

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(this.programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void dispose() {
        unbind();
        if (this.programId != 0) {
            GL20.glDeleteProgram(this.programId);
        }
    }
}
