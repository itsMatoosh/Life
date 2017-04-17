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
		
		//Show grid
		JToggleButton gridToggle = new JToggleButton("Show grid", DisplaySettings.gridVisible);
		gridToggle.addActionListener((ActionEvent e) -> {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            DisplaySettings.gridVisible = tBtn.isSelected();
	            AppFrame.simulationPanel.repaint();
	      });
		add(gridToggle);
	}
}
