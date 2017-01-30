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
	
	/**
	 * @param bytes This stucture must be rectangular in nature
	 */
	public MeshWorker(byte[][][] bytes) {
		this.data = bytes;
		SIZE_X = bytes.length;
		SIZE_Y = bytes[0].length;
		SIZE_Z = bytes[0][0].length;
	}

	@Override
	public MeshWorkerResult call() throws Exception {
		// 28 MB if 16 * 256 * 16
		float[] positionBuffer = new float[108*SIZE_X*SIZE_Y*SIZE_Z]; // maximum vertices for this chunk
		int positionIndex = 0;
		
		// 18.8 MB if 16 * 256 * 16
		float[] uvBuffer = new float[72*SIZE_X*SIZE_Y*SIZE_Z];
		int uvIndex = 0;
		
		long nanoTime = System.nanoTime();
		
		for (int x = 0; x < SIZE_X; x++) {
			for (int y = 0; y < SIZE_Y; y++) {
				for (int z = 0; z < SIZE_Z; z++) {
					if (data[x][y][z] == 0) {
						continue;
					}
					
					// kaudal face
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
					
					// left face
					
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
					
					// right face
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
					
					// posterior face
					
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
					
					// anterior face
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
					
					// cranial face
					
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
			}
		}
		
		//System.out.println("Nanos for contruction: "+(System.nanoTime()-nanoTime));
		
		FloatBuffer positions = BufferUtils.newFloatBuffer(positionIndex);
		positions.put(positionBuffer, 0, positionIndex);
		positions.flip();
		
		FloatBuffer uvs = BufferUtils.newFloatBuffer(uvIndex);
		uvs.put(uvBuffer, 0, uvIndex);
		uvs.flip();
		
		return new MeshWorkerResult(positions, uvs);
	}
	
	
}
