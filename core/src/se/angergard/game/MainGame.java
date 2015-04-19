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
package se.angergard.game;

import se.angergard.game.screen.MainMenuScreen;
import se.angergard.game.screen.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MainGame extends Game {
	
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			setScreen(new MainMenuScreen(this));
		}
	}
}
