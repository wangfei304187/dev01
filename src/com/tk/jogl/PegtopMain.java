package com.tk.jogl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class PegtopMain extends JFrame {

    GLRender listener = new GLRender();
    static FPSAnimator animator = null;

    public PegtopMain() throws HeadlessException {
        super("画陀螺");
        setSize(600, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        GLCapabilities glcaps = new GLCapabilities();
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glcaps = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(glcaps);
        canvas.addGLEventListener(listener);
        addKeyListener(new keyEventListener());
        //canvas.addMouseListener(listener);
        getContentPane().add(canvas, BorderLayout.CENTER);
        PegtopMain.animator = new FPSAnimator(canvas, 60, true);

        centerWindow(this);
    }

    private void centerWindow(Component frame) { // 居中窗体
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        frame.setLocation(screenSize.width - frameSize.width >> 1,
                screenSize.height - frameSize.height >> 1);

    }

    public static void main(String[] args) {
        final PegtopMain app = new PegtopMain();
        // 显示窗体
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                app.setVisible(true);
            }
        });
        // 动画线程开始
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                PegtopMain.animator.start();
            }
        });
    }

    class keyEventListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            //处理键盘事件
            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 38) {
                GLRender.xRot -= 5.0f;
            }

            if (e.getKeyCode() == 40) {
                GLRender.xRot += 5.0f;
            }

            if (e.getKeyCode() == 37) {
                GLRender.yRot -= 5.0f;
            }

            if (e.getKeyCode() == 39) {
                GLRender.yRot += 5.0f;
            }


        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
