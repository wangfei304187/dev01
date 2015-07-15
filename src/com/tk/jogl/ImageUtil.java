package com.tk.jogl;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/** Utilities for dealing with images. */

public class ImageUtil {
    private ImageUtil() {}

    /** Flips the supplied BufferedImage vertically. This is often a
      necessary conversion step to display a Java2D image correctly
      with OpenGL and vice versa. */
    public static void flipImageVertically(final BufferedImage image) {
        final WritableRaster raster = image.getRaster();
        Object scanline1 = null;
        Object scanline2 = null;

        for (int i = 0; i < image.getHeight() / 2; i++) {
            scanline1 = raster.getDataElements(0, i, image.getWidth(), 1, scanline1);
            scanline2 = raster.getDataElements(0, image.getHeight() - i - 1, image.getWidth(), 1, scanline2);
            raster.setDataElements(0, i, image.getWidth(), 1, scanline2);
            raster.setDataElements(0, image.getHeight() - i - 1, image.getWidth(), 1, scanline1);
        }
    }

    /**
     * Creates a <code>BufferedImage</code> with a pixel format compatible with the graphics
     * environment. The returned image can thus benefit from hardware accelerated operations
     * in Java2D API.
     *
     * @param width The width of the image to be created
     * @param height The height of the image to be created
     *
     * @return A instance of <code>BufferedImage</code> with a type compatible with the graphics card.
     */
    public static BufferedImage createCompatibleImage(final int width, final int height) {
        final GraphicsConfiguration configuration =
                GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().getDefaultConfiguration();
        return configuration.createCompatibleImage(width, height);
    }

    /**
     * Creates a thumbnail from an image. A thumbnail is a scaled down version of the original picture.
     * This method will retain the width to height ratio of the original picture and return a new
     * instance of <code>BufferedImage</code>. The original picture is not modified.
     *
     * @param image The original image to sample down
     * @param thumbWidth The width of the thumbnail to be created
     *
     * @throws IllegalArgumentException If thumbWidth is greater than image.getWidth()
     *
     * @return A thumbnail with the requested width or the original picture if thumbWidth = image.getWidth()
     */
    public static BufferedImage createThumbnail(final BufferedImage image, final int thumbWidth) {
        // Thanks to Romain Guy for this utility
        if (thumbWidth > image.getWidth()) {
            throw new IllegalArgumentException("Thumbnail width must be greater than image width");
        }

        if (thumbWidth == image.getWidth()) {
            return image;
        }

        final float ratio = (float) image.getWidth() / (float) image.getHeight();
        int width = image.getWidth();
        BufferedImage thumb = image;

        do {
            width /= 2;
            if (width < thumbWidth) {
                width = thumbWidth;
            }

            final BufferedImage temp = ImageUtil.createCompatibleImage(width, (int) (width / ratio));
            final Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();
            thumb = temp;
        } while (width != thumbWidth);

        return thumb;
    }

}