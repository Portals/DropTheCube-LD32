package se.angergard.game.system;

import se.angergard.game.astar.AStar;
import se.angergard.game.component.HoleComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.AshleyUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DrawPossibleFloorPlacementsSystem extends IntervalSystem implements Initializable{

	public DrawPossibleFloorPlacementsSystem() {
		super(.1f);
	}

	private Sprite playerSprite;
	private Texture texture;
	private boolean holeCreated = false;
	private Engine engine;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		playerSprite = Objects.SPRITE_MAPPER.get(engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).first()).sprite;
	}
	
	@Override
	public void init() {
		texture = new Texture(Gdx.files.internal("Hole.png"));
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(holeCreated){
			return;
		}
		
		Objects.BATCH.setProjectionMatrix(Objects.camera.combined);
		Objects.BATCH.setColor(new Color(0.0f, 0.0f, 1.0f, 0.6f));
		Objects.BATCH.begin();
		
		for(int i = 1; i < 8; i += 2){
			int x = (int) (((i % 3 - 1) * Values.TILED_SIZE_PIXELS * 2) + playerSprite.getX() + playerSprite.getWidth() / 2);
			int y = (int) (((i / 3 - 1) * Values.TILED_SIZE_PIXELS * 2) + playerSprite.getY() + playerSprite.getHeight() / 2);
			
			x /= Values.TILED_SIZE_PIXELS;
			y /= Values.TILED_SIZE_PIXELS;
			
			if(x < 0 || y < 0 || x >= Values.MAP_SIZE || y >= Values.MAP_SIZE){
				continue;
			}
			
			if(AStar.isSolid(x, y)){
				continue;
			}
			
			x *= Values.MAP_SIZE;
			y *= Values.MAP_SIZE;
			
			Objects.BATCH.draw(texture, x, y);
		}
		
		Objects.BATCH.end();
		Objects.BATCH.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
	}
	
	@Override
	protected void updateInterval() {
		holeCreated = AshleyUtils.entityWithComponentExist(engine, HoleComponent.class);
	}
}
