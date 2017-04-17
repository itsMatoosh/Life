package me.matoosh.life.ui;

/**
 * Stores the display settings.
 * @author Mateusz Rêbacz
 *
 */
public class DisplaySettings {
	/**
	 * Whether the grid should be visible during the simulation.
	 */
	public static boolean gridVisible = true;
	/**
	 * The scale of the simulation grid.
	 */
	public static int gridScale = 5;
	/**
	 * Size of a cell at scale 1.
	 */
	protected static int baseCellSize = 200;
	/**
	 * Whether the cells should be displayed.
	 */
	public static boolean cellsVisible = true;
	/**
	 * The current viewport.
	 */
	public static Viewport currentViewport;
}