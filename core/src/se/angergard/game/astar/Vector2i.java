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
package se.angergard.game.astar;

public class Vector2i {

	public Vector2i(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int x, y;
	
	public double length(Vector2i otherVector){
		double dx = x - otherVector.x;
		double dy = y - otherVector.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public boolean equals(Vector2i v){
		if(v == null) return false;
		if(x == v.x && y == v.y)
			return true;
	
		else{
			return false;
		}
	}
	
}
