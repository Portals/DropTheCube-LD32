package se.angergard.game.system;

import se.angergard.game.Health;
import se.angergard.game.component.HealthComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

public class DrawHealthSystem extends EntitySystem implements Initializable{

	public DrawHealthSystem(){}
		
	private HealthComponent healthComponent;
	private Engine engine;

	@Override
	public void update(float deltaTime) {
		Objects.BATCH.setProjectionMatrix(Objects.CAMERA.combined);
		Objects.BATCH.begin();
		
		Health health = healthComponent.health;
		
		for(int i = 0; i < health.getHealth(); i++){
			
		}
		
		Objects.BATCH.end();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		healthComponent = Objects.HEALTH_MAPPER.get(engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first());
	}
	
}
