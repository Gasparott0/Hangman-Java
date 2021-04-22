package forca.view.helper;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameHelper {

	public void initFrame(JPanel panel, int width, int height) {
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(null);
	}

	public void configFrame(JFrame frame, JPanel panel) {
		frame.setContentPane(panel);
		frame.pack();
		frame.setTitle("Hangman game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
