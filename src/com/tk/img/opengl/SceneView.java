package com.tk.img.opengl;

import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

public class SceneView implements GLEventListener{

    private CustomCanvas canvas;

    private GLAutoDrawable drawable;

    public SceneView() {

    }

    public void setCanvas(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    public void clear() {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 511, 0, 511, -511, 511);
        gl.glFlush();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        //		gl.glLoadIdentity();
        //		gl.glOrtho(0, 511, 0, 511, -511, 511);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        this.drawable = drawable;
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0, 511, 0, 511);

        List li = canvas.getShapes();
        for(int i=0; i<li.size(); i++) {
            IGLObject obj = (IGLObject)li.get(i);
            obj.draw(drawable);
        }

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glFlush();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        //glViewport(int x, int y, int width, int height)
        gl.glViewport(0, 0, 512, 512);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

}
