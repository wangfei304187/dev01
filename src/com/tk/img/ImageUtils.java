package com.tk.img;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.SampleModel;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    private static int min = 0;
    private static int max = 65535;
    private static IndexColorModel defaultColorModel;
    private static SampleModel sampleModel;
    private static boolean intelByteOrder = true;

    private ImageUtils() {
    }

    public static short[] read16bitImage(InputStream is, int w, int h) throws IOException {
        int bytesPerPixel = 2;
        int byteCount = w * h * bytesPerPixel;
        int nPixels = w * h;
        int bufferSize = 8192;

        int pixelsRead;
        byte[] buffer = new byte[bufferSize];
        short[] pixels = new short[nPixels];
        int totalRead = 0;
        int base = 0;
        int count;
        int bufferCount;

        while (totalRead < byteCount) {
            if (totalRead + bufferSize > byteCount)
            {
                bufferSize = byteCount - totalRead;
            }
            bufferCount = 0;
            while (bufferCount < bufferSize) { // fill the buffer
                count = is.read(buffer, bufferCount, bufferSize - bufferCount);
                if (count == -1) {
                    if (bufferCount > 0)
                    {
                        for (int i = bufferCount; i < bufferSize; i++)
                        {
                            buffer[i] = 0;
                        }
                    }
                    totalRead = byteCount;
                    break;
                }
                bufferCount += count;
            }
            totalRead += bufferSize;
            pixelsRead = bufferSize / bytesPerPixel;
            if (ImageUtils.intelByteOrder) {
                for (int i = base, j = 0; i < base + pixelsRead; i++, j += 2)
                {
                    pixels[i] = (short) ((buffer[j + 1] & 0xff) << 8 | buffer[j] & 0xff);
                }
            } else {
                for (int i = base, j = 0; i < base + pixelsRead; i++, j += 2)
                {
                    pixels[i] = (short) ((buffer[j] & 0xff) << 8 | buffer[j + 1] & 0xff);
                }
            }
            base += pixelsRead;
        }
        return pixels;
    }

    public static void setMinAndMax(int min, int max) {
        ImageUtils.min = min;
        ImageUtils.max = max;
    }

    /**
     * Create an 8-bit AWT image by scaling pixels in the range min-max to
     * 0-255.
     */
    public static Image createImage(int w, int h, short[] pixels) {
        byte[] pixels8 = ImageUtils.create8BitImage(w, h, pixels);
        return ImageUtils.createBufferedImage(w, h, pixels8);
    }

    // y = 255(x-min)/(max-min)
    public static byte[] create8BitImage(int w, int h, short[] pixels) {  // ASC using by Java2D
        byte[] pixels8 = null;
        int size = w * h;
        if (pixels8 == null) {
            pixels8 = new byte[size];
        }
        int value = 0;
        for (int i = 0; i < size; i++) {
            int x = pixels[i];
            if (x <= ImageUtils.min) {
                value = 0;
            }
            else if (x >= ImageUtils.max) {
                value = 255;
            }
            else {
                value = (int)(255.0 * (x - ImageUtils.min) / (ImageUtils.max - ImageUtils.min) + 0.5);
            }
            pixels8[i] = (byte) value;
        }
        return pixels8;
    }

    public static byte[] create8BitImageDESC(int w, int h, short[] pixels) {  // DESC using by JOGL glDrawPixels(...)
        byte[] pixels8 = null;
        int size = w * h;
        if (pixels8 == null) {
            pixels8 = new byte[size];
        }
        int value = 0;
        for (int i = size-1; i >= 0; i--) {
            int x = pixels[i];
            if (x <= ImageUtils.min) {
                value = 0;
            }
            else if (x >= ImageUtils.max) {
                value = 255;
            }
            else {
                value = (int)(255.0 * (x - ImageUtils.min) / (ImageUtils.max - ImageUtils.min) + 0.5);
            }
            pixels8[size-1 - i] = (byte) value;
        }
        return pixels8;
    }

    private static Image createBufferedImage(int w, int h, byte[] pixels8) {
        //		SampleModel sm = getIndexSampleModel(w, h);
        //		DataBuffer db = new DataBufferByte(pixels8, w * h, 0);
        //		WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        ColorModel cm = ImageUtils.getDefaultColorModel();
        //		Image image = new BufferedImage(cm, raster, false, null);

        MemoryImageSource imageSource = new MemoryImageSource(w, h, cm, pixels8, 0, w);
        Image image = Toolkit.getDefaultToolkit().createImage(imageSource);

        return image;
    }

    //	private static SampleModel getIndexSampleModel(int w, int h) {
    //		if (sampleModel == null) {
    //			IndexColorModel icm = getDefaultColorModel();
    //			WritableRaster wr = icm.createCompatibleWritableRaster(1, 1);
    //			sampleModel = wr.getSampleModel();
    //			sampleModel = sampleModel.createCompatibleSampleModel(w, h);
    //		}
    //		return sampleModel;
    //	}

    private static IndexColorModel getDefaultColorModel() {
        if (ImageUtils.defaultColorModel == null) {
            byte[] r = new byte[256];
            byte[] g = new byte[256];
            byte[] b = new byte[256];
            for (int i = 0; i < 256; i++) {
                r[i] = (byte) i;
                g[i] = (byte) i;
                b[i] = (byte) i;
            }
            ImageUtils.defaultColorModel = new IndexColorModel(8, 256, r, g, b);
        }
        return ImageUtils.defaultColorModel;
    }

    public static double means(short[] pixels) {
        long lo = 0;
        for (int i = 0; i < pixels.length; i++) {
            lo += pixels[i] & 0xFFFF;
        }
        return lo / (double) pixels.length;
    }

    public static double mean256(short[] imgContentData, int w, int h) {
        double mean256 = 0;
        if (w <= 256 && h <= 256) {
            mean256 = ImageUtils.means(imgContentData);
        } else {
            int size = 256;
            long sum = 0;
            int offsetY = h / 2 - size / 2;
            int offsetX = w / 2 - size / 2;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    sum += imgContentData[w * offsetY + offsetX + col] & 0xFFFF;
                }
                offsetY = offsetY + 1;
            }
            mean256 = sum / (double) (size * size);
        }
        return mean256;
    }

}
