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
