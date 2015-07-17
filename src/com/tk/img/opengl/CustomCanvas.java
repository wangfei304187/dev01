package com.tk.img.opengl;

import java.awt.Cursor;
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

    private List<IGLObject> rectLayer = new ArrayList<IGLObject>();

    private boolean immediateRefresh = false;

    private SceneView view;

    // 0: image & line
    // 1: rectangle mode
    // 2: annotation mode
    private int LAYER_MODE = 1;

    // 0: select rect mode
    // 1: create rect mode
    private int RECT_OP_MODE = 0;

    private Point rectP1;
    private Point rectP2;

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
                if(LAYER_MODE == 0) {
                    //                    GLHLine topHLine = getTopHLine();
                    //                    if(!topHLine.isSelected()) {
                    //
                    //                        //                    float distance = topHLine.getY() - convertY(e.getY());
                    //                        //
                    //                        //                    if(distance < 5f && distance > -5f) {
                    //                        //                        topHLine.setSelected(true);
                    //                        //                        repaintSelectedHLine(topHLine);
                    //                        //                    }
                    //
                    //                        // convert y relative to left-bottom corner
                    //                        boolean flag = topHLine.canSelected(convertLBPoint(e.getPoint()));
                    //                        if(flag) {
                    //                            topHLine.setSelected(true);
                    //                            repaintSelectedHLine(topHLine);
                    //                        }
                    //                    }
                }
                else if(LAYER_MODE == 1) {
                    rectP1 = convertLBPoint(e.getPoint());

                    RECT_OP_MODE = getRectOpMode(rectP1);

                    GLRectangle rect = getSelectedRect(rectP1);
                    int side = rect.getSelectedSide(rectP1);

                    updateCursor(side);
                }
                else {

                }

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(LAYER_MODE == 0) {
                    GLHLine topHLine = getTopHLine();
                    if(topHLine.isSelected()) {
                        topHLine.setSelected(false);
                        topHLine.setY(convertLBY(e.getY()));

                        repaintDefaultHLine(topHLine);
                    }
                }else if(LAYER_MODE == 1) {
                    if(rectP1 == null) {
                        return;
                    }

                    if(RECT_OP_MODE == 0) {
                        GLRectangle selRect = getSelectedRect(rectP1);
                        int side = selRect.getSelectedSide(rectP1);
                        if(side == Constants.RECT_NO_SIDE_SELECTED) {
                            throw new RuntimeException("*** selected side is invalid ***");
                        }

                        rectP2 = convertLBPoint(e.getPoint());

                        if(side == Constants.RECT_TOP_SIDE_SELECTED) {
                            int y1 = selRect.getLeftTop().y; // relative to left-bottom corner
                            int y2 = selRect.getRightBottom().y;
                            int y;
                            if(y1 > y2) {
                                y = rectP2.y > y2 ? rectP2.y : y2 + 1;
                                selRect.setY1(y);
                            }else {
                                y = rectP2.y > y1 ? rectP2.y : y1 + 1;
                                selRect.setY2(y);
                            }
                        }
                        else if(side == Constants.RECT_RIGHT_SIDE_SELECTED) {
                            CustomCanvas.this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                        }
                        else if(side == Constants.RECT_BOTTOM_SIDE_SELECTED) {
                            CustomCanvas.this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                        }
                        else if(side == Constants.RECT_LEFT_SIDE_SELECTED) {
                            CustomCanvas.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                        }
                        else {
                            throw new RuntimeException("*** selected side is invalid ***");
                        }

                        repaintRect(selRect);

                        CustomCanvas.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                        rectP1 = null;
                        rectP2 = null;
                        RECT_OP_MODE = -1;
                    }
                    else if(RECT_OP_MODE == 1) { // CREATE
                        rectP2 = convertLBPoint(e.getPoint());
                        GLRectangle rectObj = new GLRectangle(null);
                        rectObj.setLeftTop(rectP1);
                        rectObj.setRightBottom(rectP2);
                        addRect(rectObj);

                        repaintRect(rectObj);

                        rectP1 = null;
                        rectP2 = null;
                        RECT_OP_MODE = -1;
                    }
                    else {
                        //
                    }
                }
                else {

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
                if(LAYER_MODE == 0) {
                    GLHLine topHLine = getTopHLine();
                    if(topHLine.isSelected()) {
                        topHLine.setY(convertLBY(e.getY()));

                        repaintSelectedHLine(topHLine);
                    }
                }
                else if(LAYER_MODE == 1) {
                    if(rectP1.equals(rectP2)) {
                        return;
                    }

                    if(RECT_OP_MODE == 0) { // SELECT RECT

                    }
                    else if(RECT_OP_MODE == 1) { // CREATE RECT
                        rectP2 = convertLBPoint(e.getPoint());
                        GLRectangle rectObj = new GLRectangle(null);
                        rectObj.setLeftTop(rectP1);
                        rectObj.setRightBottom(rectP2);
                        addRect(rectObj);
                        repaintRect(rectObj);
                    }
                    else {
                        //
                    }
                }
                else {

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(LAYER_MODE == 0) {
                    GLHLine topHLine = getTopHLine();
                    //                    if(!topHLine.isSelected()) {
                    // convert y relative to left-bottom corner
                    boolean flag = topHLine.canSelected(convertLBPoint(e.getPoint()));
                    if(flag) {
                        topHLine.setSelected(true);
                        repaintSelectedHLine(topHLine);
                    }else {
                        topHLine.setSelected(false);
                        repaintDefaultHLine(topHLine);
                    }
                    //                    }
                }
                else if(LAYER_MODE == 1) {

                }
                else {

                }
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

    public List getRectLayer() {
        return rectLayer;
    }

    public void addRect(IGLObject obj) {
        rectLayer.add(obj);
        if(immediateRefresh) {
            refresh();
        }
    }

    public void removeRect(IGLObject obj) {
        rectLayer.remove(obj);
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

    public void repaintRect(GLRectangle rect) {
        refresh();
    }

    public GLRectangle getRect(Point p1, Point p2) {
        for (IGLObject obj : rectLayer)
        {
            if(obj instanceof GLRectangle) {
                GLRectangle rect = (GLRectangle)obj;
            }
        }
        return null;
    }

    public int getRectOpMode(Point p) {
        List li = getRectLayer();
        for(int i=li.size()-1; i>=0; i--) {
            GLRectangle rect = (GLRectangle)li.get(i);
            if(rect.canSelected(p)) {
                return 0; // SELECT RECT MODE
            }
        }
        return 1; // CREATE RECT MODE
    }

    //    public List getSelectedRects(Point p) {
    //        List result = new ArrayList();
    //        List li = getRectLayer();
    //        for(int i=0; i<li.size()-1; i++) {
    //            GLRectangle rect = (GLRectangle)li.get(i);
    //            if(rect.canSelected(p)) {
    //                rect.setSelected(true);
    //                result.add(rect);
    //            }
    //        }
    //        return result;
    //    }

    public GLRectangle getSelectedRect(Point p) {
        List li = getRectLayer();
        for(int i=li.size()-1; i>=0; i--) {
            GLRectangle rect = (GLRectangle)li.get(i);
            if(rect.canSelected(p)) {
                return rect;
            }
        }
        return null;
    }

    public void updateCursor(int selectedSide) {
        if(selectedSide == Constants.RECT_TOP_SIDE_SELECTED) {
            CustomCanvas.this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        else if(selectedSide == Constants.RECT_RIGHT_SIDE_SELECTED) {
            CustomCanvas.this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        }
        else if(selectedSide == Constants.RECT_BOTTOM_SIDE_SELECTED) {
            CustomCanvas.this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        }
        else if(selectedSide == Constants.RECT_LEFT_SIDE_SELECTED) {
            CustomCanvas.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        else {
            throw new RuntimeException("*** selected side is invalid ***");
        }
    }

}
