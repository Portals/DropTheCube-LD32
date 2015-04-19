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
import se.angergard.game.component.HoleComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.AshleyUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerSystem extends IntervalSystem implements Initializable{

	public PlayerSystem() {
		super(.1f);
	}

	private Entity player;
	private Engine engine;
	private boolean holeCreated = false;
	
	@Override
	public void init() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0);
		this.engine = engine;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(player);
		Body body = box2DComponent.body;
		
		float speed = 110f * deltaTime;
		Vector2 velocity = new Vector2();
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			velocity.y += speed;
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			velocity.x -= speed;
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			velocity.y -= speed;
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			velocity.x += speed;
		}
		body.setLinearVelocity(velocity);
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			sendRemoveFloorEntity(new Vector2(-Values.TILED_SIZE_PIXELS * 2, 0));
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			sendRemoveFloorEntity(new Vector2(Values.TILED_SIZE_PIXELS * 2, 0));	
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			sendRemoveFloorEntity(new Vector2(0, Values.TILED_SIZE_PIXELS * 2));
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			sendRemoveFloorEntity(new Vector2(0, -Values.TILED_SIZE_PIXELS * 2));
		}
	}
	
	private void sendRemoveFloorEntity(Vector2 tileChange){
		if(holeCreated){
			return;
		}
		holeCreated = true;
		
		Sprite sprite = Objects.SPRITE_MAPPER.get(player).sprite;
		Vector2 playerPosition = new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
		
		Entity entity = new Entity();
		
		RemoveFloorComponent removeFloorComponent = new RemoveFloorComponent();
		removeFloorComponent.playerPosition = playerPosition.add(tileChange);
				
		entity.add(removeFloorComponent);
		
		engine.addEntity(entity);
	}

	@Override
	protected void updateInterval() {
		holeCreated = AshleyUtils.entityWithComponentExist(engine, HoleComponent.class);
	}

	
}
