package me.matoosh.life.simulation;

/**
 * A single cell of the simulation.
 * @author Mateusz Rebacz
 *
 */
public class Cell {
	/**
	 * Whether the cell is active.
	 */
	public boolean isPopulated;
	/**
	 * Sets the cell into the new state.
	 */
	public boolean setPopulated;
	
	/**
	 * Neighbors of the cell.
	 */
	public Cell[] neighbors = new Cell[8];
	/**
	 * Number of living neighbors.
	 */
	public int livingNeighbors;
	
	/**
	 * X position of the cell.
	 */
	public int posX;
	/**
	 * Y position of the cell.
	 */
	public int posY;
	
	/**
	 * Creates a cell with x and y coordinates.
	 * @param x
	 * @param y
	 */
	public Cell(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	/**
	 * Caches the neighbors from the specified simulation.
	 * @param sim
	 */
	public void cacheNeighbors(Simulation sim) {
		if(neighbors[0] == null) {
			neighbors[0] = sim.getCellAt(posX - 1, posY + 1);
			neighbors[1] = sim.getCellAt(posX, posY + 1);
			neighbors[2] = sim.getCellAt(posX + 1, posY + 1);
			neighbors[3] = sim.getCellAt(posX - 1, posY);
			neighbors[4] = sim.getCellAt(posX + 1, posY);
			neighbors[5] = sim.getCellAt(posX - 1, posY - 1);
			neighbors[6] = sim.getCellAt(posX, posY - 1);
			neighbors[7] = sim.getCellAt(posX + 1, posY - 1);	
		}
		
		livingNeighbors = 0;
		for(int n = 0; n < neighbors.length; n++) {
			if(neighbors[n] == null) continue;
			if(neighbors[n].isPopulated) {
				livingNeighbors++;
			}
		}
	}
	/**
	 * Called when the cell has been clicked.
	 */
	public void onClick() {
		if(isPopulated) {
			isPopulated = false;
		} else {
			isPopulated = true;
		}
		System.out.println("Cell: " + posX + "," + posY + " populated: " + isPopulated);
	}
}
