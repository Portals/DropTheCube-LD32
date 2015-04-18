package se.angergard.game.system;

import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Box2DDebugRendererSystem extends EntitySystem{

	public Box2DDebugRendererSystem() {
		renderer = new Box2DDebugRenderer();
	}
	
	private Box2DDebugRenderer renderer;
	
	@Override
	public void update(float deltaTime) {
		renderer.render(Objects.WORLD, Objects.CAMERA.combined.cpy().scl(Values.PIXELS_PER_METER));
	}
	
}
