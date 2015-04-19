package se.angergard.game.component;

import se.angergard.game.Health;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class HealthComponent extends Component{
	public Health health;
	public Texture heartTexture;
}
