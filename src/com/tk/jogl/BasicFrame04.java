package com.tk.jogl;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.tk.img.ImageUtils;


public class BasicFrame04 implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        System.out.println("display");
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // Clear the color buffer (background)
        //        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);     // To operate on Model-View matrix
        //        gl.glLoadIdentity();

        final GLU glu = new GLU();
        glu.gluOrtho2D(0, 512, 0, 512);


        int defaultWinWidth = 1 << 12;

        int w = 512; // image width
        int h = 512; // image height

        try {
            short[] pixels = ImageUtils.read16bitImage(new FileInputStream(new File("D:/image0001.img")), w, h);
            double mean = ImageUtils.means(pixels);
            int levValue = (int)mean;
            int winValue = defaultWinWidth;
            ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);

            System.out.println("levValue: " + levValue + ", winValue: " + winValue);

            //            byte[] pixels8 = ImageUtils.create8BitImage(imgWidth, imgHeight, pixels);
            //            byte[] pixels8 = ImageUtils.create8BitImageDESC(imgWidth, imgHeight, pixels);

            //            Image img = ImageUtils.createImage(imgWidth, imgHeight, pixels);
            byte[] pixels8 = ImageUtils.create8BitImage(w, h, pixels);

            SampleModel sm = ImageUtils.getIndexSampleModel(w, h);
            ColorModel cm = ImageUtils.getDefaultColorModel();

            //            WritableRaster raster2 =
            //                    Raster.createInterleavedRaster (DataBuffer.TYPE_BYTE, w, h, 1, null);
            //            ComponentColorModel colorModel= new ComponentColorModel (ColorSpace.getInstance(ColorSpace.CS_sRGB),
            //                    new int[] {8,8,8,8}, true, false,  ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
            //            BufferedImage img2 = new BufferedImage(cm, raster2, false, null);

            DataBuffer db = new DataBufferByte(pixels8, w * h, 0);
            WritableRaster raster = Raster.createWritableRaster(sm, db, null);
            BufferedImage img = new BufferedImage(cm, raster, false, null);
            //            System.out.println(img);

            //            AffineTransform gt = new AffineTransform();
            AffineTransform gt = AffineTransform.getTranslateInstance(0, 0);
            //            gt.translate (0, h);
            AffineTransform flip = AffineTransform.getScaleInstance(-1.0d, 1.0d);
            gt.concatenate(flip);

            Graphics2D g = img.createGraphics();


            //            gt.scale(1d, -1d);
            g.transform (gt);

            g.drawImage (img, null, null);


            DataBufferByte buf = (DataBufferByte)raster.getDataBuffer();
            byte[] bytes = buf.getData();
            Buffer imgBuffer = ByteBuffer.wrap(bytes);

            //            Buffer imgBuffer = ByteBuffer.wrap(pixels8);
            //            DataBufferByte buf = (DataBufferByte)raster2.getDataBuffer();
            //            byte[] bytes = buf.getData();
            //
            //            Buffer imgBuffer = ByteBuffer.wrap(bytes);

            //            gl.glPixelZoom(0.5f, 0.5f); // ** Zoom

            //            gl.glRasterPos2i(256, 256);
            //            gl.glWindowPos2i(0, 0);
            //            gl.glRotatef(180f, 1.0f, 1.0f, 0.0f);
            //            gl.glTranslatef(256, 256, 0);
            //            gl.glRasterPos2i(0, 0);


            //            gl.glColor3f (0.0f, 0.5f, 0.0f);
            //            gl.glRecti (0, 300, 100, 330);
            //            gl.glColor3f (0.0f, 0.0f, 0.0f);
            //            gl.glRasterPos2i (10, 300);

            //            gl.glColor3f (0.0f, 0.0f, 0.0f);
            //            gl.glRasterPos2i (0, 0);

            gl.glDrawPixels(w, h, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, imgBuffer);

            //            gl.glBegin(GL2GL3.GL_QUADS); // Each set of 4 vertices form a quad
            //            gl.glColor3f(0.0f, 1.0f, 0.0f); // Red
            //            gl.glVertex2f(-256f, -256f); // x, y
            //            gl.glVertex2f(256f, -256f);
            //            gl.glVertex2f(256f, 256f);
            //            gl.glVertex2f(-256f, 256f);
            //            gl.glEnd();

            gl.glFlush(); // Render now
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        System.out.println("dispose");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("init");
        final GL2 gl = drawable.getGL().getGL2();
        // Set "clearing" or background color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println("reshape");
        System.out.println("width: " + width + "; height: " + height);
        final GL2 gl = drawable.getGL().getGL2();
        //        final GLU glu = new GLU();
        //        // Compute aspect ratio of the new window
        //        if (height == 0)
        //        {
        //            height = 1;                // To prevent divide by 0
        //        }
        //        //        GLfloat aspect = (GLfloat)width / (GLfloat)height;
        //        float aspect = width*1.0f/height;
        //
        //        // Set the viewport to cover the new window
        //        gl.glViewport(0, 0, width, height);
        //
        //        //         Set the aspect ratio of the clipping area to match the viewport
        //        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);  // To operate on the Projection matrix
        //        gl.glLoadIdentity();             // Reset the projection matrix
        //        if (width >= height) {
        //            // aspect >= 1, set the height from -1 to 1, with larger width
        //            glu.gluOrtho2D(-1.0 * aspect, 1.0 * aspect, -1.0, 1.0);
        //        } else {
        //            // aspect < 1, set the width to -1 to 1, with larger height
        //            glu.gluOrtho2D(-1.0, 1.0, -1.0 / aspect, 1.0 / aspect);
        //        }

    }

    public static void main(String[] args) {
        // getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        BasicFrame04 b = new BasicFrame04();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(512, 512);

        glcanvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e)
            {
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                //                System.out.println(e.getX() + ", " + e.getY());
            }
        });

        // creating frame
        final JFrame frame = new JFrame("Basic Frame - Image & JOGL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        frame.setSize(600, 600);
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e)
            {
                glcanvas.repaint();
            }
        });
        // adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}