/*******************************************************************************
 * Copyright 2015 Theodor Angergård
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package se.angergard.game.system;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.RemoveEntityTimerComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.Array;

public class RemoveEntityTimerSystem extends EntitySystem implements Initializable{

	private Array<Entity> entities;
	private Array<Entity> entitiesToRemove;
	private Engine engine;
	
	@Override
	public void init() {
		entities = new Array<Entity>();
		entitiesToRemove = new Array<Entity>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		engine.addEntityListener(Family.getFor(RemoveEntityTimerComponent.class), new EntityListener(){

			@Override
			public void entityAdded(Entity entity) {
				RemoveEntityTimerComponent comp = Objects.REMOVE_ENTITY_TIMER_MAPPER.get(entity); 
				if(comp != null){
					entities.add(entity);
				}
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
			//Removes body if it had one
			Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(entityToRemove);
			if(box2DComponent != null){
				Box2DUtils.destroyBody(box2DComponent);
			}
			
			engine.removeEntity(entityToRemove);
			entities.removeValue(entityToRemove, true);
		}
		
		entitiesToRemove.clear();
	}

	
}
