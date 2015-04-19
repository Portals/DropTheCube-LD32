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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
		
		Label label = new Label("   DropTheCube", labelStyle);
		
		TextButton play = new TextButton("Play", blueTextButtonStyle);
		TextButton help = new TextButton("Help", blueTextButtonStyle);
		TextButton exit = new TextButton("Exit", blueTextButtonStyle);
	
		play.addListener(getPlayListener());
		help.addListener(getHelpListener());
		exit.addListener(getExitListener());
		
		mainTable.add(label).pad(20).width(400).height(50).row();
		mainTable.add(play).pad(20).width(400).height(50).row();
		mainTable.add(help).pad(20).width(400).height(50).row();
		mainTable.add(exit).pad(20).width(400).height(50).row();
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
