package me.matoosh.life.ui;

import javax.swing.JPanel;

/**
 * Represents the viewport of the simulation window.
 * @author Mateusz Rebacz
 *
 */
public class Viewport {
	/**
	 * Positions of the viewport.
	 */
	public int minX, minY, maxX, maxY;
	/**
	 * Dimensions of the viewport.
	 */
	public int height, width;

	/**
	 * Creates a viewport based on a panel.
	 * @param panel
	 */
	public Viewport(JPanel panel) {
		minX = 0;
		minY = 0;
		resize(panel);
	}
	/**
	 * Resizes the viewport.
	 * @param panel
	 */
	public void resize(JPanel panel) {
		height = panel.getSize().height;
		width = panel.getSize().width;
		
		//Height
		if(height % (DisplaySettings.baseCellSize / DisplaySettings.gridScale) != 0) {
			height = (height / (DisplaySettings.baseCellSize / DisplaySettings.gridScale) + 1)*(DisplaySettings.baseCellSize / DisplaySettings.gridScale);
		}
		//Width
		if(width % (DisplaySettings.baseCellSize / DisplaySettings.gridScale) != 0) {
			width = (width / (DisplaySettings.baseCellSize / DisplaySettings.gridScale) + 1)*(DisplaySettings.baseCellSize / DisplaySettings.gridScale);
		}
		
		maxY = minY + height;
		maxX = minX + width;
	}
	
	/**
	 * Moves the viewport.
	 * @param changeX
	 * @param changeY
	 */
	public void move(int changeX, int changeY) {
		minX += changeX;
		minY += changeY;
		maxY = minY + height;
		maxX = minX + width;
	}
}
