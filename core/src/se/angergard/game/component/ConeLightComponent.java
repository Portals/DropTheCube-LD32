package se.angergard.game.component;

import se.angergard.game.system.Box2DLightsSystem;
import box2dLight.ConeLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class ConeLightComponent extends Component{
	public int numRays;
	public Color color;
	public float maxDistance;
	public float x;
	public float y;
	public float directionDegree;
	public float coneDegree;
	
	/**
	 * When @see {@link Box2DLightsSystem} has created the ConeLight, it will be here:
	 */
	public ConeLight coneLight;
}
