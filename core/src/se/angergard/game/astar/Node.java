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


public class Node {

	public Node(Vector2i position, Node parent, double gCost, double hCost){
		this.position = position;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
	public Vector2i position; //Position of the tile.
	public Node parent; // The node were the "ai" came from, if null, parent == null
	public double fCost, //Total cost, gCost + hCost
				  gCost, //Sum of all previously Node-node cost.
				  hCost; // The direct cost to the finish line from this node.
	
	
	
}
