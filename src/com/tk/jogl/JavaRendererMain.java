package com.tk.jogl;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class JavaRendererMain {
    public static void main(String[] args) {
        JavaRendererMain app = new JavaRendererMain();
        app.createAndRun();
    }

    public void createAndRun() {
        JFrame frame = new JFrame("Jogl 3d Shape/Rotation");
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas();
        final Animator animator = new Animator(canvas);
        animator.start();
        canvas.addGLEventListener(new JavaRenderer());
        frame.add(canvas);
        frame.setSize(640, 480);
        //        frame.setUndecorated(true);
        int size = frame.getExtendedState();
        //        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // no hace falta llamar al m¨¦todo Animator#stop()si marca error eliminenla
                System.out.println("closing");
                animator.stop();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e)
            {
                System.out.println("closed");
            }


        });
        frame.setVisible(true);
        //        canvas.requestFocus();
    }
}