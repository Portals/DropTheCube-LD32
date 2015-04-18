package se.angergard.game.system;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerSystem extends EntitySystem{

	public PlayerSystem(){
		
	}
	
	private Entity player;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0);
	}
	
	@Override
	public void update(float deltaTime) {
		Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(player);
		Body body = box2DComponent.body;
		
		float speed = 100f * deltaTime;
		Vector2 velocity = new Vector2();
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			velocity.y += speed;
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			velocity.x -= speed;
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			velocity.y -= speed;
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			velocity.x += speed;
		}
		body.setLinearVelocity(velocity);
	}
	
}
