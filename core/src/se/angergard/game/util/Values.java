package se.angergard.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Values {

	public static final float PIXELS_PER_METER = 30f;
	
	public static final int MAP_SIZE = 16;
	public static final int TILED_SIZE_PIXELS = 16;
	public static final int MAP_SIZE_PIXELS = MAP_SIZE * TILED_SIZE_PIXELS;
	public static final int MAPS = 4;
	
	public static final float CAMERA_ZOOM = 1/2f;
	
	public static final Vector2 GRAVITY = new Vector2(0, 0);

	public static final boolean SHADOWS = true;
	public static final Color AMBIENT_LIGHT = Color.WHITE;

	public static final float AMBIENT_LIGHT_BRIGHTNESS = 0.5f;

	public static final float REMOVE_FLOOR_TIME = 3;
	
	
}
