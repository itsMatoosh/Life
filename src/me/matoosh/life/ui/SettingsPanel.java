package me.matoosh.life.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel with the simulation settings.
 * @author Mateusz Rêbacz
 *
 */
public class SettingsPanel extends JPanel {
	
	public SettingsPanel() {
		setLayout(new GridLayout(0, 1));
		addContent();
    }
	/**
	 * Adds content to the panel.
	 */
	private void addContent() {
		//Grid scale
		JSlider scaleSlider = new JSlider(1, 50, DisplaySettings.gridScale);
		scaleSlider.addChangeListener((ChangeEvent e) -> {
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        DisplaySettings.gridScale = (int)source.getValue();
		        AppFrame.simulationPanel.repaint();
		    }
		});
		add(scaleSlider);
		
		//Grid x offset
		JSlider xOffset = new JSlider(0, 1000, DisplaySettings.gridXOffset);
		xOffset.addChangeListener(new XOffsetChangeListener());
		add(xOffset);
		
		//Grid y offset
		JSlider yOffset = new JSlider(0, 1000, DisplaySettings.gridYOffset);
		yOffset.addChangeListener(new YOffsetChangeListener());
		add(yOffset);
		
		//Show grid
		JToggleButton gridToggle = new JToggleButton("Show grid", DisplaySettings.gridVisible);
		gridToggle.addActionListener((ActionEvent e) -> {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            DisplaySettings.gridVisible = tBtn.isSelected();
	            AppFrame.simulationPanel.repaint();
	      });
		add(gridToggle);
	}
	
	/**
	 * Listens for the scale change event.
	 * @author Mateusz Rêbacz
	 *
	 */
	private class XOffsetChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        DisplaySettings.gridXOffset = (int)source.getValue();
		        AppFrame.simulationPanel.repaint();
		    }
		}
		
	}
	/**
	 * Listens for the scale change event.
	 * @author Mateusz Rêbacz
	 *
	 */
	private class YOffsetChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        DisplaySettings.gridYOffset = (int)source.getValue();
		        AppFrame.simulationPanel.repaint();
		    }
		}
		
	}
}
