package se.angergard.game.screen;

import se.angergard.game.MainGame;
import se.angergard.game.util.BasicGUI;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameFinished extends BasicGUI implements Screen{

	/**
	 * 
	 * @param game
	 * @param gameFinishType
	 * @param data For example how many levels the player did and
	 */
	public GameFinished(MainGame game, String data){
		this.game = game;
		this.data = data;
	}
	
	private MainGame game;
	private String data;

	@Override
	public void show() {
		super.init();
		TextButton mainMenu = new TextButton("MainMenu", blueTextButtonStyle);
		TextButton playAgain = new TextButton("Play Again", blueTextButtonStyle);
		
		mainMenu.addListener(getMainMenuListener());
		playAgain.addListener(getPlayAgainListener());
		
		Label label = new Label(data, labelStyle);
		
		mainTable.add(label).colspan(2).padBottom(75).row();
		mainTable.add(playAgain).width(400).height(50).padBottom(20).row();
		mainTable.add(mainMenu).width(400).height(50);

	}
	
	private ClickListener getPlayAgainListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new PlayScreen(game));
			}
		};
	}
	
	private ClickListener getMainMenuListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
			}
		};
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
