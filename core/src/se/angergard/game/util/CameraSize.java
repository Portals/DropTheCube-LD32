package se.angergard.game.util;

public class CameraSize {
	public static final float getWidth(){
		return Objects.camera.viewportWidth * Objects.camera.zoom;
	}
	
	public static final float getHeight(){
		return Objects.camera.viewportHeight * Objects.camera.zoom;
	}
}
