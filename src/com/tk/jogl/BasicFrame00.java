package com.tk.jogl;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.tk.img.ImageUtils;

//http://www.java-tips.org/other-api-tips-100035/112-jogl/1689-outline-fonts-nehe-tutorial-jogl-port.html

// http://blog.csdn.net/ryfdizuo/article/details/45442745 ****

// http://blog.csdn.net/vagrxie/article/details/5094735

// http://www.yiibai.com/jogl/jogl_installation.html

// http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html

// http://bbs.csdn.net/topics/390408888?page=1

public class BasicFrame00 implements GLEventListener {

    private static GLAutoDrawable drawable;
    private static GLContext context;

    private static int x = 10;
    private static int y;

    @Override
    public void init(GLAutoDrawable drawable) {
        System.out.println("init");

        BasicFrame00.drawable = drawable;
        BasicFrame00.context = drawable.getContext();


        final GL2 gl = drawable.getGL().getGL2();
        // Set "clearing" or background color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black and opaque
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        System.out.println("display");
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        //        gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Clear the color buffer (background)
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);      // To operate on Model-View matrix
        gl.glLoadIdentity();                // Reset the model-view matrix


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
        //        String str = "jogl test JOGL����";
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

            //            byte[] pixels8 = ImageUtils.create8BitImage(imgWidth, imgHeight, pixels);
            byte[] pixels8 = ImageUtils.create8BitImageDESC(imgWidth, imgHeight, pixels);




            int w = imgWidth;
            int h = imgHeight;

            Buffer imgBuffer = ByteBuffer.wrap(pixels8);

            //            Buffer imgData2 = ShortBuffer.wrap(pixels);

            //            gl.glDrawPixels(w, h, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, imgData);

            final GLU glu = new GLU();
            glu.gluOrtho2D(0, 512, 0, 512);
            //            glu.gluOrtho2D(0, 512, 512, 0);

            //            gl.glPixelZoom(0.5f, 0.5f); // ** Zoom
            //            gl.glScalef(0.5f, 0.5f, 0);
            //            gl.glRasterPos2i(0, 512);
            //            gl.glWindowPos2i(0, 0);

            gl.glDrawPixels(w, h, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, imgBuffer);


            // *********** Draw String ************
            //gl.glPixelZoom(0.5f, 0.5f); // ** Zoom
            GLUT glut = new GLUT();
            //            float textPosx = 10f;
            //            float textPosy = 10f;
            float textPosx = 0f;
            float textPosy = 0f;
            // Move to rastering position
            gl.glRasterPos2f(textPosx, textPosy);
            //            gl.glWindowPos2f(textPosx, textPosy);
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            // convert text to bitmap and tell what string to put
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, "Annotation");
            //            gl.glRasterPos2f(-textPosx, -textPosy);

            gl.glRasterPos2f(0, 0);
            //            gl.glWindowPos2f(0, 0);
            // *********** Draw Line ************
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(1.0f, 0.51f, 0.98f); // #FF83FA
            //            gl.glLineWidth(5.0f);
            gl.glVertex2f(0f, 500f); // x, y
            gl.glVertex2f(512f, 500f);

            gl.glEnd();



            System.out.println(BasicFrame00.x + ", " + BasicFrame00.y);
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0f, 1f, 0f); // #FF83FA
            gl.glVertex2f(0f, 0f); // x, y
            gl.glVertex2f(BasicFrame00.x, BasicFrame00.y);
            gl.glEnd();

            gl.glColor3f(1.0f, 1.0f, 1.0f);

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
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println("reshape");
        System.out.println("width: " + width + "; height: " + height);
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
        //        //        gl.glPixelZoom(width/512.0f, height/512.0f); // ** Zoom
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

        // ����ģ�͹۲�����ջ
        //        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        //        gl.glLoadIdentity();
    }

    public static void main(String[] args) {
        // getting the capabilities object of GL2 profile
        new BasicFrame00().todo();
    }

    public void todo() {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        BasicFrame00 b = new BasicFrame00();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(800, 800);

        glcanvas.setBackground(Color.GRAY);


        glcanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {

                System.out.println("mousePressed");

                //                BasicFrame00.context.makeCurrent();
                //                GL2 gl = BasicFrame00.context.getGL().getGL2();
                //                gl.glWindowPos2f(0, 0);
                //                gl.glBegin(GL.GL_LINE_STRIP);
                //                gl.glColor3f(1.0f, 0f, 0f);
                //                gl.glVertex3f(0,0,0);
                //                gl.glVertex3f(100,50,0);
                //                gl.glVertex3f(200,100,0);
                //                gl.glVertex3f(100,150,0);
                //                gl.glEnd();
                //
                //                gl.glColor3f(1.0f, 1f, 1f);
                //
                //                gl.glFlush();
                //
                //                //                BasicFrame00.context.release();
                //
                //
                //                glcanvas.repaint();
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

        glcanvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e)
            {
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                System.out.println(e.getX() + ", " + e.getY());
                BasicFrame00.x = e.getX();
                BasicFrame00.y = e.getY();
                System.out.println("==>" + BasicFrame00.x + ", " + BasicFrame00.y);

                glcanvas.repaint();
            }
        });

        glcanvas.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                // TODO Auto-generated method stub
                //                BasicFrame00.drawable.display();
            }

        });

        // creating frame
        final JFrame frame = new JFrame("Basic Frame - Image & JOGL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        frame.setResizable(false);

        //        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //        frame.setUndecorated(true);
        //        frame.setSize(600, 600);
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e)
            {
                glcanvas.repaint();
            }
        });
        // adding canvas to frame
        Container c = frame.getContentPane();
        //        c.setLayout(new BorderLayout());
        GridLayout layout = new GridLayout(1, 2);
        c.setLayout(layout);
        //        frame.getContentPane().add(glcanvas, BorderLayout.CENTER);
        c.add(glcanvas);
        c.add(new JPanel());
        //        frame.setSize(512 + 8 + 8, 512 + 30 + 8);
        frame.setBackground(Color.white);
        //        frame.setSize(512, 512);
        frame.setBounds(0, 0, 1400, 800);
        //        System.out.println(frame.getContentPane().getPreferredSize());
        //        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}