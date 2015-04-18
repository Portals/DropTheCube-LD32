package se.angergard.game.util;

import com.badlogic.gdx.math.Vector2;

public class Meters {

	public static final float toPixels(float meters){
		return meters * Values.PIXELS_PER_METER;
	}
	
	public static final Vector2 toPixels(Vector2 meters){
		return new Vector2(meters.x * Values.PIXELS_PER_METER, meters.y * Values.PIXELS_PER_METER);
	}
	
}
