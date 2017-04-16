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
		
		//Num of rows to draw
		int rows = getSize().height  / (200 / DisplaySettings.gridScale);
		//Num of columns to draw
		int columns = getSize().width / (200 / DisplaySettings.gridScale);
		
		System.out.println("Y offset: " + yOffset);
		//Rows
		for(int i = 0; i <= rows; i++) {	
			if(i * (height/rows) + yOffset < 0) {
				graphics.drawLine(0, i * (height/rows) + yOffset - ((i * (height/rows) + yOffset - height) / height)*((rows + 1)*(height/rows)), width, i * (height/rows) + yOffset - ((i * (height/rows) + yOffset - height) / height)*((rows + 1)*(height/rows)));	
			} else if (i * (height/rows) + yOffset > height) {
				graphics.drawLine(0, i * (height/rows) + yOffset - ((i * (height/rows) + yOffset) / height)*((rows + 1)*(height/rows)), width, i * (height/rows) + yOffset - ((i * (height/rows) + yOffset) / height)*((rows + 1)*(height/rows)));	
			} else {
				graphics.drawLine(0, i * (height/rows) + yOffset, width, i * (height/rows) + yOffset);	
			}
		}
		
		System.out.println("X offset: " + xOffset);
		//Columns
		for(int i = 0; i <= columns; i++) {
			if(i * (width/columns) + xOffset < 0) {
				graphics.drawLine(i * (width/columns) + xOffset - ((i * (width/columns) + xOffset - width) / width)*((columns + 1)*(width/columns)), 0, i * (width/columns) + xOffset - ((i * (width/columns) + xOffset - width) / width)*((columns + 1)*(width/columns)), height);	
			} else if (i * (width/columns) + xOffset > height) {
				graphics.drawLine(i * (width/columns) + xOffset - ((i * (width/columns) + xOffset) / width)*((columns + 1)*(width/columns)), 0, i * (width/columns) + xOffset - ((i * (width/columns) + xOffset) / width)*((columns + 1)*(width/columns)), height);	
			} else {
				graphics.drawLine(i * (width/columns) + xOffset, 0, i * (width/columns) + xOffset, height);	
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
