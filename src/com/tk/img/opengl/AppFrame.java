package com.tk.img.opengl;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class AppFrame extends JFrame {



    private SceneView view;
    private CustomCanvas canvas;

    public static void main(String[] args) {
        final AppFrame f = new AppFrame();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.addObjs();
                f.setVisible(true);
            }
        });
    }

    public AppFrame() {
        setTitle("JOGL Frame");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container c = getContentPane();

        GridLayout layout = new GridLayout(1, 2);
        c.setLayout(layout);

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        canvas = new CustomCanvas(capabilities);
        canvas.setSize(600, 600);
        canvas.setBackground(Color.YELLOW);

        view = new SceneView();
        canvas.addGLEventListener(view);

        c.add(canvas);

        c.add(createPnl());

        setBackground(Color.gray);
        pack();

        doCenter(this);
    }

    public void addObjs() {

        boolean oldFlag = canvas.getImmediate();
        canvas.setImmediate(false);


        GLImage imgObj = new GLImage();
        int iw = 512;
        int ih = 512;
        imgObj.setW(iw);
        imgObj.setH(ih);
        try
        {
            short[] pixels16 = ImageUtils.read16bitImage(new FileInputStream(new File("D:/image0001.img")), iw, ih);
            imgObj.setPixels16(pixels16);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        canvas.addShape(imgObj);


        GLText txtObj = new GLText();
        txtObj.setX(10);
        txtObj.setY(10);
        txtObj.setText("annotation");
        canvas.addShape(txtObj);


        //        GLLine lineObj= new GLLine();
        //        lineObj.setStartPoint(new Point(0, 500));
        //        lineObj.setEndPoint(new Point(511, 500));
        //        canvas.addShape(lineObj);

        GLHLine hlineObj = new GLHLine();
        canvas.addShape(hlineObj);

        canvas.setImmediate(oldFlag);
        canvas.refresh();

    }


    public JPanel createPnl() {
        JPanel pnl = new JPanel();
        pnl.setSize(600, 600);
        return pnl;
    }

    private void doCenter(JFrame frame) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension size = frame.getSize();
        System.out.println(size);
        frame.setLocation(screenSize.width/2 - size.width/2, screenSize.height/2 - size.height/2);
    }
}
