package com.tk.img.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLHLine implements IGLObject
{

    private float x1 = 0;
    private float x2 = 511;
    private float y = 511;


    @Override
    public void draw(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        //        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(1.0f, 0.51f, 0.98f); // #FF83FA
        //        gl.glVertex2d(p1.getX(), p1.getY()); // start point
        //        gl.glVertex2d(p2.getX(), p2.getY()); // end point

        //        gl.glPixelZoom(2f, 2f);

        int[] arr = new int[512];
        for(int i=0; i<arr.length; i++) {
            arr[i] = 0xff << 16;
        }

        Buffer buf = IntBuffer.wrap(arr);

        gl.glDrawPixels(512, 1, GL.GL_RGB, GL.GL_UNSIGNED_INT_24_8, buf);


        gl.glEnd();

        //        gl.glPixelZoom(1f, 1f);
    }

}
