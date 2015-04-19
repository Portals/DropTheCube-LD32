package se.angergard.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Values {

	public static final float PIXELS_PER_METER = 30f;
	
	public static final int MAP_SIZE = 16;
	public static final int TILED_SIZE_PIXELS = 16;
	public static final int MAP_SIZE_PIXELS = MAP_SIZE * TILED_SIZE_PIXELS;
	public static final int MAPS = 4;
	
	public static final float CAMERA_ZOOM = 0.55f;
	
	public static final Vector2 GRAVITY = new Vector2(0, 0);

	public static final boolean SHADOWS = true;
	public static final Color AMBIENT_LIGHT = Color.WHITE;

	public static final float AMBIENT_LIGHT_BRIGHTNESS = 0.065f;

	public static final float REMOVE_FLOOR_TIME = 2.5f;

	public static final Color[] LIGHT_COLORS = new Color[]{
		new Color(1, 0, 0, 1.0f),
		new Color(0, 1, 0, 1.0f),
		new Color(0, 1, 1, 1.0f),
		new Color(0, 0, 1, 1.0f),
	};
	
	public static final float POINT_LIGHT_DEFAULT_MAX_DISTANCE = 10;

	public static final int POINT_LIGHT_DEFAULT_NUM_RAYS = 50;

	public static final int MAX_HEALTH = 3;

	public static final float ENEMY_START_COOLDOWN = .5f;
	
}
