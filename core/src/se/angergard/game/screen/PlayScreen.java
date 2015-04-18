package se.angergard.game.screen;

import se.angergard.game.system.Box2DDebugRendererSystem;
import se.angergard.game.system.Box2DLightsSystem;
import se.angergard.game.system.Box2DToSpritePositionSystem;
import se.angergard.game.system.EntityUtils;
import se.angergard.game.system.MapControllerSystem;
import se.angergard.game.system.PlayerSystem;
import se.angergard.game.system.RemoveEntityTimerSystem;
import se.angergard.game.system.RemoveFloorSystem;
import se.angergard.game.system.RendererSystem;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class PlayScreen implements Screen{

	private Engine entityEngine;
	
	@Override
	public void show() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		
		entityEngine = new Engine();

		entityEngine.addEntity(EntityUtils.createPlayer());
		
		entityEngine.addSystem(new RemoveEntityTimerSystem());
		entityEngine.addSystem(new PlayerSystem());
		entityEngine.addSystem(new RemoveFloorSystem());
		entityEngine.addSystem(new Box2DToSpritePositionSystem());
		entityEngine.addSystem(new MapControllerSystem());
		entityEngine.addSystem(new RendererSystem());
		entityEngine.addSystem(new Box2DLightsSystem());
		entityEngine.addSystem(new Box2DDebugRendererSystem());
				
		Objects.CAMERA.zoom = Values.CAMERA_ZOOM;
		Objects.CAMERA.translate(new Vector2(CameraSize.getWidth() / 2, CameraSize.getHeight() / 2));;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Objects.CAMERA.update();
	
		Objects.WORLD.step(delta, 6, 2);
		entityEngine.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {		
		
	}

}
