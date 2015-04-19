package se.angergard.game.system;

import se.angergard.game.astar.AStar;
import se.angergard.game.astar.Node;
import se.angergard.game.astar.Vector2i;
import se.angergard.game.component.AStarComponent;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.EntityUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class AStarSystem extends IntervalSystem implements Initializable{

	public AStarSystem() {
		super(.1f);
	}
	
	private Entity player;
	private Array<Entity> entities;
	private Engine engine;
	
	private Array<Entity> pathArray;
	

	@Override
	public void init() {
		entities = new Array<Entity>();
		pathArray = new Array<Entity>();
		
		
		engine.addEntityListener(new EntityListener(){

			@Override
			public void entityAdded(Entity entity) {							
				AStarComponent aiStarComponent = Objects.AI_STAR_MAPPER.get(entity);
				if(aiStarComponent == null){
					return;
				}
				entities.add(entity);
			}

			@Override
			public void entityRemoved(Entity entity) {}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first();
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	
	@Override
	protected void updateInterval() {		
		for(Entity entity : entities){
			SpriteComponent playerSpriteCompoennt = Objects.SPRITE_MAPPER.get(player);
			int xPlayer = (int)playerSpriteCompoennt.sprite.getX();
			int yPlayer = (int)playerSpriteCompoennt.sprite.getY();
			
			SpriteComponent enemySpriteComponent = Objects.SPRITE_MAPPER.get(entity);
			int xEnemy = (int)enemySpriteComponent.sprite.getX();
			int yEnemy = (int)enemySpriteComponent.sprite.getY();
			
			Vector2i start = new Vector2i(xEnemy / Values.TILED_SIZE_PIXELS, yEnemy / Values.TILED_SIZE_PIXELS);
			Vector2i goal = new Vector2i(xPlayer / Values.TILED_SIZE_PIXELS, yPlayer / Values.TILED_SIZE_PIXELS);

			AStarComponent aiStarComponent = Objects.AI_STAR_MAPPER.get(entity);
			ImmutableArray<Node> path = AStar.findPath(start, goal);;
			aiStarComponent.path = path;
			
			if(this.pathArray.size != 0){
				for(Entity e : pathArray){
					engine.removeEntity(e);
				}
				pathArray.clear();
			}
			
			if(path == null) continue;
			
			if(!(path.size() > 0)) continue;
			
//			for(int i = path.size() - 1; i > 0; i--){
//				Vector2i pos = path.get(i).position;
//				Entity pathEntity = EntityUtils.createEnemy(pos.x * 16, pos.y * 16);
//				this.pathArray.add(pathEntity);
//				engine.addEntity(pathEntity);
//			}
		
			float speed = 1f;
			
			SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(entity);
			Sprite sprite = spriteComponent.sprite;
			float xPos = sprite.getX();
			float yPos = sprite.getY();
			
			Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(entity);
			Body body = box2DComponent.body;
			
			
			if(path.size() > 0){
				Vector2i vec = path.get(path.size() - 1).position; //size - 1 because the findPath returns the path in wrong order.
				Vector2 velocity = new Vector2();
								
				if(xPos < vec.x * Values.TILED_SIZE_PIXELS){
			 		velocity.x += speed;
				}
				
				if(xPos > vec.x * Values.TILED_SIZE_PIXELS){
					velocity.x -= speed;
				}
				
				if(yPos < vec.y * Values.TILED_SIZE_PIXELS){
					velocity.y += speed;
				}

				if(yPos > vec.y * Values.TILED_SIZE_PIXELS){
					velocity.y -= speed;
				}
				
				body.setLinearVelocity(velocity);				
			}
			
			
		}	
		
	}

	
}
