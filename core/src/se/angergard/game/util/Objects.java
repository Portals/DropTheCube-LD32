package se.angergard.game.util;

import se.angergard.game.component.Box2DComponent;
import se.angergard.game.component.ConeLightComponent;
import se.angergard.game.component.DirectionalLightComponent;
import se.angergard.game.component.LightComponent;
import se.angergard.game.component.PointLightComponent;
import se.angergard.game.component.RemoveEntityTimerComponent;
import se.angergard.game.component.RemoveFloorComponent;
import se.angergard.game.component.SpriteComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;

public class Objects {

	public static final OrthographicCamera CAMERA = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	public static final World WORLD = new World(Values.GRAVITY, false);

	public static final ComponentMapper<ConeLightComponent> CONE_LIGHT_MAPPER = ComponentMapper.getFor(ConeLightComponent.class);
	public static final ComponentMapper<PointLightComponent> POINT_LIGHT_MAPPER = ComponentMapper.getFor(PointLightComponent.class);
	public static final ComponentMapper<DirectionalLightComponent> DIRECTIONAL_LIGHT_MAPPER = ComponentMapper.getFor(DirectionalLightComponent.class);
	public static final ComponentMapper<LightComponent> LIGHT_MAPPER = ComponentMapper.getFor(LightComponent.class);
	public static final ComponentMapper<SpriteComponent> SPRITE_MAPPER = ComponentMapper.getFor(SpriteComponent.class);
	public static final ComponentMapper<Box2DComponent> BOX2D_MAPPER = ComponentMapper.getFor(Box2DComponent.class);
	public static final ComponentMapper<RemoveFloorComponent> REMOVE_FLOOR_MAPPER = ComponentMapper.getFor(RemoveFloorComponent.class);
	public static final ComponentMapper<RemoveEntityTimerComponent> REMOVE_ENTITY_TIMER_MAPPER = ComponentMapper.getFor(RemoveEntityTimerComponent.class);;
	
}
