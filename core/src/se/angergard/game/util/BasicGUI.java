package se.angergard.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class BasicGUI {
	
	protected Stage stage;
	protected Table mainTable;
	
	protected BitmapFont font;
	protected TextButtonStyle blueTextButtonStyle;
	
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
		blueTextButtonStyle.fontColor = Color.WHITE;
	}
	
	public void render(float delta){
		stage.act(delta);
		stage.draw();
	}
	
}
