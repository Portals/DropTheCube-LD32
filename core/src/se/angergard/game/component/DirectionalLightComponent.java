package se.angergard.game.component;

import se.angergard.game.system.Box2DLightsSystem;
import box2dLight.DirectionalLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class DirectionalLightComponent extends Component{
	public int numRays;
	public Color color;
	public float directionDegree;
	
	/**
	 * When @see {@link Box2DLightsSystem} has created the DirectionalLight, it will be here:
	 */
	public DirectionalLight directionalLight;
}
