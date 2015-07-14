package com.tk.jogl;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
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

//http://www.java-tips.org/other-api-tips-100035/112-jogl/1689-outline-fonts-nehe-tutorial-jogl-port.html

// http://blog.csdn.net/ryfdizuo/article/details/45442745 ****

// http://blog.csdn.net/vagrxie/article/details/5094735

// http://www.yiibai.com/jogl/jogl_installation.html

// http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html

// http://bbs.csdn.net/topics/390408888?page=1

public class BasicFrame00 implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        System.out.println("display");
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        //        gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Clear the color buffer (background)
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Draw a Red 1x1 Square centered at origin
        //        gl.glBegin(GL2GL3.GL_QUADS); // Each set of 4 vertices form a quad
        //        gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
        //        gl.glVertex2f(-0.5f, -0.5f); // x, y
        //        gl.glVertex2f(0.5f, -0.5f);
        //        gl.glVertex2f(0.5f, 0.5f);
        //        gl.glVertex2f(-0.5f, 0.5f);
        //
        //        gl.glVertex2f(-0.8f, 0.1f);
        //        gl.glVertex2f(-0.2f, 0.1f);
        //        gl.glVertex2f(-0.2f, 0.7f);
        //        gl.glVertex2f(-0.8f, 0.7f);
        //
        //        gl.glColor3f(0.2f, 0.2f, 0.2f);  // Dark Gray
        //        gl.glVertex2f(-0.9f, -0.7f);
        //        gl.glColor3f(1.0f, 1.0f, 1.0f);  // White
        //        gl.glVertex2f(-0.5f, -0.7f);
        //        gl.glColor3f(0.2f, 0.2f, 0.2f);  // Dark Gray
        //        gl.glVertex2f(-0.5f, -0.3f);
        //        gl.glColor3f(1.0f, 1.0f, 1.0f);  // White
        //        gl.glVertex2f(-0.9f, -0.3f);
        //        gl.glEnd();

        //        TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 12));
        //        textRenderer.beginRendering(900, 700);
        //        textRenderer.setColor(Color.YELLOW);
        //        textRenderer.setSmoothing(true);
        //
        //        //        DPoint pt = new DPoint(200, 200);
        //        //        textRenderer.draw("Hello world!!", (int) pt.x, (int) pt.y);
        //        Point pt = new Point(200, 200);
        //        textRenderer.draw("Hello world!!", pt.x, pt.y);
        //        textRenderer.endRendering();


        //        GLUT glut = new GLUT();
        //        gl.glColor3f(1.0f, 1.0f, 1.0f);  // White
        //
        //        String str = "jogl test JOGL≤‚ ‘";
        //        int font = GLUT.STROKE_MONO_ROMAN;
        //        float width = glut.glutStrokeLength(font, str);
        //        gl.glTranslatef(-width / 2f, 0, 0);
        //        // Render The Text
        //        for (int i = 0; i < str.length(); i++) {
        //            char c = str.charAt(i);
        //            glut.glutStrokeCharacter(font, c);
        //        }


        // Ref: http://bbs.csdn.net/topics/390408888?page=1


        int defaultWinWidth = 1 << 12;

        int imgWidth = 512;
        int imgHeight = 512;

        try {
            short[] pixels = ImageUtils.read16bitImage(new FileInputStream(new File("D:/image0001.img")), imgWidth, imgHeight);
            double mean = ImageUtils.means(pixels);
            int levValue = (int)mean;
            int winValue = defaultWinWidth;
            ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);

            System.out.println("levValue: " + levValue + ", winValue: " + winValue);

            byte[] pixels8 = ImageUtils.create8BitImage(imgWidth, imgHeight, pixels);

            int w = imgWidth;
            int h = imgHeight;

            Buffer imgBuffer = ByteBuffer.wrap(pixels8);

            //            Buffer imgData2 = ShortBuffer.wrap(pixels);

            //            gl.glDrawPixels(w, h, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, imgData);

            final GLU glu = new GLU();
            glu.gluOrtho2D(0, 512, 0, 512);


            //            gl.glPixelZoom(0.5f, 0.5f); // ** Zoom

            //            gl.glRasterPos2i(256, 256);
            //            gl.glWindowPos2i(0, 0);
            //            gl.glRotatef(180f, 1.0f, 1.0f, 0.0f);
            //            gl.glTranslatef(256, 256, 0);
            //            gl.glRasterPos2i(0, 0);

            gl.glDrawPixels(w, h, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, imgBuffer);

            //            glu.gluLookAt(0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0, 1.0 ,0.0);

            //            gl.glRotatef(90f, 0.0f, 1.0f, 0.0f);

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
        //        System.out.println("reshape");
        //        System.out.println("width: " + width + "; height: " + height);
        //        final GL2 gl = drawable.getGL().getGL2();
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
        BasicFrame00 b = new BasicFrame00();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(512, 512);
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