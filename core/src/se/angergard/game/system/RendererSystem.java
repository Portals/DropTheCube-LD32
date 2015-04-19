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
