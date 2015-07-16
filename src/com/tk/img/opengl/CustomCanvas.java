package com.tk.img.opengl;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class CustomCanvas extends GLCanvas
{
    private List li = new ArrayList();

    private boolean immediateRefresh = true;

    public CustomCanvas(GLCapabilitiesImmutable capabiliteis) {
        super(capabiliteis);
    }

    @Override
    public void addGLEventListener(GLEventListener arg0)
    {
        super.addGLEventListener(arg0);
        ((SceneView)arg0).setCanvas(this);
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

}
