package com.tk.jogl;
//import javax.media.opengl.GLAutoDrawable;
//import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLProfile;
//import javax.media.opengl.GLCapabilities;
//import javax.media.opengl.awt.GLCanvas;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

//http://jogamp.org/wiki/index.php/Using_JOGL_in_AWT_SWT_and_Swing#JOGL_in_Swing

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT GLCanvas.
 *
 * @author Wade Walker
 */
public class OneTriangleSwingGLCanvas {

    public static void main( String [] args ) {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );

        glcanvas.addGLEventListener( new GLEventListener() {
            
        	@Override
            public void init( GLAutoDrawable glautodrawable ) {
        		System.out.println(glautodrawable);
        		/*
        		 * AWT-GLCanvas[Realized true,
	jogamp.opengl.windows.wgl.WindowsOnscreenWGLDrawable,
	Factory   jogamp.opengl.windows.wgl.WindowsWGLDrawableFactory@15aad0c,
	handle    0x850117f6,
	Drawable size 624x442 surface[624x442],
	AWT[pos 0/0, size 624x442,
	visible true, displayable true, showing true,
	AWTGraphicsConfiguration[AWTGraphicsScreen[AWTGraphicsDevice[type .awt, connection \Display0, unitID 0, awtDevice Win32GraphicsDevice[screen=0], handle 0x0], idx 0],
	chosen    GLCaps[wgl vid 4 gdi: rgba 8/8/8/0, opaque, accum-rgba 16/16/16/0, dp/st/ms 16/8/0, dbl, mono  , sw, GLProfile[GL2/GL2.sw], on-scr[.]],
	requested GLCaps[rgba 8/8/8/0, opaque, accum-rgba 0/0/0/0, dp/st/ms 16/0/0, dbl, mono  , hw, GLProfile[GL2/GL2.sw], on-scr[.]],
	sun.awt.Win32GraphicsConfig@17550b3[dev=Win32GraphicsDevice[screen=0],pixfmt=4],
	encapsulated WindowsWGLGraphicsConfiguration[DefaultGraphicsScreen[WindowsGraphicsDevice[type .windows, connection decon, unitID 0, handle 0x0, owner false, NullToolkitLock[obj 0x171a9b]], idx 0], pfdID 4, ARB-Choosen false,
	requested GLCaps[rgba 8/8/8/0, opaque, accum-rgba 0/0/0/0, dp/st/ms 16/0/0, dbl, mono  , hw, GLProfile[GL2/GL2.sw], on-scr[.]],
	chosen    GLCaps[wgl vid 4 gdi: rgba 8/8/8/0, opaque, accum-rgba 16/16/16/0, dp/st/ms 16/8/0, dbl, mono  , sw, GLProfile[GL2/GL2.sw], on-scr[.]]]]]]
        		 */
            	System.out.println("init");
            }
        	
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
            	System.out.println(x + ", " + y + "; " + width + ", " + height);
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
                System.out.println("display");
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            	System.out.println("dispose");
            }
        });

        final JFrame jframe = new JFrame( "One Triangle Swing GLCanvas" ); 
        jframe.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                jframe.dispose();
                System.exit( 0 );
            }
        });

        jframe.getContentPane().add( glcanvas, BorderLayout.CENTER );
        jframe.setSize( 640, 480 );
        jframe.setVisible( true );
    }
}