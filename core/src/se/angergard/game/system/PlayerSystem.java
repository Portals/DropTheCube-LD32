package se.angergard.game.system;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;
import box2dLight.PointLight;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerSystem extends EntitySystem implements Initializable{

	private Entity player;
	private Engine engine;
	
	@Override
	public void init() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0);
		this.engine = engine;
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
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			sendRemoveFloorEntity();
		}
		
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(player);
		Sprite sprite = spriteComponent.sprite;
		
		PointLightComponent pointLightComponent = Objects.POINT_LIGHT_MAPPER.get(player);
		if(pointLightComponent.pointLight == null){
			System.out.println("PointLight == null");
			return;
		}
		
		PointLight pointLight = pointLightComponent.pointLight;
		pointLight.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
		
	}
	
	private void sendRemoveFloorEntity(){
		Sprite sprite = Objects.SPRITE_MAPPER.get(player).sprite;
		Vector2 playerPosition = new Vector2(sprite.getX(), sprite.getY());
		
		Entity entity = new Entity();
		
		RemoveFloorComponent removeFloorComponent = new RemoveFloorComponent();
		removeFloorComponent.playerPosition = playerPosition;
				
		entity.add(removeFloorComponent);
		
		engine.addEntity(entity);
	}

	
}
