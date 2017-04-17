package me.matoosh.life;

import java.awt.EventQueue;

import me.matoosh.life.ui.AppFrame;

/**
 * Main class of the Life project.
 * @author Mateusz R�bacz
 *
 */
public class Life {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AppFrame();
            }
        });
	}

}
