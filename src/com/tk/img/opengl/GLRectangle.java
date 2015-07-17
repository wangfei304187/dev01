package com.tk.img.opengl;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLRectangle implements IGLObject
{
    private String name;

    private int x1;
    private int y1;

    private int x2;
    private int y2;

    private float lineWidth = 1.0f;

    private boolean selected = false;

    @Override
    public String getName()
    {
        return name;
    }

    public void setX1(int x) {
        x1 = x;
    }

    public void setY1(int y) {
        y1 = y;
    }

    public void setX2(int x) {
        x2 = x;
    }

    public void setY2(int y) {
        y2 = y;
    }

    public void setLeftTop(Point p) {
        x1 = p.x;
        y1 = p.y;
    }

    public void setRightBottom(Point p) {
        x2 = p.x;
        y2 = p.y;
    }

    public Point getLeftTop() {
        return new Point(x1, y1);
    }

    public Point getRightBottom() {
        return new Point(x2, y2);
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

    public GLRectangle(String name) {
        this.name = name;
    }

    @Override
    public void draw(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        //        gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);  // transparent
        //        gl.glLineWidth(lineWidth); // call before glBegin
        //        gl.glBegin(GL2GL3.GL_QUADS);
        //        gl.glColor3f(0, 1, 0);
        //        //        gl.glRectf(x1, y1, x2, y2);
        //        gl.glVertex2f(x1, y1);
        //        gl.glVertex2f(x2, y1);
        //        gl.glVertex2f(x2, y2);
        //        gl.glVertex2f(x1, y2);
        //        gl.glEnd();

        gl.glLineWidth(lineWidth); // call before glBegin
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0f, 1f, 0f); // green
        gl.glVertex2f(x1, y1); // start point
        gl.glVertex2f(x2, y1); // end point
        gl.glVertex2f(x2, y2);
        gl.glVertex2f(x1, y2);
        gl.glEnd();
    }

    // p : relative to left-bottom corner
    public boolean canSelected(Point p) {
        int boxX = p.x - Constants.HIT_BOX_SIZE / 2;
        int boxY = p.y - Constants.HIT_BOX_SIZE / 2;

        int width = Constants.HIT_BOX_SIZE;
        int height = Constants.HIT_BOX_SIZE;

        Rectangle r = new Rectangle(x1, y1, x2, y2);

        return r.intersects(boxX, boxY, width, height);

    }


    /**
     *
     * (x1, y1)               (x2, y1)
     *     ----------------------
     *     |                    |
     *     |                    |
     *     ----------------------
     * (x1, y2)               (x2, y2)
     *
     *
     *
     * (x1, y2)               (x2, y2)
     *     ----------------------
     *     |                    |
     *     |                    |
     *     ----------------------
     * (x1, y1)               (x2, y1)
     *
     *
     *
     * (x2, y1)               (x1, y1)
     *     ----------------------
     *     |                    |
     *     |                    |
     *     ----------------------
     * (x2, y2)               (x1, y2)
     *
     *
     *
     *
     * (x2, y2)               (x1, y2)
     *     ----------------------
     *     |                    |
     *     |                    |
     *     ----------------------
     * (x2, y1)               (x1, y1)
     *
     */


    // 0: top side
    // 1: right side
    // 2: bottom side
    // 3: left side
    public int getSelectedSide(Point p) {
        int boxX = p.x - Constants.HIT_BOX_SIZE / 2;
        int boxY = p.y - Constants.HIT_BOX_SIZE / 2;

        int width = Constants.HIT_BOX_SIZE;
        int height = Constants.HIT_BOX_SIZE;


        if(x1 < x2 && y1 > y2) {
            Line2D.Float topLine = new Line2D.Float(x1, y1, x2, y1);
            if(topLine.intersects(boxX, boxY, width, height)) {
                return Constants.RECT_TOP_SIDE_SELECTED;
            }

            Line2D.Float rightLine = new Line2D.Float(x2, y1, x2, y2);
            if(rightLine.intersects(boxX, boxY, width, height)) {
                return Constants.RECT_RIGHT_SIDE_SELECTED;
            }

            Line2D.Float bottomLine = new Line2D.Float(x1, y2, x2, y2);
            if(rightLine.intersects(boxX, boxY, width, height)) {
                return Constants.RECT_BOTTOM_SIDE_SELECTED;
            }

            Line2D.Float leftLine = new Line2D.Float(x1, y1, x1, y2);
            if(rightLine.intersects(boxX, boxY, width, height)) {
                return Constants.RECT_LEFT_SIDE_SELECTED;
            }

        }


        return Constants.RECT_NO_SIDE_SELECTED;
    }



}
