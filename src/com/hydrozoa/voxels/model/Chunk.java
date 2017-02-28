package com.hydrozoa.voxels.model;

import com.badlogic.gdx.math.GridPoint3;
import com.hydrozoa.voxels.graphics.TexturedMesh;

public class Chunk {
	
	public final static int SIZE_X = 16;
	public final static int SIZE_Y = 256;
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
