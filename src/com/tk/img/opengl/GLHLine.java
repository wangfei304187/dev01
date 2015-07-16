package com.tk.img.opengl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLHLine implements IGLObject
{

	private float x1 = 0;
	private float x2 = 511;
	private float y = 511;

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void draw(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();

		//gl.glPointSize(5.0f);

		gl.glLineWidth(2.0f); // call before glBegin

		gl.glBegin(GL.GL_LINES);

		gl.glColor3f(1.0f, 0.51f, 0.98f); // #FF83FA

		gl.glVertex2d(x1, y); // start point
		gl.glVertex2d(x2, y); // end point

		gl.glEnd();
	}

}
