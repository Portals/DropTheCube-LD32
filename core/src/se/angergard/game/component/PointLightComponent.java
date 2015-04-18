package se.angergard.game.component;

import se.angergard.game.system.Box2DLightsSystem;
import box2dLight.PointLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class PointLightComponent extends Component{
	public int numRays;
	public Color color;
	public float maxDistance;
	public float x;
	public float y;
	
	/**
	 * When @see {@link Box2DLightsSystem} has created the PointLight, it will be here:
	 */
	public PointLight pointLight;
}
