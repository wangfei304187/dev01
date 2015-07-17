package com.tk.img.opengl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLImage implements IGLObject
{
    private int w;
    private int h;
    private short[] pixels16;

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setPixels16(short[] pixels16) {
        this.pixels16 = pixels16;
    }

    @Override
    public void draw(GLAutoDrawable drawable)
    {
        if(w <= 0 || h <=0 || pixels16 == null) {
            return;
        }

        GL2 gl = drawable.getGL().getGL2();

        int defaultWinWidth = 1 << 12;

        double mean = ImageUtils.means(pixels16);
        int levValue = (int)mean;
        int winValue = defaultWinWidth;
        ImageUtils.setMinAndMax(levValue-winValue/2, levValue + winValue/2);
        BufferedImage bi = ImageUtils.createImage(w, h, pixels16);

        com.jogamp.opengl.util.awt.ImageUtil.flipImageVertically(bi);

        DataBufferByte buf = (DataBufferByte)bi.getData().getDataBuffer();
        byte[] bytes = buf.getData();
        Buffer imgBuffer = ByteBuffer.wrap(bytes);

        gl.glDrawPixels(w, h, GL.GL_LUMINANCE, GL.GL_UNSIGNED_BYTE, imgBuffer);
    }

    @Override
    public String getName()
    {
        return null;
    }

}
