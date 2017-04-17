package me.matoosh.life.simulation;

import java.util.ArrayList;

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
	 * The Thread this simulation is running on.
	 */
	private Thread simulationThread;
	
	/**
	 * Creates a new simulation.
	 * @param settings
	 */
	public Simulation(SimulationSettings settings) {
		this.settings = settings;
		state.add(new Cell(0,0));
		state.add(new Cell(1,5));
	}
	
	/**
	 * Starts or continues the simulation.
	 */
	public void start() {
		//Creating the thread if needed.
		if(simulationThread == null) {
			simulationThread = new Thread(new SimulationLogic(this));
		}
		
		//Starting or resuming the simulation.
		if(simulationThread.isAlive()) {
			isPaused = false;
		} else {
			simulationThread.start();
		}
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
		simulationThread.interrupt();
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
		if(found == null) {
			found = new Cell(x, y);
			state.add(found);
		}
		
		return found;
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
			/**
			 * Continue the simulation loop until the thread is interuped.
			 */
			while(!Thread.currentThread().isInterrupted()) {
				//Handling the pause.
				if(simulation.isPaused) {
					try {
						wait();
					} catch (InterruptedException e) {
						//Called when the thread is interrupted while waiting.
						e.printStackTrace();
					}
				}
				
				//Logic.
				for(Cell c : simulation.state) {
					int liveNeighbors = 0;
					for (Cell n : c.neighbors) {
						if(n.isPopulated) {
							liveNeighbors++;
						}
					}
					if(c.isPopulated) {
						if(liveNeighbors < 2) {
							c.isPopulated = false;
						} else if(liveNeighbors == 2 || liveNeighbors == 3) {
							c.isPopulated = true;
						} else if(liveNeighbors > 3) {
							c.isPopulated = false;
						}
					} else {
						if(liveNeighbors == 3) {
							c.isPopulated = true;
						}
					}
				}
				
				//Wait before next generation.
				try {
					wait(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
