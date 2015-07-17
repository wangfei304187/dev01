package com.tk.img.opengl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class CustomCanvas extends GLCanvas
{
    private List<IGLObject> li = new ArrayList<IGLObject>();

    private boolean immediateRefresh = false;

    private SceneView view;

    public CustomCanvas(GLCapabilitiesImmutable capabiliteis) {
        super(capabiliteis);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                GLHLine topHLine = getTopHLine();
                if(!topHLine.isSelected()) {

                    //                    float distance = topHLine.getY() - convertY(e.getY());
                    //
                    //                    if(distance < 5f && distance > -5f) {
                    //                        topHLine.setSelected(true);
                    //                        repaintSelectedHLine(topHLine);
                    //                    }

                    // convert y relative to left-bottom corner
                    boolean flag = topHLine.canSelected(convertLBPoint(e.getPoint()));
                    if(flag) {
                        topHLine.setSelected(true);
                        repaintSelectedHLine(topHLine);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                GLHLine topHLine = getTopHLine();
                if(topHLine.isSelected()) {
                    topHLine.setSelected(false);
                    topHLine.setY(convertLBY(e.getY()));

                    repaintDefaultHLine(topHLine);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }

        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                GLHLine topHLine = getTopHLine();
                if(topHLine.isSelected()) {
                    topHLine.setY(convertLBY(e.getY()));

                    repaintSelectedHLine(topHLine);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }

    @Override
    public void addGLEventListener(GLEventListener arg0)
    {
        view = (SceneView)arg0;
        super.addGLEventListener(arg0);
        view.setCanvas(this);
    }

    public List getShapes() {
        return li;
    }

    public void addShape(IGLObject obj) {
        li.add(obj);
        if(immediateRefresh) {
            refresh();
        }
    }

    public void removeShape(IGLObject obj) {
        li.remove(obj);
        if(immediateRefresh) {
            refresh();
        }
    }

    public void refresh() {
        repaint();
    }

    public void setImmediate(boolean immediateRefresh) {
        this.immediateRefresh = immediateRefresh;
    }

    public boolean getImmediate() {
        return immediateRefresh;
    }

    // convert point relative to left-bottom corner
    public Point convertLBPoint(Point p) {
        return new Point(convertLBX(p.x), convertLBY(p.y));
    }

    // convert y relative to left-bottom corner
    public int convertLBY(int ty) {
        int y = getHeight() - ty;
        return y > 510 ? 510 : y < 0 ? 0 : y;
    }

    public int convertLBX(int x) {
        return x;
    }

    public void repaintDefaultHLine(GLHLine hline) {
        repaintUnselectedHLine(hline);
    }

    private void repaintUnselectedHLine(GLHLine hline) {
        hline.setLineWidth(1.0f);;
        repaintHLine(hline);
    }

    public void repaintSelectedHLine(GLHLine hline) {
        hline.setLineWidth(5.0f);
        repaintHLine(hline);
    }

    private void repaintHLine(GLHLine hline) {
        refresh();
    }

    public GLHLine getTopHLine() {
        return getHLine(Constants.TOP_HLINE);
    }

    private GLHLine getHLine(String name) {
        for (IGLObject obj : li)
        {
            if(obj instanceof GLHLine && obj.getName() == name) {
                return (GLHLine)obj;
            }
        }
        return null;
    }

}
