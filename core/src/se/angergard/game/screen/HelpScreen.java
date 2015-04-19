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

public class HelpScreen extends BasicGUI implements Screen{

	public HelpScreen(MainGame game){
		this.game = game;
	}
	
	private MainGame game;
	private Label label;
	
	@Override
	public void show() {
		super.init();
		
		TextButton back = new TextButton("Back", blueTextButtonStyle);
		
		back.addListener(getBackListener());
		
		label = new Label("", labelStyle);
		label.setText("Welcome to DropTheCube!\n" 
					 +" Walk with W-A-S-D,\n"
					 +"Place holes with arrowkeys.\n"
					 +" You can see where you\n"
					 +"can place holes with the\n"
					 +"four fake holes around you.\n"
					 +" You gain a point when a\n"
					 +"cube falls into a hole.\n"
					 +" You die if you fall into\n"
					 +"your own hole.\n"
					 +" You have three lives.\n"
					 +" When you get hit, the rest\n"
					 +"of the cubes disappear.\n"
					 +"To walk to the next level,\n"
					 +"go through one of the four\n"
					 +"doors. You can't go through\n"
					 +"the door you came from.\n"
					 +"Made by Theodor Angergard\n"
					 +"AKA Portals,\n"
					 +"AKA PortalProgramming\n"
					 +"Source at:\n"
					 +"github.com/Portals/\n"
					 +"DropTheCube-LD32");
		label.setFontScale(0.6f);
		
		mainTable.add(label).padBottom(10).row();
		mainTable.add(back).height(50);
	}
	
	private ClickListener getBackListener(){
		return new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
			}
		};
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
