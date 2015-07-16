package com.tk.img.opengl;

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
				updateHLine(e.getY());
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

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				updateHLine(e.getY());
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

	public void clear() {

	}

	public void setImmediate(boolean immediateRefresh) {
		this.immediateRefresh = immediateRefresh;
	}

	public boolean getImmediate() {
		return immediateRefresh;
	}

	private void updateHLine(int offset) {
		int y = CustomCanvas.this.getHeight() - offset;
		for(int i=li.size()-1; i>=0; i--) {
			IGLObject obj = li.get(i);
			if(obj instanceof GLHLine) {
				li.remove(obj);
			}
		}

		GLHLine newLineObj = new GLHLine();
		newLineObj.setY(y);
		li.add(newLineObj);

		CustomCanvas.this.refresh();
	}
}
