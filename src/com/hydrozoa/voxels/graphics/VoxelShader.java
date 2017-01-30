package com.hydrozoa.voxels.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Matrix4;

public class VoxelShader extends ShaderProgram {
	
	public static final String VERTEX_SHADER_PATH = "src/shaders/voxel_vertexshader.glsl";
	public static final String FRAGMENT_SHADER_PATH = "src/shaders/voxel_fragmentshader.glsl";
	
	private int location_combinedMatrix;

	public VoxelShader(AssetManager assetManager) {
		String vertexShader = assetManager.get(VERTEX_SHADER_PATH, String.class);
		String fragmentShader = assetManager.get(FRAGMENT_SHADER_PATH, String.class);
		
		loadShaders(vertexShader, fragmentShader);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_combinedMatrix = super.getUniformLocation("combinedMatrix");
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
		super.bindAttributes(1, "uvCoords");
	}
	
	public void loadCombinedMatrix(Matrix4 matrix){
		super.loadMatrix(location_combinedMatrix, matrix);
	}
}
