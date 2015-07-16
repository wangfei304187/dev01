package com.tk.img.opengl;

import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

public class SceneView implements GLEventListener{

    public CustomCanvas canvas;

    public SceneView() {

    }

    public void setCanvas(CustomCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0, 512, 0, 512);

        List li = canvas.getShapes();
        for(int i=0; i<li.size(); i++) {
            IGLObject obj = (IGLObject)li.get(i);
            obj.draw(drawable);
        }

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {

    }


    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

}
