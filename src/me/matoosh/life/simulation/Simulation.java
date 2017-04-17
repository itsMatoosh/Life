package me.matoosh.life.simulation;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import me.matoosh.life.ui.AppFrame;

/**
 * Represents a single simulation.
 * @author Mateusz Rêbacz
 *
 */
public class Simulation {
	/**
	 * The settings of this simulation.
	 */
	public SimulationSettings settings;
	
	/**
	 * The current state of the array.
	 */
	public ArrayList<Cell> state = new ArrayList<Cell>();
	/**
	 * Whether the simulation has been paused.
	 */
	public boolean isPaused = false;
	/**
	 * The current bounds of the simulation.
	 */
	public SimulationBounds currentBounds;
	
	/**
	 * Scheduler of this simulation.
	 */
	private final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> simulationHandle;
	
	/**
	 * Creates a new simulation.
	 * @param settings
	 */
	public Simulation(SimulationSettings settings) {
		this.settings = settings;
		this.currentBounds = new SimulationBounds();
	}
	
	/**
	 * Starts or continues the simulation.
	 */
	public void start() {
	    if(isPaused) {
	    	isPaused = false;
	    	return;
	    }
		
	    //Caching neighbors.
	    for(int i = 0; i < state.size(); i++) {
	    	state.get(i).cacheNeighbors(this);
	    }
	    
		//Creating the handle.
		simulationHandle = scheduler.scheduleAtFixedRate(new SimulationLogic(this), 170, 170, TimeUnit.MILLISECONDS);
	}
	/**
	 * Pauses the simulation.
	 */
	public void pause() {
		isPaused = true;
	}
	/**
	 * Stops the simulation.
	 */
	public void stop() {
		if(simulationHandle != null) {
			simulationHandle.cancel(true);
			state.clear();
			currentBounds = null;	
		}
	}
	/**
	 * Returns the cell at the specified point.
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell getCellAt(int x, int y) {
		Cell found = null;
		for(Cell c : state) {
			if(c.posX == x && c.posY == y) {
				found = c;
			}
		}
		//Creating the missing, dead cell.
		if(found == null && currentBounds.isInBounds(x, y)) {
			found = new Cell(x, y, this);
			state.add(found);
		}
		
		return found;
	}
	/**
	 * Creates a cell at x and y and expands bounds if needed.
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell createCellAt(int x, int y) {
		if(currentBounds != null && !currentBounds.isInBounds(x, y)) {
			expandBounds(x, y);
		}
		
		Cell found = getCellAt(x,y);
		found.cacheNeighbors(this);
		return found;
	}
	/**
	 * Expands the bounds of the simulation to fit the specified point.
	 * @param x
	 * @param y
	 */
	private void expandBounds(int x, int y) {
		if(currentBounds.maxX == 0 || currentBounds.maxY == 0 || currentBounds.minX == 0 || currentBounds.minY == 0) {
			currentBounds.maxX = x + 5;
			currentBounds.minX = x - 5;
			currentBounds.maxY = y + 5;
			currentBounds.minY = y - 5;
		}
		if(x >= currentBounds.maxX) {
			currentBounds.maxX = x + 5;
		} else if(x <= currentBounds.minX) {
			currentBounds.minX = x - 5;
		}
		if(y >= currentBounds.maxY) {
			currentBounds.maxY = y + 5; 
		} else if(y <= currentBounds.minY) {
			currentBounds.minY = y - 5;
		}
		
		System.out.println("New bounds: " + currentBounds.minX + "," + currentBounds.minY + " - " + currentBounds.maxX + "," + currentBounds.maxY);
	}
	
	/**
	 * Threadable simulation logic.
	 * @author Mateusz Rêbacz
	 *
	 */
	public class SimulationLogic implements Runnable {

		/**
		 * The simulation.
		 */
		private Simulation simulation;
		
		public SimulationLogic(Simulation sim) {
			this.simulation = sim;
		}
		
		/**
		 * Called when the simulation is started.
		 */
		@Override
		public void run() {
			if(isPaused) {
				return;
			}
			//Logic.
			Cell topRight = null, bottomLeft = null;
			for(int i = 0; i < state.size(); i++) {
				int liveNeighbors = 0;

				Cell c = state.get(i);
				
				if(topRight == null) {
					topRight = c;
				}
				if(bottomLeft == null) {
					bottomLeft = c;
				}
				
				for (Cell n : c.neighbors) {
					if(n == null) {
						//System.out.println("sss");
						c.cacheNeighbors(simulation);
					} else {
						//System.out.println("sssxdcdac");
					}
					if(n != null && n.isPopulated) {
						liveNeighbors++;
					}
				}
				if(c.isPopulated) {
					if(liveNeighbors == 2 || liveNeighbors == 3) {
						System.out.println("Still Alive");
						c.isPopulated = true;
					} else {
						System.out.println("Death");
						c.isPopulated = false;
					}
				} else {
					if(liveNeighbors == 3) {
						System.out.println("Copulation");
						if(topRight == null || c.posX > topRight.posX || c.posY > topRight.posY) {
							topRight = c;
						}
						if(bottomLeft == null || c.posX < bottomLeft.posX || c.posY < bottomLeft.posY) {
							bottomLeft = c;
						}	
						c.isPopulated = true;
					}
				}
			}
			
			expandBounds(bottomLeft.posX - 3, bottomLeft.posY - 3);
			expandBounds(topRight.posX + 3, bottomLeft.posY + 3);
			AppFrame.simulationPanel.repaint();
		}
	}
	/**
	 * The bounds of the simulation.
	 * @author Mateusz Rêbacz
	 *
	 */
	public class SimulationBounds {
		/**
		 * The bounds.
		 */
		public int minX = 0, minY = 0, maxX = 0, maxY = 0;
		
		public boolean isInBounds(int x, int y) {
			if(x > minX && x < maxX && y > minY && y < maxY) {
				return true;
			} else {
				return false;
			}
		}
	}
}
