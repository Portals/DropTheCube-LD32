package se.angergard.game.screen;

import se.angergard.game.MainGame;
import se.angergard.game.util.BasicGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BasicGUI implements Screen{

	public MainMenuScreen(MainGame game){
		this.game = game;
	}
	
	private MainGame game;
	
	@Override
	public void show() {
		super.init();
		
		TextButton play = new TextButton("Play", blueTextButtonStyle);
		TextButton help = new TextButton("Help", blueTextButtonStyle);
		TextButton exit = new TextButton("Exit", blueTextButtonStyle);
	
		play.addListener(getPlayListener());
		help.addListener(getHelpListener());
		exit.addListener(getExitListener());
		
		mainTable.add(play).pad(50).row();
		mainTable.add(help).pad(50).row();
		mainTable.add(exit).pad(50).row();
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
		stage.clear();
	}

	@Override
	public void dispose() {
		
	}

	private ClickListener getPlayListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new PlayScreen(game));
			}
		};
	}
	
	private ClickListener getHelpListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new HelpScreen(game));
			}
		};
	}
	
	private ClickListener getExitListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		};
	}
	
}
