package com.gl3dgame.perspect;               //要是想使用默认包，请去掉这行


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

public class PerspectMain extends JFrame {

    GLRender listener = new GLRender();
    static FPSAnimator animator = null;

    public PerspectMain() throws HeadlessException {
        super("透视投影,上下左右调整角度");
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
        PerspectMain.animator = new FPSAnimator(canvas, 60, true);

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
        final PerspectMain app = new PerspectMain();
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
                PerspectMain.animator.start();
            }
        });
    }

    class keyEventListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 38) {
                listener.xRot -= 5.0f;
            }

            if (e.getKeyCode() == 40) {
                listener.xRot += 5.0f;
            }

            if (e.getKeyCode() == 37) {
                listener.yRot -= 5.0f;
            }

            if (e.getKeyCode() == 39) {
                listener.yRot += 5.0f;
            }

            listener.xRot = (int) listener.xRot % 360;
            listener.yRot = (int) listener.yRot % 360;


        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
