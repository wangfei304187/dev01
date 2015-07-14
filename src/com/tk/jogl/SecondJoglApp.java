package com.tk.jogl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * This is a basic JOGL app. Feel free to
 * reuse this code or modify it.
 * ���Ǹ�������JOGL����������������øô�������޸�����
 */
public class SecondJoglApp extends JFrame
{

    public static void main(String[] args)
    {
        final SecondJoglApp app = new SecondJoglApp();

        // show what we've done
        // ��һ����������ʲô
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                app.setVisible(true);
            }
        });
    }

    public SecondJoglApp()
    {
        // set the JFrame title
        // ����JFrame����
        super("Second JOGL Application");

        // kill the process when the JFrame is closed
        // ��JFrame�رյ�ʱ�򣬽�������
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // only three JOGL lines of code ... and here they are
        // ֻ������JOGL���� ... ����
        // GLCapabilities glcaps = new GLCapabilities();
        // GLCanvas glcanvas = GLDrawableFactory.getFactory().createGLCanvas(glcaps);
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(new SecondGLEventListener());

        // add the GLCanvas just like we would any Component
        // ���������һ����GLCanvas����
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(200, 200);

        // center the JFrame on the screen
        // ʹJFrame��ʾ����Ļ����
        centerWindow(this);
    }

    public void centerWindow(Component frame)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        frame.setLocation(screenSize.width - frameSize.width >> 1, screenSize.height - frameSize.height >> 1);
    }
}