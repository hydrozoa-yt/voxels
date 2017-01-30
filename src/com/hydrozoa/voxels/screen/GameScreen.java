package com.hydrozoa.voxels.screen;

import com.badlogic.gdx.Screen;
import com.hydrozoa.voxels.Voxels;

public abstract class GameScreen implements Screen {
	
	private Voxels app;
	
	public GameScreen(Voxels app) {
		this.app = app;
	}
	
	public Voxels getApp() {
		return app;
	}
}
