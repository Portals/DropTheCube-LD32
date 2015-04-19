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
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Meters;
import se.angergard.game.util.Objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Box2DToSpritePositionSystem extends IteratingSystem implements Initializable{

	@SuppressWarnings("unchecked")
	public Box2DToSpritePositionSystem() {
		super(Family.getFor(SpriteComponent.class, Box2DComponent.class));
	}

	@Override
	public void init() {
		
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(entity);
		Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(entity);
	
		Sprite sprite = spriteComponent.sprite;
		Body body = box2DComponent.body;
		
		Vector2 bodyPosition = body.getPosition();
		
		float newX = Meters.toPixels(bodyPosition.x) - sprite.getWidth() / 2;
		float newY = Meters.toPixels(bodyPosition.y) - sprite.getHeight() / 2;
		
		sprite.setPosition(newX, newY);
	}

}
