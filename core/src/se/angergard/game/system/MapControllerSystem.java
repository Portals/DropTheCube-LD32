package se.angergard.game.system;

import java.util.Iterator;

import se.angergard.game.MainGame;
import se.angergard.game.astar.AStar;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.HealthComponent;
import se.angergard.game.component.HoleComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PlayerComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.ScoreComponent;
import se.angergard.game.component.SpriteComponent;
import se.angergard.game.enums.LightType;
import se.angergard.game.interfaces.Createable;
import se.angergard.game.interfaces.Initializable;
import se.angergard.game.screen.GameFinished;
import se.angergard.game.util.AshleyUtils;
import se.angergard.game.util.Box2DUtils;
import se.angergard.game.util.CameraSize;
import se.angergard.game.util.EntityUtils;
import se.angergard.game.util.Objects;
import se.angergard.game.util.RunnablePool;
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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class MapControllerSystem extends EntitySystem implements Initializable, Createable{
	
	public MapControllerSystem(MainGame game){ //Because MapControllerSystem checks alot of things
		this.game = game;
	}

	private MainGame game;
	private Engine engine;
	private Entity player;
	private TiledMap[] maps;
	private OrthogonalTiledMapRenderer mapRenderer;
	private boolean[][] solids;
	private ImmutableArray<Body> tiledMapBodies;
	private Array<Entity> pointLights;
	private Array<Entity> enemies;
	private RunnablePool runnablePool;
	private boolean alreadyHurted = false;

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
		
		runnablePool = new RunnablePool();
		
		Objects.world.setContactListener(new ContactListener(){

			@Override
			public void beginContact(final Contact contact) {
				runnablePool.add(new Runnable(){
					@Override
					public void run() {
						if(Objects.world.isLocked()){
							runnablePool.add(this);
							return;
						}
						
						if(contact == null){
							return;
						}
						
						if(contact.getFixtureA() == null || contact.getFixtureB() == null){
							return;
						}
						
						Entity e1 = (Entity) contact.getFixtureA().getUserData();
						Entity e2 = (Entity) contact.getFixtureB().getUserData();
						
						if(e1 == null || e2 == null){
							return;
						}
					}
				});
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
		loadMap(0, new Vector2(Values.TILED_SIZE_PIXELS * 2, CameraSize.getHeight() / 2));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		player = engine.getEntitiesFor(Family.getFor(PlayerComponent.class)).get(0); //Since there's only one player
	}
	
	public void update(float delta){
		checkCollision(); //Just because Box2D worked sometimes, and sometimes it didn't. I'm going to create my own Physics library after this. 
		
		SpriteComponent spriteComponent = Objects.SPRITE_MAPPER.get(player);
		Sprite sprite = spriteComponent.sprite;
		float xPos = sprite.getX();
		float yPos = sprite.getY();

		Vector2 newPosition = null;
		
		if(xPos <= 5){
			newPosition = new Vector2(CameraSize.getWidth() - Values.TILED_SIZE_PIXELS * 3, CameraSize.getHeight() / 2 - sprite.getHeight());
		}
		
		else if(yPos <= 5){
			newPosition = new Vector2(CameraSize.getWidth() / 2 - sprite.getWidth() / 2, CameraSize.getHeight() - Values.TILED_SIZE_PIXELS * 3);
		}
		
		else if(xPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS){
			newPosition = new Vector2(Values.TILED_SIZE_PIXELS * 2, CameraSize.getHeight() / 2 - sprite.getHeight() / 2);
		}
		
		else if(yPos >= Values.MAP_SIZE_PIXELS - Values.TILED_SIZE_PIXELS){
			newPosition = new Vector2(CameraSize.getWidth() / 2 - sprite.getWidth() / 2, Values.TILED_SIZE_PIXELS * 2);
		}
		
		final Vector2 newPosition2 = newPosition;
		
		if(newPosition != null){
			runnablePool.add(new Runnable(){
				@Override
				public void run() {
					if(!Objects.world.isLocked()){
						loadMap(MathUtils.random(0, maps.length - 1), newPosition2);				
					}
					else{
						runnablePool.add(this);
					}
				}
			});
		}
		
		mapRenderer.setView(Objects.camera);
		mapRenderer.render();
		
		runnablePool.run();
	}
		
	private void loadMap(int map, Vector2 newPosition){
		ScoreComponent scoreComponent = Objects.SCORE_MAPPER.get(player);
		scoreComponent.levels += 1;
		alreadyHurted = false;
		if(tiledMapBodies != null){//Map has been changed
			//Destroy old Player
			Box2DComponent playerPox2DComponent = Objects.BOX2D_MAPPER.get(player);
			Box2DUtils.destroyBody(playerPox2DComponent);
						
			//Destroy all old bodies
			for(int i = 0; i < tiledMapBodies.size(); i++){
				Box2DUtils.destroyBody(tiledMapBodies.get(i));
			}
			
			//Removes all old lights
			for(Entity pointLight : pointLights){
				Objects.POINT_LIGHT_MAPPER.get(pointLight).pointLight.remove();
			}

			if(enemies.size != 0){
				removeAllEnemies();				
			}

			if(AshleyUtils.entityWithComponentExist(engine, HoleComponent.class)){
				@SuppressWarnings("unchecked")
				Entity entity = engine.getEntitiesFor(Family.getFor(HoleComponent.class)).first();
				
				Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(entity);
				Box2DUtils.destroyBody(box2DComponent);
				
				engine.removeEntity(entity);
			}
									
			pointLights.clear();
			
			Objects.world = new World(new Vector2(0, 0), false);
			
			SpriteComponent playerSpriteComponent = Objects.SPRITE_MAPPER.get(player);
			playerSpriteComponent.sprite.setPosition(newPosition.x, newPosition.y);
			
			Box2DComponent newPlayerBox2DComponent = Box2DUtils.create(playerSpriteComponent.sprite, BodyType.DynamicBody);
			
			playerPox2DComponent.body = newPlayerBox2DComponent.body;
			playerPox2DComponent.fixture = newPlayerBox2DComponent.fixture;
		}
		
		initSolidTiles(maps[map]);
		tiledMapBodies = Box2DUtils.create(maps[map]);
		createLights(maps[map], map);	
		mapRenderer.setMap(maps[map]);
		spawnEnemies(maps[map], scoreComponent.levels);
	}
	
	private void spawnEnemies(TiledMap tiledMap, int level) {
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

	private void checkCollision(){
		//Checks player and enemies
		
		ScoreComponent scoreComponent = Objects.SCORE_MAPPER.get(player);
		SpriteComponent playerSpriteComponent = Objects.SPRITE_MAPPER.get(player);
		Rectangle playerRectangle = playerSpriteComponent.sprite.getBoundingRectangle();
		playerRectangle.set(playerRectangle.x - 1, playerRectangle.y - 1, playerRectangle.width + 2, playerRectangle.height + 2);
		
		for(Entity enemy : enemies){
			SpriteComponent enemySpriteComponent = Objects.SPRITE_MAPPER.get(enemy);
			Sprite enemySprite = enemySpriteComponent.sprite;
			
			//Checks if the player has touched any enemy
			if(!alreadyHurted && Intersector.overlaps(playerRectangle, enemySprite.getBoundingRectangle())){
				alreadyHurted = true;
				HealthComponent healthComponent = Objects.HEALTH_MAPPER.get(player);
				healthComponent.health.hurt(1);
				if(healthComponent.health.isDead()){
					game.setScreen(new GameFinished(game, "You died,\nScore:")); //TODO: Add score
				}
				else{
					removeAllEnemies();
				}
			}
		}

		
		@SuppressWarnings("unchecked")
		ImmutableArray<Entity> potentiallyHoleArray = engine.getEntitiesFor(Family.getFor(HoleComponent.class));
		
		if(potentiallyHoleArray.size() == 0){
			return;
		}
		
		Entity hole = potentiallyHoleArray.first();
		
		SpriteComponent holeSpriteComponent = Objects.SPRITE_MAPPER.get(hole);
		Sprite holeSprite = holeSpriteComponent.sprite;
		
		Circle circle = new Circle(holeSprite.getX() + holeSprite.getWidth() / 2, holeSprite.getY() + holeSprite.getHeight() / 2, holeSprite.getWidth() / 2);
		
		Array<Entity> enemiesToRemove = new Array<Entity>();

		for(Entity enemy : enemies){
			SpriteComponent enemySpriteComponent = Objects.SPRITE_MAPPER.get(enemy);
			Sprite enemySprite = enemySpriteComponent.sprite;
			
			if(Intersector.overlaps(circle, enemySprite.getBoundingRectangle())){
				Box2DComponent enemyBox2DComponent = Objects.BOX2D_MAPPER.get(enemy);
				engine.removeEntity(enemy);
				Box2DUtils.destroyBody(enemyBox2DComponent);
				enemiesToRemove.add(enemy);
				scoreComponent.score += 1;
			}
		}
		
		for(Entity enemyToRemove : enemiesToRemove){
			enemies.removeValue(enemyToRemove, true);
		}
		
		enemiesToRemove.clear();
		
		//Checks if player fell in the hole
		
		playerRectangle = playerSpriteComponent.sprite.getBoundingRectangle(); //Recreates it to be bigger, or else collision is weird;
		playerRectangle.set(playerRectangle.x + 6, playerRectangle.y + 6, playerRectangle.width - 12, playerRectangle.height - 12);
		
		if(Intersector.overlaps(circle, playerRectangle)){
			game.setScreen(new GameFinished(game, "You fell into \nyour own hole.\nNo score for you..."));
		}
	}
	
	private void removeAllEnemies(){
		runnablePool.add(new Runnable(){
			@Override
			public void run() {
				if(Objects.world.isLocked()){
					runnablePool.add(this);
					return;
				}
				//Removes all enemies
				for(Entity enemy : enemies){
					Box2DComponent box2DComponent = Objects.BOX2D_MAPPER.get(enemy);
					Box2DUtils.destroyBody(box2DComponent);
					
					engine.removeEntity(enemy);
				}
				enemies.clear();
			}
		});

	}
	
}

