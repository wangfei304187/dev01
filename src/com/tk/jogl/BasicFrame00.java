package com.tk.jogl;

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
import com.tk.img.ImageUtils;

// http://www.yiibai.com/jogl/jogl_installation.html

// http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html

public class BasicFrame00 implements GLEventListener {
    @Override
    public void display(GLAutoDrawable drawable) {
        System.out.println("display");
        final GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Clear the color buffer (background)

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

            Buffer imgData = ByteBuffer.wrap(pixels8);

            //            Buffer imgData2 = ShortBuffer.wrap(pixels);

            //            gl.glDrawPixels(w, h, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, imgData);
            gl.glDrawPixels(w, h, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, imgData);

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
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        System.out.println("reshape");
    }

    public static void main(String[] args) {
        // getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        BasicFrame00 b = new BasicFrame00();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(400, 400);
        // creating frame
        final JFrame frame = new JFrame("Basic Frame");
        // adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }
}