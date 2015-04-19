package se.angergard.game.system;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.HoleComponent;
import se.angergard.game.component.RemoveEntityTimerComponent;
import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.RunnablePool;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RemoveFloorSystem extends EntitySystem implements Initializable{

	public RemoveFloorSystem() {
		runnablePool = new RunnablePool();
	}
	
	private RunnablePool runnablePool;
	
	public void update(float delta){
		runnablePool.run();
	}
	
	@Override
	public void init() {	
		runnablePool = new RunnablePool();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(final Engine engine) {
		engine.addEntityListener(Family.getFor(RemoveFloorComponent.class), new EntityListener(){

			@Override
			public void entityAdded(final Entity entity) {				
				runnablePool.add(new Runnable(){
					@Override
					public void run() {
						if(Objects.WORLD.isLocked()){
							runnablePool.add(this);
							return;
						}
						else{
							RemoveFloorComponent removeFloorComponent = Objects.REMOVE_FLOOR_MAPPER.get(entity);
							
							Vector2 tilePosition = removeFloorComponent.playerPosition.cpy().scl(1f / Values.TILED_SIZE_PIXELS);
							tilePosition.x = (int) tilePosition.x;
							tilePosition.y = (int) tilePosition.y;
							tilePosition.scl(Values.MAP_SIZE);
							
							Entity holeEntity = new Entity();
							
							SpriteComponent spriteComponent = new SpriteComponent();
							spriteComponent.sprite = new Sprite(new Texture(Gdx.files.internal("Hole.png")));
							spriteComponent.sprite.setPosition(tilePosition.x, tilePosition.y);
							
							RemoveEntityTimerComponent removeEntityTimerComponent = new RemoveEntityTimerComponent();
							removeEntityTimerComponent.time = Values.REMOVE_FLOOR_TIME;
							
							//Box2DComponent box2DComponent = Box2DUtils.createCircle(spriteComponent.sprite);
							
							HoleComponent holeComponent = new HoleComponent();
							
							entity.add(removeEntityTimerComponent);
							entity.add(spriteComponent);
							entity.add(holeComponent);
							//entity.add(box2DComponent);
							
							//box2DComponent.fixture.setUserData(entity);
							
							engine.addEntity(holeEntity);
						}
					}
				});
			}

			@Override
			public void entityRemoved(Entity entity) {}
			
		});
	}

}
