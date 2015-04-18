package se.angergard.game.system;

import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Pixels;
import se.angergard.game.util.Values;
import box2dLight.ConeLight;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;

public class RotationConeLightSystem extends EntitySystem implements Initializable, Createable{

	private float direction = 0;
	private Entity coneLightEntity;
	private ConeLight coneLight;
	private Engine engine;
	
	@Override
	public void create() {
		coneLightEntity = new Entity();
		
		LightComponent lightComponent = new LightComponent();
		lightComponent.lightType = LightType.ConeLight;
		
		ConeLightComponent coneLightComponent = new ConeLightComponent();
		coneLightComponent.color = new Color(0.5f, 0.5f, .5f, 1.0f);
		coneLightComponent.numRays = 100;
		coneLightComponent.x = 5;
		coneLightComponent.y = 5;
		coneLightComponent.maxDistance = 15f;
		coneLightComponent.coneDegree =  180f;
		coneLightComponent.directionDegree = 50;
		
		coneLightEntity.add(lightComponent);
		coneLightEntity.add(coneLightComponent);
		
		engine.addEntity(coneLightEntity);
	}

	@Override
	public void init() {
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void update(float deltaTime) {
		ConeLightComponent comp = Objects.CONE_LIGHT_MAPPER.get(coneLightEntity);
		
		if(comp.coneLight == null){
			return;
		}
		if(coneLight == null){
			coneLight = comp.coneLight;
		}
		
		direction += 50 * deltaTime;
				
		//coneLight.setDirection(direction);
		
	}
	
}
