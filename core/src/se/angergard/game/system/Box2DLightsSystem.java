package se.angergard.game.system;

import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.DirectionalLightComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;

public class Box2DLightsSystem extends EntitySystem implements Initializable{
	
	private RayHandler rayHandler;
	
	@Override
	public void init() {
		rayHandler = new RayHandler(Objects.world);
		rayHandler.setShadows(Values.SHADOWS);
		rayHandler.setAmbientLight(Values.AMBIENT_LIGHT);
		rayHandler.setAmbientLight(Values.AMBIENT_LIGHT_BRIGHTNESS);
	}
		
	@Override
	public void update(float deltaTime) {
		rayHandler.setCombinedMatrix(Objects.camera.combined);
		rayHandler.updateAndRender();
	}
	
	@Override
	public void addedToEngine(final Engine engine) { 
		engine.addEntityListener(new EntityListener(){

			@Override
			public void entityAdded(Entity entity) {
				LightComponent lightComponent = Objects.LIGHT_MAPPER.get(entity);
				if(lightComponent == null){
					return;
				}

				LightType lightType = lightComponent.lightType;
				
				if(lightType == LightType.ConeLight){
					ConeLightComponent comp = Objects.CONE_LIGHT_MAPPER.get(entity);
					ConeLight coneLight = new ConeLight(rayHandler, comp.numRays, comp.color, comp.maxDistance, comp.x, comp.y, comp.directionDegree, comp.coneDegree);
					comp.coneLight = coneLight;
				}
				else if(lightType == LightType.PointLight){
					PointLightComponent comp = Objects.POINT_LIGHT_MAPPER.get(entity);
					PointLight pointLight = new PointLight(rayHandler, comp.numRays, comp.color, comp.maxDistance, comp.x, comp.y);
					comp.pointLight = pointLight;
				}
				else if(lightType == LightType.DirectionalLight){
					DirectionalLightComponent comp = Objects.DIRECTIONAL_LIGHT_MAPPER.get(entity);
					DirectionalLight directionalLight = new DirectionalLight(rayHandler, comp.numRays, comp.color, comp.directionDegree);
					comp.directionalLight = directionalLight;
				}
			}

			@Override
			public void entityRemoved(Entity entity) {
				
			}
			
		});
	}

}
