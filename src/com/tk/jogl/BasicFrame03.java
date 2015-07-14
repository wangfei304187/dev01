package com.tk.jogl;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

// import javax.media.opengl.GLAutoDrawable;
// import javax.media.opengl.GLCapabilities;
// import javax.media.opengl.GLEventListener;
// import javax.media.opengl.GLProfile;
// import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

// http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html
// 4.1  Example 3: Clipping-area and Viewport (GL03Viewport.cpp)

public class BasicFrame03 implements GLEventListener
{

    private float angle = 0f;

    @Override
    public void display(GLAutoDrawable drawable)
    {
        System.out.println("display");

        final GL2 gl = drawable.getGL().getGL2();

        GLU glu = new GLU();
        GLUT glut = new GLUT();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);   // Clear the color buffer
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);     // To operate on Model-View matrix
        gl.glLoadIdentity();               // Reset the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(-0.5f, 0.4f, 0.0f);    // Translate
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f); // rotate by angle in degrees
        gl.glBegin(GL2GL3.GL_QUADS);                  // Each set of 4 vertices form a quad
        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
        gl.glVertex2f(-0.3f, -0.3f);
        gl.glVertex2f( 0.3f, -0.3f);
        gl.glVertex2f( 0.3f,  0.3f);
        gl.glVertex2f(-0.3f,  0.3f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(-0.4f, -0.3f, 0.0f);   // Translate
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f); // rotate by angle in degrees
        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex2f(-0.3f, -0.3f);
        gl.glVertex2f( 0.3f, -0.3f);
        gl.glVertex2f( 0.3f,  0.3f);
        gl.glVertex2f(-0.3f,  0.3f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(-0.7f, -0.5f, 0.0f);   // Translate
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f); // rotate by angle in degrees
        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glColor3f(0.2f, 0.2f, 0.2f); // Dark Gray
        gl.glVertex2f(-0.2f, -0.2f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex2f( 0.2f, -0.2f);
        gl.glColor3f(0.2f, 0.2f, 0.2f); // Dark Gray
        gl.glVertex2f( 0.2f,  0.2f);
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        gl.glVertex2f(-0.2f,  0.2f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(0.4f, -0.3f, 0.0f);    // Translate
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f); // rotate by angle in degrees
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex2f(-0.3f, -0.2f);
        gl.glVertex2f( 0.3f, -0.2f);
        gl.glVertex2f( 0.0f,  0.3f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(0.6f, -0.6f, 0.0f);    // Translate
        gl.glRotatef(180.0f + angle, 0.0f, 0.0f, 1.0f); // Rotate 180+angle degree
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        gl.glVertex2f(-0.3f, -0.2f);
        gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
        gl.glVertex2f( 0.3f, -0.2f);
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        gl.glVertex2f( 0.0f,  0.3f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix

        gl.glPushMatrix();                     // Save model-view matrix setting
        gl.glTranslatef(0.5f, 0.4f, 0.0f);     // Translate
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f); // rotate by angle in degrees
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Yellow
        gl.glVertex2f(-0.1f, -0.2f);
        gl.glVertex2f( 0.1f, -0.2f);
        gl.glVertex2f( 0.2f,  0.0f);
        gl.glVertex2f( 0.1f,  0.2f);
        gl.glVertex2f(-0.1f,  0.2f);
        gl.glVertex2f(-0.2f,  0.0f);
        gl.glEnd();
        gl.glPopMatrix();                      // Restore the model-view matrix


        //        glu.glutSwapBuffers();   // Double buffered - swap the front and back buffers

        // Change the rotational angle after each display()
        angle += 0.2f;
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        System.out.println("dispose");
    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        System.out.println("init");
        final GL2 gl = drawable.getGL().getGL2();
        // Set "clearing" or background color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        System.out.println("reshape");
        final GL2 gl = drawable.getGL().getGL2();
        final GLU glu = new GLU();
        // Compute aspect ratio of the new window
        if (height == 0)
        {
            height = 1;                // To prevent divide by 0
        }
        //        GLfloat aspect = (GLfloat)width / (GLfloat)height;
        float aspect = width*1.0f/height;

        // Set the viewport to cover the new window
        gl.glViewport(0, 0, width, height);

        // Set the aspect ratio of the clipping area to match the viewport
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);  // To operate on the Projection matrix
        gl.glLoadIdentity();             // Reset the projection matrix
        if (width >= height) {
            // aspect >= 1, set the height from -1 to 1, with larger width
            glu.gluOrtho2D(-1.0 * aspect, 1.0 * aspect, -1.0, 1.0);
        } else {
            // aspect < 1, set the width to -1 to 1, with larger height
            glu.gluOrtho2D(-1.0, 1.0, -1.0 / aspect, 1.0 / aspect);
        }
    }

    public static void main(String[] args)
    {
        // getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        BasicFrame03 b = new BasicFrame03();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(320, 320);

        //        glcanvas.swapBuffers();



        // creating frame
        final JFrame frame = new JFrame("Basic Frame");
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e)
            {
                glcanvas.repaint();
            }
        });
        // adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}