package se.angergard.game;

import se.angergard.game.screen.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MainGame extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			setScreen(new MainMenuScreen(this));
		}
	}
}
