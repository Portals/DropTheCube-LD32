package se.angergard.game.util;

public class CameraSize {
	public static final float getWidth(){
		return Objects.CAMERA.viewportWidth * Objects.CAMERA.zoom;
	}
	
	public static final float getHeight(){
		return Objects.CAMERA.viewportHeight * Objects.CAMERA.zoom;
	}
}
