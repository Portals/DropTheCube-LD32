package se.angergard.game.util;

import com.badlogic.gdx.utils.Array;

public class RunnablePool {
	
	public RunnablePool(){
		runnables = new Array<Runnable>();
	}

	private Array<Runnable> runnables;
	
	public void add(Runnable runnable){
		runnables.add(runnable);
	}
	
	public void run(){
		for(Runnable runnable : runnables){
			runnable.run();
		}
		runnables.clear();
	}
}
