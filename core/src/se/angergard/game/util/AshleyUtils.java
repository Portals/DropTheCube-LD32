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
package se.angergard.game.util;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

public final class AshleyUtils {

	private AshleyUtils(){}
	
	public static final <T extends Component> boolean hasComponent(Entity e1, Class<T> component){
		return e1.getComponent(component) != null;
	}

	/**
	 * 
	 * @return The first entity that has the component, returns null if none of the entities has the given component. Works good with PlayerComponent for example
	 */
	public static final <T extends Component> Entity hasComponent(Entity e1, Entity e2, Class<T> component){
		if(e1 == null && e2 == null){
			return null;
		}
		else if(e1 == null){
			return hasComponent(e2, component) ? e2 : null;
		}
		else if(e2 == null){
			return hasComponent(e1, component) ? e1 : null;
		}
		return hasComponent(e1, component) ? e1 : hasComponent(e2, component)? e2 : null; 
	}

	@SuppressWarnings("unchecked")
	public static final <T extends Component> boolean entityWithComponentExist(Engine engine, Class<T> component) {
		return engine.getEntitiesFor(Family.getFor(component)).size() > 0;
	}
	
	public static final <T extends Component> void addComponents(Entity entity, Component... components){
		for(Component component : components){
			entity.add(component);
		}
	}
	
}
