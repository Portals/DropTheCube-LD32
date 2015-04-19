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
package se.angergard.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class BasicGUI {
	
	protected Stage stage;
	protected Table mainTable;
	
	protected BitmapFont font;
	protected TextButtonStyle blueTextButtonStyle;
	protected LabelStyle labelStyle;
	
	public void init(){
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		mainTable = new Table();
		mainTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage.addActor(mainTable);
		
		font = new BitmapFont(Gdx.files.internal("font2.fnt"));
		
		blueTextButtonStyle = new TextButtonStyle();
		blueTextButtonStyle.up = TextureUtils.createDrawable(new Color(52f / 255f, 73f / 255f, 94f / 255f, 1.0f), 300, 75);
		blueTextButtonStyle.down = TextureUtils.createDrawable(new Color(44f / 255f, 62f / 255f, 80f / 255f, 1.0f), 300, 75);
		blueTextButtonStyle.over = TextureUtils.createDrawable(new Color(60f / 255f, 84f / 255f, 108f / 255f, 1.0f), 300, 75);
		blueTextButtonStyle.font = font;
		blueTextButtonStyle.fontColor = Color.BLACK;
		
		labelStyle = new LabelStyle();
		labelStyle.background = blueTextButtonStyle.up;
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;
	}
	
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
}
