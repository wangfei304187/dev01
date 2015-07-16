package com.tk.img.opengl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

public class GLText implements IGLObject
{

    //bottom-left position
    private float x;
    private float y;

    private String str;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setText(String str) {
        this.str = str;
    }

    @Override
    public void draw(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();
        gl.glRasterPos2f(x, y);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        // convert text to bitmap and tell what string to put
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, str);

        gl.glRasterPos2f(0, 0);
    }

}
