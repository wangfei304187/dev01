package com.tk.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

/**
 * For our purposes only two of the GLEventListeners matter.
 * Those would be init() and display().
 * 为了达到我们的目的，GLEventListener中只有两个方法有用。
 * 它们是init()和display()。
 */
public class SecondGLEventListener implements GLEventListener
{

    @Override
    public void display(GLAutoDrawable drawable)
    {
        // TODO Auto-generated method stub
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;

        //        GL gl = drawable.getGL();
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(5.0f);

        for (int i = 0; i < 50; i++)
        {

            red -= .09f;
            green -= .12f;
            blue -= .15f;

            if (red < 0.15)
            {
                red = 1.0f;
            }
            if (green < 0.15)
            {
                green = 1.0f;
            }
            if (blue < 0.15)
            {
                blue = 1.0f;
            }

            gl.glColor3f(red, green, blue);

            gl.glBegin(GL.GL_POINTS);
            gl.glVertex2i(i * 10, 150);
            gl.glEnd();

            //            gl.glTranslatef(0, 0, 0);
            gl.glBegin(GL2GL3.GL_QUADS); // Each set of 4 vertices form a quad
            gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
            gl.glVertex2f(-100f, -100f); // x, y
            gl.glVertex2f(100f, -100f);
            gl.glVertex2f(100f, 100f);
            gl.glVertex2f(-100f, 100f);
            gl.glEnd();


            //            gl.glTranslatef(100, 100, 0);
            gl.glBegin(GL2GL3.GL_QUADS); // Each set of 4 vertices form a quad
            gl.glColor3f(0.0f, 1.0f, 0.0f); // Red
            gl.glVertex2f(-50f, -50f); // x, y
            gl.glVertex2f(50f, -50f);
            gl.glVertex2f(50f, 50f);
            gl.glVertex2f(-50f, 50f);
            gl.glEnd();

            gl.glFlush();
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        // TODO Auto-generated method stub

        final GL2 gl = drawable.getGL().getGL2();

        GLU glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        //        gl.glViewport(0, 0, 500, 300);
        gl.glViewport(0, 0, 200, 200);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        //        glu.gluOrtho2D(0.0, 500.0, 0.0, 300.0);
        glu.gluOrtho2D(0.0, 200, 0.0, 200);


    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
    {
        // TODO Auto-generated method stub

    }
}