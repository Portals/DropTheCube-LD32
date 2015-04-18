package se.angergard.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Box2DComponent extends Component{
	public Body body;
	public Fixture fixture;
}
