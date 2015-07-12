package com.tk.img;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ImageCanvas extends JComponent {

	private Image img;
	private int w;
	private int h;

	public ImageCanvas() {
	}

	public Image getImage() {
		return img;
	}
	
	public void setImage(Image img) {
		this.img = img;
	}
	
	public void setImageWidth(int w) {
		this.w = w;
	}
	
	public void setImageHeight(int h) {
		this.h = h;
	}
	
//	@Override
//	public void paint(Graphics g) {
//		int w = img.getWidth(null);
//		int h = img.getHeight(null);
////		Image bufferImage = createImage(w, h);
//		Image bufferImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
//		Graphics bufferGraphics = bufferImage.getGraphics();
//		bufferGraphics.drawImage(img, 0, 0, w, h, null);
//		g.drawImage(bufferImage, 0, 0, w, h, null);
//	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, w, h, null);
	}

	public void refresh() {
		repaint();
	}

}
