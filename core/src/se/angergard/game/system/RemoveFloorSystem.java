package se.angergard.game.system;

import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

public class RemoveFloorSystem extends EntitySystem{

	public RemoveFloorSystem() {
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		engine.addEntityListener(Family.getFor(RemoveFloorComponent.class), new EntityListener(){

			@Override
			public void entityAdded(Entity entity) {
				RemoveFloorComponent removeFloorComponent = Objects.REMOVE_FLOOR_MAPPER.get(entity);
			}

			@Override
			public void entityRemoved(Entity entity) {}
			
		});
	}
	
	@Override
	public void update(float deltaTime) {
		
	}
	
}
