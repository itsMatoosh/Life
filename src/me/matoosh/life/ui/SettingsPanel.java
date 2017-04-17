package me.matoosh.life.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;

import me.matoosh.life.simulation.Simulation;
import me.matoosh.life.simulation.SimulationManager;
import me.matoosh.life.simulation.SimulationSettings;

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
		//New simulation button.
		JButton newSimulation = new JButton("New Simulation");
		newSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.stop();
			SimulationManager.currentSimulation = new Simulation(new SimulationSettings());
			AppFrame.simulationPanel.repaint();
		});
		add(newSimulation);
		
		//Start simulation button.
		JButton startSimulation = new JButton("Start Simulation");
		startSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.start();
		});
		add(startSimulation);
		
		//Pause simulation button.
		JButton pauseSimulation = new JButton("Pause Simulation");
		pauseSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.pause();
		});
		add(pauseSimulation);
		
		//Stop simulation button.
		JButton stopSimulation = new JButton("Stop Simulation");
		stopSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.stop();
		});
		add(stopSimulation);
		
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
