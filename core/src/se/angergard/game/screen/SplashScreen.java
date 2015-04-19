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
import se.angergard.game.tween.FloatTweener;
import se.angergard.game.tween.FloatValue;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen{
	
	public SplashScreen(MainGame game){
		this.game = game;
	}

	private MainGame game;
	
	private Sprite libgdx, ludumdare, portalprogramming;
	
	private FloatValue floatValue;
	private TweenManager manager;
	
	private float timer = 0;
	
	private SpriteBatch batch;
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		
		manager = new TweenManager();
		
		floatValue = new FloatValue();
		
		libgdx = new Sprite(new Texture(Gdx.files.internal("libgdxsplash.png")));
		ludumdare = new Sprite(new Texture(Gdx.files.internal("ludumdaresplash.png")));
		portalprogramming = new Sprite(new Texture(Gdx.files.internal("portalprogrammingsplash.png")));
	
		Tween.registerAccessor(FloatValue.class, new FloatTweener());
		
		Tween.to(floatValue, 0, 3.0f).target(1.0f).ease(Quad.INOUT).repeatYoyo(5, 0).setCallback(new TweenCallback(){

			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setScreen(new MainMenuScreen(game));
			}
			
		}).start(manager);
		
	}

	@Override
	public void render(float delta) {			
		manager.update(delta);
		
		timer += delta;
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if(timer <= 6){
			ludumdare.draw(batch, floatValue.value);
		}
		else if(timer <= 12){
			libgdx.draw(batch, floatValue.value);
		}
		else if(timer <= 18){
			portalprogramming.draw(batch, floatValue.value);
		}
		batch.end();
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
