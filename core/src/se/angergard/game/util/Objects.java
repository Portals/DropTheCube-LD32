package se.angergard.game.util;

import se.angergard.game.component.AStarComponent;
import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.DirectionalLightComponent;
import se.angergard.game.component.HealthComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.RemoveEntityTimerComponent;
import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.component.ScoreComponent;
import se.angergard.game.component.SpeedComponent;
import se.angergard.game.component.SpriteComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Objects {

	public static final SpriteBatch BATCH = new SpriteBatch();
	public static OrthographicCamera camera;
	public static World world;

	public static final ComponentMapper<ConeLightComponent> CONE_LIGHT_MAPPER = ComponentMapper.getFor(ConeLightComponent.class);
	public static final ComponentMapper<PointLightComponent> POINT_LIGHT_MAPPER = ComponentMapper.getFor(PointLightComponent.class);
	public static final ComponentMapper<DirectionalLightComponent> DIRECTIONAL_LIGHT_MAPPER = ComponentMapper.getFor(DirectionalLightComponent.class);
	public static final ComponentMapper<LightComponent> LIGHT_MAPPER = ComponentMapper.getFor(LightComponent.class);
	public static final ComponentMapper<SpriteComponent> SPRITE_MAPPER = ComponentMapper.getFor(SpriteComponent.class);
	public static final ComponentMapper<Box2DComponent> BOX2D_MAPPER = ComponentMapper.getFor(Box2DComponent.class);
	public static final ComponentMapper<RemoveFloorComponent> REMOVE_FLOOR_MAPPER = ComponentMapper.getFor(RemoveFloorComponent.class);
	public static final ComponentMapper<RemoveEntityTimerComponent> REMOVE_ENTITY_TIMER_MAPPER = ComponentMapper.getFor(RemoveEntityTimerComponent.class);;
	public static final ComponentMapper<AStarComponent> A_STAR_MAPPER = ComponentMapper.getFor(AStarComponent.class);
	public static final ComponentMapper<HealthComponent> HEALTH_MAPPER = ComponentMapper.getFor(HealthComponent.class);
	public static final ComponentMapper<ScoreComponent> SCORE_MAPPER = ComponentMapper.getFor(ScoreComponent.class);
	public static final ComponentMapper<SpeedComponent> SPEED_MAPPER = ComponentMapper.getFor(SpeedComponent.class);
	
}
