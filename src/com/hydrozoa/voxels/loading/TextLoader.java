package com.hydrozoa.voxels.loading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class TextLoader extends AsynchronousAssetLoader<String, TextLoader.TextParameter>{

	private String text;
	
	public TextLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
		text = file.readString();
	}

	@Override
	public String loadSync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
		return text;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextParameter parameter) {
		return null;
	}
	
	static public class TextParameter extends AssetLoaderParameters<String> {}
	
}
