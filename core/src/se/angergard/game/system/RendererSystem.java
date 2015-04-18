package se.angergard.game.system;

import se.angergard.game.component.SpriteComponent;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class RendererSystem extends IteratingSystem{
	
	@SuppressWarnings("unchecked")
	public RendererSystem() {
		super(Family.getFor(SpriteComponent.class));
		
		batch = new SpriteBatch();
		renderQueue = new Array<Sprite>();
	}
	
	private SpriteBatch batch;
	private Array<Sprite> renderQueue;
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);		
		batch.setProjectionMatrix(Objects.CAMERA.combined);
		batch.begin();
		
		for(Sprite sprite : renderQueue){
			sprite.draw(batch);
		}
		
		batch.end();
		
		renderQueue.clear();
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(Objects.SPRITE_MAPPER.get(entity).sprite);
	}

	
	
}
