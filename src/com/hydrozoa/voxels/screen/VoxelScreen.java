package com.hydrozoa.voxels.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.MathUtils;
import com.hydrozoa.voxels.Voxels;
import com.hydrozoa.voxels.graphics.TexturedMesh;
import com.hydrozoa.voxels.graphics.VoxelShader;
import com.hydrozoa.voxels.model.Chunk;
import com.hydrozoa.voxels.thread.MeshWorker;
import com.hydrozoa.voxels.thread.MeshWorkerResult;
import com.hydrozoa.voxels.util.ArrayUtils;

public class VoxelScreen extends GameScreen {
	
	private List<Future<MeshWorkerResult>> results = new ArrayList<Future<MeshWorkerResult>>();
	
	private VoxelShader shader;
	private Texture texture;
	private Chunk chunk;
	
	private PerspectiveCamera cam;

	private CameraInputController controller;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<MeshWorkerResult> workerFuture;
	private MeshWorkerResult workerResult;
	
	public VoxelScreen(Voxels app) {
		super(app);
		texture = app.getAssetManager().get("res/texture.png", Texture.class);
		
		chunk = new Chunk(new GridPoint3(0,0,0));
		
		shader = new VoxelShader(app.getAssetManager());
		
		cam = new PerspectiveCamera(70f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(-10f, 10f, 10f);
		cam.lookAt(0f, 0f, 0f);
		cam.update();
		
		controller = new CameraInputController(cam);
	}

	@Override
	public void dispose() {
		chunk.dispose();
		shader.dispose();
		executor.shutdown();
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float delta) {
		// remove one block every frame
		int x = MathUtils.random(Chunk.SIZE_X-1);
		int y = MathUtils.random(Chunk.SIZE_Y-1);
		int z = MathUtils.random(Chunk.SIZE_Z-1);
		chunk.removeBlock(x, y, z);
		
		// schedule thread to mesh chunk
		if (chunk.needsFreshMesh() && workerFuture == null) {
			//System.out.println("Allocating thread for meshing...");
			workerFuture = executor.submit(new MeshWorker(ArrayUtils.threeDeeArrayCopier(chunk.getBytes())));
		}
		
		// attach mesh to chunk
		if (workerFuture.isDone()) {
			try {
				//System.out.println("Thread returned, now uploading new mesh to GPU...");
				workerResult = workerFuture.get();
				chunk.updateMesh(new TexturedMesh(workerResult.getPositions(),  workerResult.getUvs()));
				workerFuture = null;
			} catch (InterruptedException e) {
				// does not happen as these threads never wait() or sleep()
			} catch (ExecutionException e) {
				System.err.println("Error generating mesh for chunk:");
				e.printStackTrace();
			}
		}
		
		GL11.glClearColor(0.2f, 0.0f, 0.7f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		shader.begin();
		shader.loadCombinedMatrix(cam.combined);
		
		texture.bind(0);
		
		if (chunk.hasMesh()) {
			GL30.glBindVertexArray(chunk.getMesh().getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, chunk.getMesh().getVertexCount());
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		
		shader.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

}
