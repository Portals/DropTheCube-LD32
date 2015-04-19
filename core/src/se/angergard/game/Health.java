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

package se.angergard.game;

public class Health {

	public Health(int health){
		this.health = START_HEALTH = health;
	}
	
	private int health;
	private final int START_HEALTH;
	
	public void hurt(int damage){
		health -= damage;
	}
	
	public boolean isDead(){
		return health <= 0;
	}
	
	public void reset(){
		health = START_HEALTH;
	}
	
	public int getHealth(){
		return health;
	}
	
}
