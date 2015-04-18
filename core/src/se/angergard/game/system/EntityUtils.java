package se.angergard.game.system;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.DirectionalLightComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.CameraSize;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EntityUtils {
	
	public static final Entity createPlayer(){
		Entity entity = new Entity();
		
		SpriteComponent spriteComponent = new SpriteComponent();
		Texture texture = new Texture(Gdx.files.internal("Player.png"));
		Sprite sprite = new Sprite(texture);
		sprite.setPosition(CameraSize.getWidth() / 4 - sprite.getWidth() / 2, CameraSize.getHeight() / 4 - sprite.getHeight() / 2);
		sprite.setScale(1.5f);
		spriteComponent.sprite = sprite;
		
		Box2DComponent box2DComponent = Box2DUtils.create(sprite);
		
		PlayerComponent playerComponent = new PlayerComponent();
		
		entity.add(spriteComponent);
		entity.add(box2DComponent);
		entity.add(playerComponent);
		
		return entity;
	}
	
	public static final Entity createConeLight(ConeLightComponent coneLightComponent){
		Entity entity = new Entity();
		LightComponent lightComponent = new LightComponent();
		lightComponent.lightType = LightType.ConeLight;
		
		entity.add(lightComponent);
		entity.add(coneLightComponent);
		
		return entity;
	}
	
	
	public static final Entity createPointLight(PointLightComponent pointLightComponent){
		Entity entity = new Entity();
		LightComponent lightComponent = new LightComponent();
		lightComponent.lightType = LightType.PointLight;
		
		entity.add(lightComponent);
		entity.add(pointLightComponent);
		
		return entity;
	}
	
	
	public static final Entity createDirectionalLight(DirectionalLightComponent directionalLightComponent){
		Entity entity = new Entity();
		LightComponent lightComponent = new LightComponent();
		lightComponent.lightType = LightType.DirectionalLight;
		
		entity.add(lightComponent);
		entity.add(directionalLightComponent);

		return entity;
	}
	
}
