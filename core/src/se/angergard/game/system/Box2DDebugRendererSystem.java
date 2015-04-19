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

import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Box2DDebugRendererSystem extends EntitySystem implements Initializable{
	
	private Box2DDebugRenderer renderer;
	
	@Override
	public void init() {
		renderer = new Box2DDebugRenderer();
	}
	
	@Override
	public void update(float deltaTime) {
		renderer.render(Objects.world, Objects.camera.combined.cpy().scl(Values.PIXELS_PER_METER));
	}


	
}
