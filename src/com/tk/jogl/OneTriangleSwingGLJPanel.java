package com.tk.jogl;
//import javax.media.opengl.GLAutoDrawable;
//import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLProfile;
//import javax.media.opengl.GLCapabilities;
//import javax.media.opengl.awt.GLJPanel;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT
 * GLJPanel.
 *
 * @author Wade Walker
 */
public class OneTriangleSwingGLJPanel {

	public static void main(String[] args) {
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities(glprofile);
		GLJPanel gljpanel = new GLJPanel(glcapabilities);

		gljpanel.addGLEventListener(new GLEventListener() {
			@Override
			public void init(GLAutoDrawable glautodrawable) {
			}

			@Override
			public void reshape(GLAutoDrawable glautodrawable, int x, int y,
					int width, int height) {
				OneTriangle.setup(glautodrawable.getGL().getGL2(), width,
						height);
			}

			@Override
			public void display(GLAutoDrawable glautodrawable) {
				OneTriangle.render(glautodrawable.getGL().getGL2(),
						glautodrawable.getSurfaceWidth(),
						glautodrawable.getSurfaceHeight());
			}
			
			@Override
			public void dispose(GLAutoDrawable glautodrawable) {
			}
		});

		final JFrame jframe = new JFrame("One Triangle Swing GLJPanel");
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				jframe.dispose();
				System.exit(0);
			}
		});

		jframe.getContentPane().add(gljpanel, BorderLayout.CENTER);
		jframe.setSize(640, 480);
		jframe.setVisible(true);
	}
}