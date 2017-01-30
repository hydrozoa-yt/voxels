package com.hydrozoa.voxels;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Voxels";
		config.height = 400;
		config.width = 600;
		config.vSyncEnabled = false;
		config.foregroundFPS = 9000;
		
		new LwjglApplication(new Voxels(), config);
	}

}
