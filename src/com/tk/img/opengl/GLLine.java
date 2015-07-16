package com.tk.img.opengl;

import java.awt.Point;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLLine implements IGLObject
{

	private Point p1;
	private Point p2;

	public void setStartPoint(Point p) {
		p1 = p;
	}

	public void setEndPoint(Point p) {
		p2 = p;
	}

	@Override
	public void draw(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(1.0f, 0.51f, 0.98f); // #FF83FA
		//		gl.glLineWidth(5.0f);
		gl.glVertex2d(p1.getX(), p1.getY()); // start point
		gl.glVertex2d(p2.getX(), p2.getY()); // end point


		gl.glEnd();
	}

}
