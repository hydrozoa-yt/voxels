package com.hydrozoa.voxels.thread;

import java.nio.FloatBuffer;
import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.BufferUtils;

/**
 * Generates a Mesh.
 */
public class MeshWorker implements Callable<MeshWorkerResult> {
	
	/* Blocks of the chunk  */
	private byte[][][] data;
	
	private int SIZE_X, SIZE_Y, SIZE_Z;
	
	private int positionIndex;
	private int uvIndex;
	
	/**
	 * @param bytes This stucture must be rectangular in nature
	 */
	public MeshWorker(byte[][][] bytes) {
		this.data = bytes;
		SIZE_X = bytes.length;
		SIZE_Y = bytes[0].length;
		SIZE_Z = bytes[0][0].length;
		
		positionIndex = 0;
		uvIndex = 0;
	}

	@Override
	public MeshWorkerResult call() throws Exception {
		// 28 MB if 16 * 256 * 16
		float[] positionBuffer = new float[108*SIZE_X*SIZE_Y*SIZE_Z]; // maximum vertices for this chunk
		
		// 18.8 MB if 16 * 256 * 16
		float[] uvBuffer = new float[72*SIZE_X*SIZE_Y*SIZE_Z];
		
		long nanoTime = System.nanoTime();
		
		for (int x = 0; x < SIZE_X; x++) {
			for (int y = 0; y < SIZE_Y; y++) {
				for (int z = 0; z < SIZE_Z; z++) {
					if (data[x][y][z] == 0) {
						continue;
					}
					
					// kaudal face
					if (y > 0) {
						if (data[x][y-1][z]==0) { // if there is air
							addBottomFace(positionBuffer, uvBuffer, x, y, z);
						}
					} else {
						addBottomFace(positionBuffer, uvBuffer, x, y, z);
					}
					
					// left face
					if (x > 0) {
						if (data[x-1][y][z]==0) {
							addLeftFace(positionBuffer, uvBuffer, x, y, z);
						}
					} else {
						addLeftFace(positionBuffer, uvBuffer, x, y, z);
					}
					
					// right face
					if (x < SIZE_X-1) {
						if (data[x+1][y][z]==0) {
							addRightFace(positionBuffer, uvBuffer, x, y, z);
						} 
					} else {
						addRightFace(positionBuffer, uvBuffer, x, y, z);
					}
					
					// posterior face
					if (z > 0) {
						if (data[x][y][z-1]==0) {
							addBackFace(positionBuffer, uvBuffer, x, y, z);
						}
					} else {
						addBackFace(positionBuffer, uvBuffer, x, y, z);
					}
					
					// anterior face
					if (z < SIZE_Z-1) {
						if (data[x][y][z+1]==0) {
							addFrontFace(positionBuffer, uvBuffer, x, y, z);
						}
					} else {
						addFrontFace(positionBuffer, uvBuffer, x, y, z);
					}
					
					// cranial face
					if (y < SIZE_Y-1) {
						if (data[x][y+1][z]==0) { // if there is air
							addTopFace(positionBuffer, uvBuffer, x, y, z);
						}
					} else {
						addTopFace(positionBuffer, uvBuffer, x, y, z);
					}
				}
			}
		}
		
		System.out.println("Millis for contruction: "+(System.nanoTime()-nanoTime)/1000000f + " ms.");
		System.out.println("Number of floats to upload: "+(positionIndex+uvIndex));
		
		FloatBuffer positions = BufferUtils.newFloatBuffer(positionIndex);
		positions.put(positionBuffer, 0, positionIndex);
		positions.flip();
		
		FloatBuffer uvs = BufferUtils.newFloatBuffer(uvIndex);
		uvs.put(uvBuffer, 0, uvIndex);
		uvs.flip();
		
		return new MeshWorkerResult(positions, uvs);
	}
	
	private void addBottomFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
	
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
	private void addFrontFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
	private void addBackFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
	private void addRightFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
	private void addLeftFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+1f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
	private void addTopFace(float[] positionBuffer, float[] uvBuffer, int x, int y, int z) {
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 0f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+1f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 1f;
		uvBuffer[uvIndex++] = 1f;
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+-1f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 1f;
		
		
		positionBuffer[positionIndex++] = x*1f+0f;
		positionBuffer[positionIndex++] = y*1f+0f;
		positionBuffer[positionIndex++] = z*-1f+0f;
		uvBuffer[uvIndex++] = 0f;
		uvBuffer[uvIndex++] = 0f;
	}
	
}
