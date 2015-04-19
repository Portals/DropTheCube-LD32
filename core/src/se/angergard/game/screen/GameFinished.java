/*******************************************************************************
 * Copyright 2015 Theodor Angergård
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
