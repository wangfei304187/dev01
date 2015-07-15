package com.tk.img.opengl;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AppFrame extends JFrame {

	public static void main(String[] args) {
		AppFrame f = new AppFrame();
		f.setVisible(true);
	}

	public AppFrame() {
		setTitle("JOGL Frame");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
