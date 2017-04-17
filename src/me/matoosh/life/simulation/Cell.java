package me.matoosh.life.simulation;

/**
 * A single cell of the simulation.
 * @author Mateusz Rêbacz
 *
 */
public class Cell {
	/**
	 * Whether the cell is active.
	 */
	public boolean isPopulated;
	/**
	 * Timestamp of when the cell became active.
	 */
	public float timestamp;
	/**
	 * Neighbors of the cell.
	 */
	public Cell[] neighbors = new Cell[8];
	
	/**
	 * X position of the cell.
	 */
	public int posX;
	/**
	 * Y position of the cell.
	 */
	public int posY;
	/**
	 * The simulation this cell belongs to.
	 */
	public Simulation simulation;
	
	/**
	 * Creates a cell with x and y coordinates.
	 * @param x
	 * @param y
	 */
	public Cell(int x, int y, Simulation sim) {
		this.posX = x;
		this.posY = y;
		this.simulation = sim;
	}
	/**
	 * Caches the neighbors from the specified simulation.
	 * @param sim
	 */
	public void cacheNeighbors(Simulation sim) {
		neighbors[0] = sim.getCellAt(posX - 1, posY + 1);
		neighbors[1] = sim.getCellAt(posX, posY + 1);
		neighbors[2] = sim.getCellAt(posX + 1, posY + 1);
		neighbors[3] = sim.getCellAt(posX - 1, posY);
		neighbors[4] = sim.getCellAt(posX + 1, posY);
		neighbors[5] = sim.getCellAt(posX - 1, posY - 1);
		neighbors[6] = sim.getCellAt(posX, posY - 1);
		neighbors[7] = sim.getCellAt(posX + 1, posY - 1);
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
