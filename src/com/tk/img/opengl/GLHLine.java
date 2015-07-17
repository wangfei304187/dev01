package com.tk.img.opengl;

import java.awt.Point;
import java.awt.geom.Line2D;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLHLine implements IGLObject
{
    private String name;
    private int x1 = 0;
    private int x2 = 511;
    private int y = 510;

    private float lineWidth = 1.0f;

    private boolean selected = false;

    public void setY(int y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public Point getStart() {
        return new Point(x1, y);
    }

    public Point getEnd() {
        return new Point(x2, y);
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public GLHLine(String name) {
        this.name = name;
    }

    @Override
    public void draw(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        gl.glLineWidth(lineWidth); // call before glBegin

        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(1.0f, 0.51f, 0.98f); // #FF83FA
        gl.glVertex2f(x1, y); // start point
        gl.glVertex2f(x2, y); // end point
        gl.glEnd();
    }

    @Override
    public String getName()
    {
        return name;
    }

    // p : relative to left-bottom corner
    public boolean canSelected(Point p) {
        int boxX = p.x - Constants.HIT_BOX_SIZE / 2;
        int boxY = p.y - Constants.HIT_BOX_SIZE / 2;

        int width = Constants.HIT_BOX_SIZE;
        int height = Constants.HIT_BOX_SIZE;

        Line2D.Float line = new Line2D.Float(getStart(), getEnd());

        return line.intersects(boxX, boxY, width, height);
    }

}
