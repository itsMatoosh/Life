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
	 * The x offset of the grid.
	 */
	public static int gridXOffset = 0;
	/**
	 * The y offset of the grid.
	 */
	public static int gridYOffset = 0;
	/**
	 * Size of a cell at scale 1.
	 */
	protected static int baseCellSize = 200;
}
