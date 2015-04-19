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
