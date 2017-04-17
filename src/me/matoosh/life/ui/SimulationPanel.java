package me.matoosh.life.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * Panel where the life simulation is drawn.
 * @author Mateusz Rêbacz
 *
 */
public class SimulationPanel extends JPanel implements MouseListener, MouseMotionListener {
	//Dragging vars
	private int _lastX = -1, _lastY = -1;
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
		
		//Painting.
		//Backgound
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(DisplaySettings.gridVisible) {		
			paintGrid(graphics, getSize().height, getSize().width, DisplaySettings.gridXOffset, DisplaySettings.gridYOffset);
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
	private void paintGrid(Graphics2D graphics, int height, int width, int xOffset, int yOffset) {
		graphics.setColor(Color.GRAY);
		
		int baseSize = DisplaySettings.baseCellSize;
		//Height
		if(height % (baseSize / DisplaySettings.gridScale) != 0) {
			height = (height / (baseSize / DisplaySettings.gridScale) + 1)*(baseSize / DisplaySettings.gridScale);
		}
		//Width
		if(width % (baseSize / DisplaySettings.gridScale) != 0) {
			width = (width / (baseSize / DisplaySettings.gridScale) + 1)*(baseSize / DisplaySettings.gridScale);
		}
		
		//Num of rows to draw
		int rows = height  / (baseSize / DisplaySettings.gridScale);
		int rowHeight = height/rows;
		//Num of columns to draw
		int columns = width / (baseSize / DisplaySettings.gridScale);
		int columnWidth = width/columns;
		
		//System.out.println("Y offset: " + yOffset);
		//Rows
		for(int i = 0; i <= rows; i++) {	
			if(i * rowHeight + yOffset < 0) {
				int localYOffset = yOffset - rowHeight*rows*((i * rowHeight + yOffset - height) / height);
				graphics.drawLine(0, i * rowHeight + localYOffset, width, i * rowHeight + localYOffset);	
			} else if (i * rowHeight + yOffset > height) {
				int localYOffset = yOffset - rowHeight*rows*((i * rowHeight + yOffset) / height);
				graphics.drawLine(0, i * rowHeight + localYOffset, width, i * rowHeight + localYOffset);	
			} else {
				graphics.drawLine(0, i * rowHeight + yOffset, width, i * rowHeight + yOffset);	
			}
		}
		
		//System.out.println("X offset: " + xOffset);
		//Columns
		for(int i = 0; i <= columns; i++) {
			if(i * columnWidth + xOffset < 0) {
				int localXOffset = xOffset - columnWidth*columns*((i * columnWidth + xOffset - width) / width);
				graphics.drawLine(i * columnWidth + localXOffset, 0, i * columnWidth + localXOffset, height);	
			} else if (i * columnWidth + xOffset > height) {
				int localXOffset = xOffset - columnWidth*columns*((i * columnWidth + xOffset) / width);
				graphics.drawLine(i * columnWidth + localXOffset, 0, i * columnWidth + localXOffset, height);	
			} else {
				graphics.drawLine(i * columnWidth + xOffset, 0, i * columnWidth + xOffset, height);	
			}
		}
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
	}
	@Override
	public void mouseDragged(MouseEvent event) {
		Point point = event.getPoint();
		
		int changeX = point.x - _lastX;
		int changeY = point.y - _lastY;
		
		DisplaySettings.gridXOffset += changeX;
		DisplaySettings.gridYOffset += changeY;
		
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
