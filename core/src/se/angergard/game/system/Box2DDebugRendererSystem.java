package se.angergard.game.system;

import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Box2DDebugRendererSystem extends EntitySystem implements Initializable{
	
	private Box2DDebugRenderer renderer;
	
	@Override
	public void init() {
		renderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void update(float deltaTime) {
		renderer.render(Objects.world, Objects.camera.combined.cpy().scl(Values.PIXELS_PER_METER));
	}


	
}
