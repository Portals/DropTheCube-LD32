package se.angergard.game.system;

import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class RendererSystem extends IteratingSystem implements Initializable{
	
	@SuppressWarnings("unchecked")
	public RendererSystem() {
		super(Family.getFor(SpriteComponent.class));
	}
	
	private Array<Sprite> renderQueue;
	

	@Override
	public void init() {
		renderQueue = new Array<Sprite>();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);		
		Objects.BATCH.setProjectionMatrix(Objects.camera.combined);
		Objects.BATCH.begin();
		
		for(Sprite sprite : renderQueue){
			sprite.draw(Objects.BATCH);
		}
		
		Objects.BATCH.end();
		
		renderQueue.clear();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(Objects.SPRITE_MAPPER.get(entity).sprite);
	}

	
	
}
