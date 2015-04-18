package se.angergard.game.component;

import se.angergard.game.astar.Node;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;

public class AStarComponent extends Component{
	public ImmutableArray<Node> path;
}
