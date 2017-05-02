package me.matoosh.life.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

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
 * @author Mateusz Rebacz
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
		
		//Simulation step button.
		JButton stepSimulation = new JButton("Simulate Step");
		stepSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.stepsToMake++;
		});
		add(stepSimulation);
		
		//Stop simulation button.
		JButton stopSimulation = new JButton("Stop Simulation");
		stopSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.stop();
		});
		add(stopSimulation);
		//New simulation button.
		JButton newSimulation = new JButton("New Simulation");
		newSimulation.addActionListener((ActionEvent e) -> {
			SimulationManager.currentSimulation.stop();
			SimulationManager.currentSimulation = new Simulation(new SimulationSettings());
			DisplaySettings.currentViewport = new Viewport(AppFrame.simulationPanel);
			AppFrame.simulationPanel.repaint();
		});
		add(newSimulation);
		//Randomize button.
		JButton randomizeSimulation = new JButton("Randomize");
		randomizeSimulation.addActionListener((ActionEvent e) -> {
			Random random = new Random();
			for(int i = 0; i < SimulationManager.currentSimulation.state.size(); i++) {
				if(random.nextInt(2) == 1) {
					SimulationManager.currentSimulation.state.get(i).isPopulated = true;
				}
			}
			AppFrame.simulationPanel.repaint();
		});
		add(randomizeSimulation);
		
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
		//Show alive
		JToggleButton aliveToggle = new JToggleButton("Show alive", DisplaySettings.showAlive);
		aliveToggle.addActionListener((ActionEvent e) -> {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            DisplaySettings.showAlive = tBtn.isSelected();
	            AppFrame.simulationPanel.repaint();
	      });
		add(aliveToggle);
		//Show dead
		JToggleButton deadToggle = new JToggleButton("Show dead", DisplaySettings.showDead);
		deadToggle.addActionListener((ActionEvent e) -> {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            DisplaySettings.showDead = tBtn.isSelected();
	            AppFrame.simulationPanel.repaint();
	      });
		add(deadToggle);
		//Show bounds
		JToggleButton boundsToggle = new JToggleButton("Show bounds", DisplaySettings.showCurrentBounds);
		boundsToggle.addActionListener((ActionEvent e) -> {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            DisplaySettings.showCurrentBounds = tBtn.isSelected();
	            AppFrame.simulationPanel.repaint();
	      });
		add(boundsToggle);
	}
}
