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

import se.angergard.game.astar.AStar;
import se.angergard.game.astar.Node;
import se.angergard.game.astar.Vector2i;
import se.angergard.game.component.AStarComponent;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.SpeedComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
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
				AStarComponent aiStarComponent = Objects.A_STAR_MAPPER.get(entity);
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
		for(Entity entity : entities){
			AStarComponent aStarComponent = Objects.A_STAR_MAPPER.get(entity);
			aStarComponent.cooldown -= deltaTime;
		}
	}

	
	@Override
	protected void updateInterval() {		
		for(Entity entity : entities){
			AStarComponent aStarComponent = Objects.A_STAR_MAPPER.get(entity);
			if(aStarComponent.cooldown > 0){
				continue;
			}
			
			SpriteComponent playerSpriteComponent = Objects.SPRITE_MAPPER.get(player);
			int xPlayer = (int)(playerSpriteComponent.sprite.getX() + playerSpriteComponent.sprite.getWidth() / 2);
			int yPlayer = (int)(playerSpriteComponent.sprite.getY() + playerSpriteComponent.sprite.getHeight() / 2);
			
			SpriteComponent enemySpriteComponent = Objects.SPRITE_MAPPER.get(entity);
			int xEnemy = (int)(enemySpriteComponent.sprite.getX() + enemySpriteComponent.sprite.getWidth() / 2);
			int yEnemy = (int)(enemySpriteComponent.sprite.getY() + enemySpriteComponent.sprite.getHeight() / 2);
			
			Vector2i start = new Vector2i(xEnemy / Values.TILED_SIZE_PIXELS, yEnemy / Values.TILED_SIZE_PIXELS);
			Vector2i goal = new Vector2i(xPlayer / Values.TILED_SIZE_PIXELS, yPlayer / Values.TILED_SIZE_PIXELS);

			ImmutableArray<Node> path = AStar.findPath(start, goal);;
			aStarComponent.path = path;
			
			if(this.pathArray.size != 0){
				for(Entity e : pathArray){
					engine.removeEntity(e);
				}
				pathArray.clear();
			}
			
			if(path == null) continue;
			
			if(!(path.size() > 0)) {
				continue;
			}
			
			SpeedComponent speedComponent = Objects.SPEED_MAPPER.get(entity);
			float speed = speedComponent.speed;
			
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
