package com.hydrozoa.voxels.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.badlogic.gdx.utils.BufferUtils;

public class TexturedMesh {
	
	private int vaoID;
	
	private int positionVboID;
	private int uvCoordsVboID;
	
	private int vertexCount;
	
	public TexturedMesh(float[] positions, float[] uvCoords) {
		genModel(positions, uvCoords);
	}
	
	public TexturedMesh(FloatBuffer positions, float[] uvCoords) {
		genModel(positions, uvCoords);
	}
	
	public TexturedMesh(FloatBuffer positions, FloatBuffer uvCoords) {
		genModel(positions, uvCoords);
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	/**
	 * @param positions Remember to #flip() so it is in read-mode
	 * @param uvCoords 
	 */
	private void genModel(FloatBuffer positions, FloatBuffer uvCoords) {
		this.vertexCount = positions.capacity()/3;
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		/* put positions into attribute list */
		positionVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positions, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		/* put uvCoords into attribute list */
		uvCoordsVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvCoords, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * @param positions Remember to #flip() so it is in read-mode
	 * @param uvCoords 
	 */
	private void genModel(FloatBuffer positions, float[] uvCoords) {
		this.vertexCount = positions.capacity()/3;
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		/* put positions into attribute list */
		positionVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positions, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		/* put uvCoords into attribute list */
		uvCoordsVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsVboID);
		FloatBuffer uvBuffer = BufferUtils.newFloatBuffer(uvCoords.length);
		uvBuffer.put(uvCoords);
		uvBuffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		GL30.glBindVertexArray(0);
	}
	
	private void genModel(float[] positions, float[] uvCoords) {
		this.vertexCount = positions.length/3;
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		/* put positions into attribute list */
		positionVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboID);
		FloatBuffer buffer = BufferUtils.newFloatBuffer(positions.length);
		buffer.put(positions);
		buffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		/* put uvCoords into attribute list */
		uvCoordsVboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvCoordsVboID);
		FloatBuffer uvBuffer = BufferUtils.newFloatBuffer(uvCoords.length);
		uvBuffer.put(uvCoords);
		uvBuffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuffer, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind buffer
		
		GL30.glBindVertexArray(0);
	}
	
	public void updateVertices(int offset, float[] data) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboID);
		FloatBuffer floatbuffer = BufferUtils.newFloatBuffer(data.length);
		floatbuffer.put(data);
		floatbuffer.flip();
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset*4, floatbuffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void getVertices(int offset, int length, float[] container) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboID);
		FloatBuffer buffer = BufferUtils.newFloatBuffer(length);
		GL15.glGetBufferSubData(GL15.GL_ARRAY_BUFFER, offset*4, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	
		buffer.get(container, 0, length);
	}
	
	public void dispose() {
		GL30.glDeleteVertexArrays(vaoID);
		GL15.glDeleteBuffers(positionVboID);
	}
}
