package se.angergard.game.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public class AStar {

	private static boolean[][] solids;

	private static Comparator<Node> nodeSorter = new Comparator<Node>(){
		@Override
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost){
				return 1;
			}
			else if(n1.fCost > n0.fCost){
				return -1;
			}
			return 0;
		}
	};

	public static void setSolids(boolean[][] solids){
		AStar.solids = solids;
	}
	
	public static ImmutableArray<Node> findPath(Vector2i start, Vector2i goal){
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node > closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, start.length(goal));
		openList.add(current);
		
		while (openList.size() > 0){
			Collections.sort(openList, nodeSorter);
			
			current = openList.get(0); //The one with the current lowest cost
			if(current.position.equals(goal)){
				Array<Node> path = new Array<Node>();
				while(current.parent != null){
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return new ImmutableArray<Node>(path);
			}
			
			openList.remove(current);
			closedList.add(current);
			
			for (int i = 0; i < 9; i++){
				if(i == 4){ //Middle
					continue;
				}
				int x = current.position.x;
				int y = current.position.y;
				
				int xi = (i % 3) - 1; //Either -1, 0, 1
				int yi = (i / 3) - 1; //Same ^
				
				if(x + xi < 0 || y + yi < 0){
					continue;
				}
				
				if(x + xi >= 16 || y + yi >= 16){
					continue;
				}
				
				if(solids[x + xi][y + yi]){
					continue;
				}
				
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getSpecialDistance(current.position, a);
				double hCost = a.length(goal);
				Node node = new Node(a, current, gCost, hCost);
				
				if(vecInList(closedList, a) && gCost >= node.gCost){
					continue;
				}
				if(!vecInList(openList, a) || gCost < node.gCost){
					openList.add(node);
				}
			}
			
		}
				
		return null;
	}
	
	private static double getSpecialDistance(Vector2i a, Vector2i b){
		return a.length(b) == 1 ? 1 : .95;
	}
	
	private static boolean vecInList(ArrayList<Node> list, Vector2i vector){
		for(Node n : list){
			if(n.position.equals(vector)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSolid(int x, int y){
		if(x < 0){
			return true;
		}
		if(y < 0){
			return true;
		}
		if(x >= 16){
			return true;
		}
		if(y >= 16){
			return true;
		}
		return solids[x][y];
	}
	
}
