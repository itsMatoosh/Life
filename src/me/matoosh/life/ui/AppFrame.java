package me.matoosh.life.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;

/**
 * The frame of the aplication.
 * @author Mateusz Rebacz
 *
 */
public class AppFrame extends JFrame {
	/**
	 * The simulation panel.
	 */
	public static SimulationPanel simulationPanel;
	
	public AppFrame() {
		super("Life");
        setSize(800, 500);
		setLayout(new GridBagLayout());
		addPanels();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	/**
	 * Adds the app panels.
	 */
	private void addPanels() {
		//Settings panel.
		add(new SettingsPanel(), new GridBagConstraints(0, 0, 1, 1, 0.05, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		
		//Simulation panel.
		simulationPanel = new SimulationPanel();
		add(simulationPanel, new GridBagConstraints(1, 0, 1, 1, 0.95, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}
}
