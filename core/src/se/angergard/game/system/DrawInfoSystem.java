package se.angergard.game.system;

import se.angergard.game.Health;
import se.angergard.game.component.HealthComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.ScoreComponent;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.Objects;
import se.angergard.game.util.TextureUtils;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class DrawInfoSystem extends EntitySystem implements Initializable, Createable{

	public DrawInfoSystem(){}
		
	private Sprite background;
	private Label label;
	private ScoreComponent scoreComponent;
	private HealthComponent healthComponent;
	private Engine engine;

	@Override
	public void update(float deltaTime) {
		Objects.BATCH.setProjectionMatrix(Objects.camera.combined);
		Objects.BATCH.begin();
		
		background.setSize(CameraSize.getWidth(), 26);
		background.setPosition(0, 256);
		background.draw(Objects.BATCH);
		
		background.setSize(26, CameraSize.getHeight() - 26);
		background.setPosition(256, 0);
		background.draw(Objects.BATCH);
		
		Health health = healthComponent.health;
		Texture heartTexture = healthComponent.heartTexture;
		float scl = 2.8f;
		
		for(int i = 0; i < health.getHealth(); i++){
			Objects.BATCH.draw(heartTexture, i * heartTexture.getWidth() * scl + 1, CameraSize.getHeight() - heartTexture.getHeight() * scl - 2, heartTexture.getWidth() * scl, heartTexture.getHeight() * scl);
		}
		
		label.setFontScale(0.5f);
		label.setPosition(Values.MAX_HEALTH * heartTexture.getWidth() * scl + 5, CameraSize.getHeight() - heartTexture.getHeight() * scl / 2);
		label.setText("Score:" + scoreComponent.score + ", Level:" + scoreComponent.levels);
		label.draw(Objects.BATCH, 1);
		
		Objects.BATCH.end();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		Entity player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first();
		healthComponent = Objects.HEALTH_MAPPER.get(player);
		scoreComponent = Objects.SCORE_MAPPER.get(player);
	}
	
	public void create() {
		background = new Sprite(TextureUtils.createTexture(new Color(52f / 255f, 73f / 255f, 94f / 255f, 1.0f), 1, 1));
		
		BitmapFont font = new BitmapFont(Gdx.files.internal("font2.fnt"));

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);

		label = new Label("", labelStyle);
	}
	
}
