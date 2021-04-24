package forca.view;

import javax.swing.JFrame;

import forca.view.panels.HomeScreen;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	/**
	 * Create the frame.
	 */
	public Frame() {

		new HomeScreen(this);
	}

}
