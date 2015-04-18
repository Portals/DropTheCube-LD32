package se.angergard.game.system;

import se.angergard.game.component.RemoveEntityTimerComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Array;

public class RemoveEntityTimerSystem extends EntitySystem{
	
	public RemoveEntityTimerSystem() {
		entities = new Array<Entity>();
		entitiesToRemove = new Array<Entity>();
	}

	private Array<Entity> entities;
	private Array<Entity> entitiesToRemove;
	private Engine engine;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		engine.addEntityListener(new EntityListener(){

			@Override
			public void entityAdded(Entity entity) {
				RemoveEntityTimerComponent comp = Objects.REMOVE_ENTITY_TIMER_MAPPER.get(entity); 
				if(comp != null)
					entities.add(entity);
			}

			@Override
			public void entityRemoved(Entity entity) {}
			
		});
	}
	
	@Override
	public void update(float deltaTime) {
		for(Entity entity : entities){
			RemoveEntityTimerComponent removeEntityTimerComponent = Objects.REMOVE_ENTITY_TIMER_MAPPER.get(entity);
			removeEntityTimerComponent.time -= deltaTime;
			if(removeEntityTimerComponent.time <= 0){
				entitiesToRemove.add(entity);
			}
		}
		if(entitiesToRemove.size == 0){
			return;
		}
		for(Entity entityToRemove : entitiesToRemove){
			engine.removeEntity(entityToRemove);
			entities.removeValue(entityToRemove, true);
		}
		
		entitiesToRemove.clear();
	}
	
}
