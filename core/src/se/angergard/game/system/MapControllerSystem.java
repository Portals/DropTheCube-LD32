package se.angergard.game.system;

import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapControllerSystem extends EntitySystem {
	
	public MapControllerSystem(){
		maps = new TiledMap[Values.MAPS];
		
		TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
		
		for(int i = 0; i < Values.MAPS; i++){
			maps[i] = mapLoader.load("smallmaps/smallmap" + i + ".tmx");
			
		}
		Box2DUtils.create(maps[0]);
		
		mapRenderer = new OrthogonalTiledMapRenderer(maps[0]);
	}
	
	private Entity player;
	private TiledMap[] maps;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0); //Since there's only one player
	}
	
	public void update(float delta){
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(player);
		Sprite sprite = spriteComponent.sprite;
		float xPos = sprite.getX();
		float yPos = sprite.getY();
				
//		if(xPos < Values.TILED_SIZE_PIXELS|| yPos < Values.TILED_SIZE_PIXELS || xPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS || yPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS){
//			mapRenderer.setMap(maps[MathUtils.random(0, maps.length - 1)]);
//		}	
		
		mapRenderer.setView(Objects.CAMERA);
		mapRenderer.render();
	}
	
	
//	if(xPos < 1){
//		
//	}
//	
//	if(yPos < 1){
//		
//	}
//	
//	if(xPos >= Values.MAP_SIZE - 1){
//		
//	}
//	
//	if(yPos >= Values.MAP_SIZE - 1){
//		
//	}
	
}
