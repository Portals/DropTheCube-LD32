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
