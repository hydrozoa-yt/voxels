package com.hydrozoa.voxels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.hydrozoa.voxels.graphics.VoxelShader;
import com.hydrozoa.voxels.loading.TextLoader;
import com.hydrozoa.voxels.screen.VoxelScreen;

public class Voxels extends Game {
	
	private AssetManager assetManager;
	
	private VoxelScreen screen;

	@Override
	public void create() {
		assetManager = new AssetManager();
		assetManager.setLoader(String.class, new TextLoader(new InternalFileHandleResolver()));
		assetManager.load(VoxelShader.VERTEX_SHADER_PATH, String.class);
		assetManager.load(VoxelShader.FRAGMENT_SHADER_PATH, String.class);
		assetManager.load("res/texture.png", Texture.class);
		assetManager.finishLoading();
		
		screen = new VoxelScreen(this);
		
		this.setScreen(screen);
	}
	
	@Override
	public void render() {
		super.render();
		System.out.println("FPS: "+Gdx.graphics.getFramesPerSecond());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		assetManager.dispose();
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
