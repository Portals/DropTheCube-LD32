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
package se.angergard.game.util;

import com.badlogic.gdx.math.Vector2;

public class Pixels {
	
	public static final float toMeters(float pixels){
		return pixels / Values.PIXELS_PER_METER;
	}
	
	
	public static final Vector2 toMeters(Vector2 pixels){
		return new Vector2(pixels.x / Values.PIXELS_PER_METER, pixels.y / Values.PIXELS_PER_METER);
	}
}
