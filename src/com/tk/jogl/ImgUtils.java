package com.tk.jogl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.tk.img.ImageCanvas;

public class ImgUtils {

    //https://github.com/sgothel/jogl/tree/master/src/jogl/classes/com/jogamp/opengl/util/awt
    public static void createThumbnail(BufferedImage img, int width) {
        BufferedImage bi = com.jogamp.opengl.util.awt.ImageUtil.createThumbnail(img, 100);
        final JFrame frame = new JFrame("Load Image Sample");
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.white);
        ImageCanvas imgcanvas = new ImageCanvas();
        com.jogamp.opengl.util.awt.ImageUtil.flipImageVertically(bi);
        imgcanvas.setImage(bi);
        imgcanvas.setImageWidth(100);
        imgcanvas.setImageHeight(100);
        c.add(imgcanvas, BorderLayout.CENTER);
        imgcanvas.refresh();
        frame.setVisible(true);
    }

}
