package se.angergard.game.screen;

import se.angergard.game.MainGame;
import se.angergard.game.util.BasicGUI;

import com.badlogic.gdx.Screen;

public class HelpScreen extends BasicGUI implements Screen{

	public HelpScreen(MainGame game){
		this.game = game;
	}
	
	private MainGame game;
	
	@Override
	public void show() {
		super.init();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
