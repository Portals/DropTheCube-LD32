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
