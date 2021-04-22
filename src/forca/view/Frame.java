package forca.view;

import javax.swing.JFrame;

import forca.view.panels.HomeScreen;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public Frame() {

		new HomeScreen(this);
	}

}
