package me.matoosh.life.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import me.matoosh.life.simulation.Cell;
import me.matoosh.life.simulation.Simulation;
import me.matoosh.life.simulation.SimulationManager;

/**
 * Panel where the life simulation is drawn.
 * @author Mateusz Rêbacz
 *
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	//Dragging vars
	private int _lastX = -1, _lastY = -1;
	private int _lastChangeX = -1, _lastChangeY = -1;
	private boolean _dragging = false;
	
	/**
	 * Initializes the simulation panel.
	 */
	public SimulationPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * Paints the simulation.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		//Type conv
		Graphics2D graphics = (Graphics2D) g;
		
		//Scaling the viewport.
		if(DisplaySettings.currentViewport == null) {
			DisplaySettings.currentViewport = new Viewport(this);
		}
		DisplaySettings.currentViewport.resize(this);
		
		//Painting.
		//Backgound
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(SimulationManager.currentSimulation == null) return;
		
		//Cells
		if(DisplaySettings.cellsVisible) {
			paintCells(SimulationManager.currentSimulation, graphics, DisplaySettings.currentViewport);
		}
		
		//Grid
		if(DisplaySettings.gridVisible) {		
			paintGrid(graphics, DisplaySettings.currentViewport);
		}
		
		//Bounds
		if(DisplaySettings.showCurrentBounds) {
			graphics.setColor(Color.BLUE);
			int cellSize = DisplaySettings.baseCellSize/DisplaySettings.gridScale;
			graphics.drawRect(-DisplaySettings.currentViewport.minX + SimulationManager.currentSimulation.currentBounds.minX * cellSize + cellSize/2, -DisplaySettings.currentViewport.minY + SimulationManager.currentSimulation.currentBounds.minY * cellSize + cellSize/2, 
					(SimulationManager.currentSimulation.currentBounds.maxX - SimulationManager.currentSimulation.currentBounds.minX) * cellSize + cellSize/2, 
					(SimulationManager.currentSimulation.currentBounds.maxY - SimulationManager.currentSimulation.currentBounds.minY) * cellSize + cellSize/2);
		}
	}
	/**
	 * Paints the simulation grid.
	 * @param graphics
	 * @param height
	 * @param width
	 * @param xOffset
	 * @param yOffset
	 */
	private void paintGrid(Graphics2D graphics, Viewport viewport) {
		graphics.setColor(Color.GRAY);	
		int baseSize = DisplaySettings.baseCellSize;
		
		//Num of rows to draw
		int rows = viewport.height  / (baseSize / DisplaySettings.gridScale);
		int rowHeight = viewport.height/rows;
		//Num of columns to draw
		int columns = viewport.width / (baseSize / DisplaySettings.gridScale);
		int columnWidth = viewport.width/columns;
		int offsetX = -viewport.minX;
		
		//System.out.println("Y offset: " + yOffset);
		//Rows
		for(int i = 0; i <= rows; i++) {	
			//Checking if the line is in the viewport.
			if(i*rowHeight - viewport.minY > viewport.height) {
				int localOffset = -viewport.minY - rowHeight * rows * ((i * rowHeight - viewport.minY) / viewport.height);
				graphics.drawLine(0, i * rowHeight + localOffset, viewport.width, i * rowHeight + localOffset);	
			} else if(i*rowHeight - viewport.minY < 0) {
				int localOffset = -viewport.minY - rowHeight * (rows + 1) * ((i * rowHeight - viewport.minY - viewport.height) / viewport.height);
				graphics.drawLine(0, i * rowHeight + localOffset, viewport.width, i * rowHeight + localOffset);	
			}
			else {
				graphics.drawLine(0, i*rowHeight - viewport.minY, viewport.width, i*rowHeight - viewport.minY);
			}
		}
		
		//System.out.println("X offset: " + xOffset);
		//Columns
		for(int i = 0; i <= columns; i++) {
			if(i * columnWidth + offsetX < 0) {
				int localXOffset = offsetX - columnWidth*columns*((i * columnWidth + offsetX - viewport.width) / viewport.width);
				graphics.drawLine(i * columnWidth + localXOffset, 0, i * columnWidth + localXOffset, viewport.height);	
			} else if (i * columnWidth + offsetX > viewport.height) {
				int localXOffset = offsetX - columnWidth*columns*((i * columnWidth + offsetX) / viewport.width);
				graphics.drawLine(i * columnWidth + localXOffset, 0, i * columnWidth + localXOffset, viewport.height);	
			} else {
				graphics.drawLine(i * columnWidth + offsetX, 0, i * columnWidth + offsetX, viewport.height);	
			}
		}
	}
	/**
	 * Paints the cells in the simulation.
	 * @param graphics
	 * @param height
	 * @param width
	 * @param xOffset
	 * @param yOffset
	 */
	private void paintCells(Simulation sim, Graphics2D graphics, Viewport viewport) {
		
		
		int cellSize = DisplaySettings.baseCellSize/DisplaySettings.gridScale;

		/**
		 * Drawing every populated cell.
		 */
		for(int i = 0; i < sim.state.size(); i++) {
			Cell c = sim.state.get(i);
			if(c.isPopulated) {
				graphics.setColor(Color.GREEN);
				if(!DisplaySettings.showAlive) continue;
			} else {
				graphics.setColor(Color.RED);
				if(!DisplaySettings.showDead) continue;
			}
			
			int cellX = c.posX * cellSize + cellSize/2, cellY = c.posY * cellSize + cellSize/2;
			
			//Checking if the cell is within the viewport.
			if(cellY - cellSize/2 < viewport.maxY && cellY + cellSize/2 > viewport.minY && cellX - cellSize/2 < viewport.maxX && cellX + cellSize/2 > viewport.minX) {
				graphics.fillRect(cellX - cellSize/2 - viewport.minX, cellY - cellSize/2 - viewport.minY, cellSize, cellSize);
			}
		}
	}
	/**
	 * Gets the cell located at display coordinates x and y.
	 * @param x
	 * @param y
	 * @param simulation
	 * @return
	 */
	public Cell getCellAt(int x, int y, Simulation simulation) {
		int cellSize = DisplaySettings.baseCellSize/DisplaySettings.gridScale;
		int cellX = x/cellSize, cellY = y/cellSize;
		
		return simulation.getCellAt(cellX, cellY);
	}
	/**
	 * Gets the cell located at display coordinates x and y.
	 * @param x
	 * @param y
	 * @param simulation
	 * @return
	 */
	public Cell createCellAt(int x, int y, Simulation simulation) {
		int cellSize = DisplaySettings.baseCellSize/DisplaySettings.gridScale;
		int cellX = x/cellSize, cellY = y/cellSize;
		
		return simulation.createCellAt(cellX, cellY);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		_dragging = true;
		_lastX = event.getPoint().x;
		_lastY = event.getPoint().y;
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		_dragging = false;
		
		if(_lastChangeX < 5 && _lastChangeY < 5) {
			createCellAt(event.getPoint().x + DisplaySettings.currentViewport.minX, event.getPoint().y + DisplaySettings.currentViewport.minY, SimulationManager.currentSimulation).onClick();
			repaint();
		}
		
		_lastChangeX = 0;
		_lastChangeY = 0;
	}
	@Override
	public void mouseDragged(MouseEvent event) {
		Point point = event.getPoint();
		
		int changeX = point.x - _lastX;
		int changeY = point.y - _lastY;
		_lastChangeX += changeX;
		_lastChangeY += changeY;
		
		DisplaySettings.currentViewport.move(-changeX, -changeY);
		
		_lastX = point.x;
		_lastY = point.y;
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
