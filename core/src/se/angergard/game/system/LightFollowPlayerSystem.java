package se.angergard.game.system;

import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LightFollowPlayerSystem extends EntitySystem implements Initializable, Createable{

	private Engine engine;
	private Entity light; 
	private Entity player;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first();
	}
	
	@Override
	public void update(float deltaTime) {
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(player);
		Sprite sprite = spriteComponent.sprite;
		
		PointLightComponent pointLightComponent = Objects.POINT_LIGHT_MAPPER.get(light);
		
		if(pointLightComponent.pointLight != null){
			pointLightComponent.pointLight.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);		
		}
	}

	@Override
	public void create() {
		light = new Entity();
		
		PointLightComponent pointLightComponent = new PointLightComponent();
		pointLightComponent.color = Color.WHITE;
		pointLightComponent.numRays = 100;
		pointLightComponent.maxDistance = 20f;
		pointLightComponent.x = 0;
		pointLightComponent.y = 0;
		
		LightComponent lightComponent = new LightComponent();
		lightComponent.lightType = LightType.PointLight;
		
		light.add(pointLightComponent);
		light.add(lightComponent);
		
		engine.addEntity(light);
	}

	@Override
	public void init() {
		
	}
	
}
