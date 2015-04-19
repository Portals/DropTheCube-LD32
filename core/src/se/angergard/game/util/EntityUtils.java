package se.angergard.game.util;

import se.angergard.game.Health;
import se.angergard.game.component.AStarComponent;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.DirectionalLightComponent;
import se.angergard.game.component.EnemyComponent;
import se.angergard.game.component.HealthComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.ScoreComponent;
import se.angergard.game.component.SpeedComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.enums.LightType;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class EntityUtils {
	
	public static final Entity createEnemyAStar(float x, float y){		
		Entity entity = new Entity();
		
		EnemyComponent enemyComponent = new EnemyComponent();
		
		SpriteComponent spriteComponent = new SpriteComponent();
		spriteComponent.sprite = new Sprite(new Texture(Gdx.files.internal("Ai.png")));
		spriteComponent.sprite.setPosition(x, y);
		
		Box2DComponent box2DComponent = Box2DUtils.create(spriteComponent.sprite, BodyType.DynamicBody);
		
		AStarComponent aStarComponent = new AStarComponent();

		SpeedComponent speedComponent = new SpeedComponent();
		
		AshleyUtils.addComponents(entity, enemyComponent, spriteComponent, aStarComponent, box2DComponent, speedComponent);
		
		box2DComponent.fixture.setUserData(entity);
		
		return entity;
	}
	
//	public static final Entity createEnemy(float x, float y){
//		Entity entity = new Entity();
//		
//		EnemyComponent enemyComponent = new EnemyComponent();
//		
//		SpriteComponent spriteComponent = new SpriteComponent();
//		spriteComponent.sprite = new Sprite(new Texture(Gdx.files.internal("Ai.png")));
//		spriteComponent.sprite.setPosition(x, y);
//		
//		entity.add(enemyComponent);
//		entity.add(spriteComponent);	
//
//		return entity;
//	}
		
	public static final Entity createPlayer(){
		Entity entity = new Entity();
		
		SpriteComponent spriteComponent = new SpriteComponent();
		Texture texture = new Texture(Gdx.files.internal("Player.png"));
		Sprite sprite = new Sprite(texture);
		sprite.setPosition(CameraSize.getWidth() / 4 - sprite.getWidth() / 2, CameraSize.getHeight() / 4 - sprite.getHeight() / 2);
		sprite.setScale(1.5f);
		spriteComponent.sprite = sprite;
		
		Box2DComponent box2DComponent = Box2DUtils.create(sprite, BodyType.DynamicBody);
		
		PlayerComponent playerComponent = new PlayerComponent();
		
		HealthComponent healthComponent = new HealthComponent();
		healthComponent.health = new Health(Values.MAX_HEALTH);
		healthComponent.heartTexture = new Texture(Gdx.files.internal("heart.png"));
		
		ScoreComponent scoreComponent = new ScoreComponent();
		
		AshleyUtils.addComponents(entity, spriteComponent, box2DComponent, playerComponent, healthComponent, scoreComponent);
		
		box2DComponent.fixture.setUserData(entity);
		
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
