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
package se.angergard.game.component;

import se.angergard.game.system.Box2DLightsSystem;
import box2dLight.ConeLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class ConeLightComponent extends Component{
	public int numRays;
	public Color color;
	public float maxDistance;
	public float x;
	public float y;
	public float directionDegree;
	public float coneDegree;
	
	/**
	 * When @see {@link Box2DLightsSystem} has created the ConeLight, it will be here:
	 */
	public ConeLight coneLight;
}
