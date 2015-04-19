package se.angergard.game.system;

import java.util.Iterator;

import se.angergard.game.astar.AStar;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.EntityUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.Pixels;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MapControllerSystem extends EntitySystem implements Initializable, Createable{

	private Engine engine;
	private Entity player;
	private TiledMap[] maps;
	private OrthogonalTiledMapRenderer mapRenderer;
	private boolean[][] solids;
	private ImmutableArray<Body> tiledMapBodies;
	private Array<Entity> pointLights;
	private Array<Entity> enemies;

	@Override
	public void init() {
		maps = new TiledMap[Values.MAPS];
		
		TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
		
		for(int i = 0; i < Values.MAPS; i++){
			maps[i] = mapLoader.load("smallmaps/smallmap" + i + ".tmx");
		}
		
		solids = new boolean[Values.MAP_SIZE][Values.MAP_SIZE];
		AStar.setSolids(solids);
		
		mapRenderer = new OrthogonalTiledMapRenderer(maps[3]);
		
		pointLights = new Array<Entity>();
		enemies = new Array<Entity>();
		
		
		Objects.WORLD.setContactListener(new ContactListener(){

			@Override
			public void beginContact(Contact contact) {
				contact.getFixtureA();
				contact.getFixtureB();
			}

			@Override
			public void endContact(Contact contact) {
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
			}
			
		});
	}
	
	@Override
	public void create() {
		loadMap(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0); //Since there's only one player
	}
	
	public void update(float delta){
		Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(player);
		Body body = box2DComponent.body;
		
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(player);
		Sprite sprite = spriteComponent.sprite;
		float xPos = sprite.getX();
		float yPos = sprite.getY();

		if(xPos <= 5){
			body.setTransform(Pixels.toMeters(CameraSize.getWidth() - Values.TILED_SIZE_PIXELS * 2), Pixels.toMeters(CameraSize.getHeight() / 2), 0);
			loadMap(MathUtils.random(0, maps.length - 1));
		}
		
		else if(yPos <= 5){
			body.setTransform(Pixels.toMeters(CameraSize.getWidth() / 2), Pixels.toMeters(CameraSize.getHeight() - Values.TILED_SIZE_PIXELS * 2), 0);
			loadMap(MathUtils.random(0, maps.length - 1));		
		}
		
		else if(xPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS){
			body.setTransform(Pixels.toMeters(Values.TILED_SIZE_PIXELS * 2), Pixels.toMeters(CameraSize.getHeight() / 2), 0);
			loadMap(MathUtils.random(0, maps.length - 1));		
		}
		
		else if(yPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS){
			body.setTransform(Pixels.toMeters(CameraSize.getWidth() / 2), Pixels.toMeters(Values.TILED_SIZE_PIXELS * 2), 0);
			loadMap(MathUtils.random(0, maps.length - 1));			
		}
		
		mapRenderer.setView(Objects.CAMERA);
		mapRenderer.render();
	}
		
	private void loadMap(int map){
		if(tiledMapBodies != null){//Map has been changed
			//Destroy all old bodies
			for(int i = 0; i < tiledMapBodies.size(); i++){
				Objects.WORLD.destroyBody(tiledMapBodies.get(i));
			}
			
			//Removes all old lights
			for(Entity pointLight : pointLights){
				Objects.POINT_LIGHT_MAPPER.get(pointLight).pointLight.remove();
			}
			
			//Removes all enemies
			for(Entity enemy : enemies){
				Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(enemy);
				Objects.WORLD.destroyBody(box2DComponent.body);
				
				engine.removeEntity(enemy);
			}
			
			pointLights.clear();
			enemies.clear();
		}
		
		initSolidTiles(maps[map]);
		tiledMapBodies = Box2DUtils.create(maps[map]);
		createLights(maps[map], map);	
		mapRenderer.setMap(maps[map]);
		spawnEnemies(maps[map]);
	}
	
	private void spawnEnemies(TiledMap tiledMap) {
		Array<Vector2> spawnpoints = new Array<Vector2>();
		
		MapLayer layer = tiledMap.getLayers().get("EnemySpawnPoints");
		
		Iterator<MapObject> it = layer.getObjects().iterator();
		while(it.hasNext()){
			MapObject object = it.next();
			EllipseMapObject ellipseMapObject = (EllipseMapObject) object;
			Ellipse ellipse = ellipseMapObject.getEllipse();
			
			spawnpoints.add(new Vector2(ellipse.x + ellipse.width / 2, ellipse.y + ellipse.height / 2));
		}
		
		Vector2 vec = spawnpoints.get(MathUtils.random(0, spawnpoints.size - 1));
		
		Entity entity = EntityUtils.createEnemyAStar(vec.x, vec.y);
		enemies.add(entity);
		
		engine.addEntity(entity);
	}

	private void initSolidTiles(TiledMap map){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("CollisionTiles");
		
		for(int x = 0; x < layer.getWidth(); x++){
			for(int y = 0; y < layer.getHeight(); y++){
				Cell cell = layer.getCell(x, y);
				if(cell == null){
					solids[x][y] = false;
					continue;
				}
				if(cell.getTile() == null){
					solids[x][y] = false;
					continue;
				}
				else{
					solids[x][y] = true;
				}
			}
		}
	}
	
	
	private void createLights(TiledMap tiledMap, int map) {
		MapLayer layer = tiledMap.getLayers().get("PointLights");
		
		Iterator<MapObject> it = layer.getObjects().iterator();
		while(it.hasNext()){
			MapObject object = it.next();
			//Always going to be a circle
			EllipseMapObject ellipse = (EllipseMapObject) object;
			
			Entity entity = new Entity();
			
			PointLightComponent pointComponent = new PointLightComponent();
			pointComponent.color = Values.LIGHT_COLORS[map];
			pointComponent.maxDistance = ellipse.getEllipse().area() / 25;
			pointComponent.numRays = Values.POINT_LIGHT_DEFAULT_NUM_RAYS;
			pointComponent.x = ellipse.getEllipse().x + ellipse.getEllipse().width / 2;
			pointComponent.y = ellipse.getEllipse().y + ellipse.getEllipse().height / 2;
			
			LightComponent lightComponent = new LightComponent();
			lightComponent.lightType = LightType.PointLight;
			
			entity.add(pointComponent);
			entity.add(lightComponent);
			
			engine.addEntity(entity);
			
			pointLights.add(entity);
		}
		
	}

	
}

