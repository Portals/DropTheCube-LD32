package se.angergard.game.util;

import se.angergard.game.component.HoleComponent;

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
