package se.angergard.game.component;

import se.angergard.game.astar.Node;
import se.angergard.game.util.Values;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;

public class AStarComponent extends Component{
	public ImmutableArray<Node> path;
	public float cooldown = Values.ENEMY_START_COOLDOWN;
}
