package com.tk.img.opengl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
                int y = CustomCanvas.this.getWidth() - e.getY();
                for(int i=0; i<li.size(); i++) {
                    IGLObject obj = li.get(i);
                    if(obj instanceof GLLine) {
                        li.remove(obj);
                    }
                }

                GLHLine newLineObj = new GLHLine();
                newLineObj.setY(y);
                li.add(newLineObj);

                CustomCanvas.this.refresh();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
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

    public void clear() {

    }

    public void setImmediate(boolean immediateRefresh) {
        this.immediateRefresh = immediateRefresh;
    }

    public boolean getImmediate() {
        return immediateRefresh;
    }

}
