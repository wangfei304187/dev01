package com.tk.jogl;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

public class JavaRenderer implements GLEventListener, KeyListener {
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();

    @Override
    public void display(GLAutoDrawable gLDrawable) {
        //        final GL gl = gLDrawable.getGL();

        final GL2 gl = gLDrawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

        gl.glBegin(GL.GL_TRIANGLES);

        // Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Right Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); gl.glVertex3f(0.0f, -1.0f, -1.0f);

        // Left Side Facing Front
        gl.glColor3f(0.0f, 1.0f, 1.0f); gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); gl.glVertex3f(0.0f, -1.0f, -1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Bottom
        gl.glColor3f(0.0f, 0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.1f, 0.1f, 0.1f); gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glColor3f(0.2f, 0.2f, 0.2f); gl.glVertex3f(0.0f, -1.0f, -1.0f);

        gl.glEnd();

        rotateT += 0.2f;
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void init(GLAutoDrawable gLDrawable) {
        //        final GL gl = gLDrawable.getGL();
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        //        gLDrawable.addKeyListener(this);


    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        //        final GL gl = gLDrawable.getGL();
        final GL2 gl = gLDrawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        JavaRenderer.glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // no hace falta llamar al m¨¦todo Animator#stop()


            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub

    }
}