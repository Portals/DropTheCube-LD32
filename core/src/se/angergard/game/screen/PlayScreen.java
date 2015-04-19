package se.angergard.game.screen;

import se.angergard.game.MainGame;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.system.AStarSystem;
import se.angergard.game.system.Box2DDebugRendererSystem;
import se.angergard.game.system.Box2DLightsSystem;
import se.angergard.game.system.Box2DToSpritePositionSystem;
import se.angergard.game.system.DrawInfoSystem;
import se.angergard.game.system.DrawPossibleFloorPlacementsSystem;
import se.angergard.game.system.LightFollowPlayerSystem;
import se.angergard.game.system.MapControllerSystem;
import se.angergard.game.system.PlayerSystem;
import se.angergard.game.system.RemoveEntityTimerSystem;
import se.angergard.game.system.RemoveFloorSystem;
import se.angergard.game.system.RendererSystem;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.EntityUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PlayScreen implements Screen{
	
	public PlayScreen(MainGame game){
		this.game = game;
	}
	
	private MainGame game;
	private Engine entityEngine;
	
	@Override
	public void show() {
		Objects.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Objects.world = new World(new Vector2(0,0), false);
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
				
		entityEngine = new Engine();

		entityEngine.addEntity(EntityUtils.createPlayer());
		
		entityEngine.addSystem(new RemoveEntityTimerSystem());
		entityEngine.addSystem(new PlayerSystem());
		entityEngine.addSystem(new AStarSystem());
		//entityEngine.addSystem(new RotationConeLightSystem()); Doesn't work..
		entityEngine.addSystem(new Box2DToSpritePositionSystem());
		entityEngine.addSystem(new LightFollowPlayerSystem());
		entityEngine.addSystem(new MapControllerSystem(game));
		entityEngine.addSystem(new RendererSystem());
		entityEngine.addSystem(new Box2DLightsSystem());
		entityEngine.addSystem(new Box2DDebugRendererSystem());
		entityEngine.addSystem(new DrawInfoSystem());
		entityEngine.addSystem(new DrawPossibleFloorPlacementsSystem());
		entityEngine.addSystem(new RemoveFloorSystem());
		
		
		ImmutableArray<EntitySystem> systems = entityEngine.getSystems();
		
		for(int i = 0; i < systems.size(); i++){ // It must have a Initializable
			Initializable init = (Initializable) systems.get(i);
			init.init();
		}
		
		for(int i = 0; i < systems.size(); i++){ // It must not have a Createable
			if(systems.get(i) instanceof Createable){
				Createable create = (Createable) systems.get(i);
				create.create();
			}
		}
		
		Objects.camera.zoom = Values.CAMERA_ZOOM;
		Objects.camera.translate(new Vector2(CameraSize.getWidth() / 2, CameraSize.getHeight() / 2));;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Objects.camera.update();
		
		Objects.world.step(delta, 6, 2);
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
