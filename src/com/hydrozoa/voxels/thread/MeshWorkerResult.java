package com.hydrozoa.voxels.thread;

import java.nio.FloatBuffer;

public class MeshWorkerResult {
	
	private FloatBuffer positions;
	private FloatBuffer uvs;
	
	public MeshWorkerResult(FloatBuffer positions, FloatBuffer uvs) {
		this.positions = positions;
		this.uvs = uvs;
	}

	public FloatBuffer getPositions() {
		return positions;
	}

	public FloatBuffer getUvs() {
		return uvs;
	}
}
