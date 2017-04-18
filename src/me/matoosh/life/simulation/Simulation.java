package me.matoosh.life.simulation;

import java.util.ArrayList;
import java.util.List;
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
	public List<Cell> state = new ArrayList<Cell>();
	/**
	 * Whether the simulation has been paused.
	 */
	public boolean isPaused = false;
	/**
	 * The current bounds of the simulation.
	 */
	public SimulationBounds currentBounds;
	/**
	 * Steps to make even whern the simulation is paused.
	 */
	public int stepsToMake = 0;
	
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
		simulationHandle = scheduler.scheduleAtFixedRate(new SimulationLogic(this), 50, 50, TimeUnit.MILLISECONDS);
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
			found = new Cell(x, y);
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
	 * Updates the bounds of the simulation.
	 * @param x
	 * @param y
	 */
	private void updateBounds() {
		for(int c = 0; c < state.size(); c++) {
			Cell cell = state.get(c);
			if(!currentBounds.isInBounds(cell.posX, cell.posY, 4)) {
				state.remove(c);
			}
		}
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
		
		//System.out.println("New bounds: " + currentBounds.minX + "," + currentBounds.minY + " - " + currentBounds.maxX + "," + currentBounds.maxY);
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
				if(stepsToMake > 0) {
					stepsToMake--;
				} else {
					return;	
				}
			}
			//Logic.
			int maxX = 0, maxY = 0, minX = 0, minY = 0;
			
			//Counting neighbors.
			for(int i = 0; i < state.size(); i++) {
				state.get(i).cacheNeighbors(simulation);			
			}
			
			//Logic.
			for(int i = 0; i < state.size(); i++) {
				Cell c = state.get(i);
				
				if(c.isPopulated) {
					if(c.posX > maxX) {
						maxX = c.posX;
					}
					else if(c.posX < minX) {
						minX = c.posX;
					}
					if(c.posY > maxY) {
						maxY = c.posY;
					}
					else if(c.posY < minY) {
						minY = c.posY;
					}
					
					if(c.livingNeighbors == 2 || c.livingNeighbors == 3) {
						c.setPopulated = true;
					} else {
						c.setPopulated = false;
					}
				} else {
					if(c.livingNeighbors == 3) {
						//System.out.println("Copulation");
						c.setPopulated = true;
					}
				}
			}
		
			//Counting neighbors.
			for(Cell c : state) {
				c.isPopulated = c.setPopulated;		
			}
			
			currentBounds.minX = minX - 5;
			currentBounds.minY = minY - 5;
			currentBounds.maxX = maxX + 5;
			currentBounds.maxY = maxY + 5;
			updateBounds();
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
		public boolean isInBounds(int x, int y, int tolerance) {
			if(x > (minX - tolerance) && x < (maxX + tolerance) && y > (minY - tolerance) && y < (maxY + tolerance)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
