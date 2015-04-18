package se.angergard.game.desktop;

import se.angergard.game.MainGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 512;
		config.height = 512;
		config.resizable = false;
		new LwjglApplication(new MainGame(), config);
	}
}
