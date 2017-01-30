package com.hydrozoa.voxels.model;

import java.nio.FloatBuffer;

import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.utils.BufferUtils;
import com.hydrozoa.voxels.graphics.TexturedMesh;

public class Chunk {
	
	public final static int SIZE_X = 16;
	public final static int SIZE_Y = 128;
	public final static int SIZE_Z = 16;
	
	private byte[][][] blocks;
	
	/* does this chunk need mesh updating? */
	private boolean isDirty = false;
	
	private TexturedMesh mesh;
	
	private GridPoint3 position;
	
	public Chunk(GridPoint3 position) {
		this.position = position;
		blocks = new byte[SIZE_X][SIZE_Y][SIZE_Z];
		for (int x = 0; x < SIZE_X; x++) {
			for (int y = 0; y < SIZE_Y; y++) {
				for (int z = 0; z < SIZE_Z; z++) {
					blocks[x][y][z] = 1;
				}
			}
		}
		isDirty = true;
	}
	
	public void removeBlock(int x, int y, int z) {
		blocks[x][y][z] = 0;
		isDirty = true;
	}
	
	private void generateMesh() {
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
					if (blocks[x][y][z] == 0) {
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
		
		//System.out.println("Buffer size: "+((positions.capacity()*4)/1000)+" KBs");
		
		// if there is an old mesh, dispose of it
		if (mesh != null) {
			mesh.dispose();
		}
		
		long nanoTime2 = System.nanoTime();
		
		mesh = new TexturedMesh(positions, uvs);
		//System.out.println("Nanos for uploading: "+(System.nanoTime()-nanoTime2));
		
		isDirty = false;
	}
	
	/**
	 * @param mesh			New Mesh for this Chunk
	 * @param chunkUpdate	Millitime for chunks last update
	 */
	public void updateMesh(TexturedMesh mesh) {
		if (hasMesh()) {
			this.mesh.dispose();
		}
		this.mesh = mesh;
		isDirty = false;
	}
	
	public boolean needsFreshMesh() {
		return isDirty;
	}
	
	public byte[][][] getBytes() {
		return blocks;
	}
	
	public TexturedMesh getMesh() {
		return mesh;
	}
	
	public boolean hasMesh() {
		return (mesh != null); 
	}
	
	public GridPoint3 getPosition() {
		return position;
	}

	public void dispose() {
		mesh.dispose();
	}

}
