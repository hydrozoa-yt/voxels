package com.hydrozoa.voxels.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int geometryShaderID;
	private int fragmentShaderID;
	
	private FloatBuffer matrixBuffer;
	
	/**
	 * You MUST call #loadShaders on the subclasses constructor!
	 */
	public ShaderProgram() {}
	
	public ShaderProgram(String vertexShader, String fragmentShader) {
		loadShaders(vertexShader, fragmentShader);
	}
	
	public ShaderProgram(String vertexShader, String fragmentShader, String geometryShader) {
		loadShaders(vertexShader, fragmentShader, geometryShader);
	}
	
	protected void loadShaders(String vertexShader, String fragmentShader) {
		matrixBuffer = BufferUtils.newFloatBuffer(16);
		
		vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected void loadShaders(String vertexShader, String fragmentShader, String geometryShader) {
		matrixBuffer = BufferUtils.newFloatBuffer(16);
		
		vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		geometryShaderID = loadShader(geometryShader, GL32.GL_GEOMETRY_SHADER);
		fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, geometryShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String variableName) {
		return GL20.glGetUniformLocation(programID, variableName);
	}
	
	public void begin() {
		GL20.glUseProgram(programID);
	}
	
	public void end() {
		GL20.glUseProgram(0);
	}
	
	public void dispose() {
		end();
		if(geometryShaderID==-1){
			GL20.glDetachShader(programID, vertexShaderID);
			GL20.glDetachShader(programID, fragmentShaderID);
			GL20.glDeleteShader(vertexShaderID);
			GL20.glDeleteShader(fragmentShaderID);
			GL20.glDeleteProgram(programID);
		} else {
			GL20.glDetachShader(programID, vertexShaderID);
			GL20.glDetachShader(programID, geometryShaderID);
			GL20.glDetachShader(programID, fragmentShaderID);
			GL20.glDeleteShader(vertexShaderID);
			GL20.glDeleteShader(geometryShaderID);
			GL20.glDeleteShader(fragmentShaderID);
			GL20.glDeleteProgram(programID);
		}
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttributes(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3 vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		float toLoad = 0f;
		if (value) {
			toLoad=1f;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4 value) {
		matrixBuffer.put(value.val);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	private static int loadShader(String shader, int type) {
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shader);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
			System.err.println(GL20.glGetShaderInfoLog(shaderID, 512));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}
	
}
