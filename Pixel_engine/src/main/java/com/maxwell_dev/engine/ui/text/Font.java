package com.maxwell_dev.engine.ui.text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Font {
    private java.awt.Font font;

    /**
     * Creates a font from a true type font file
     *
     * @param trueTypeFontPath the path to the true type font file
     */
    public Font(String trueTypeFontPath) {
        try {
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(trueTypeFontPath));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a font from a java.awt.Font
     *
     * @param font the java.awt.Font
     */
    public Font(java.awt.Font font) {
        this.font = font;
    }

    /**
     * Gets the font
     *
     * @return the font
     */
    public java.awt.Font font() {
        return font;
    }

    /**
     * Sets the size of the font
     *
     * @param size the size of the font
     * @return the font
     */
    public Font setSize(float size) {
        font = font.deriveFont(size);
        return this;
    }

    /**
     * Converts a string to a bitmap
     *
     * @param text the text to be converted to a bitmap
     * @return a bitmap of the text
     */
    public BufferedImage bitmapOfText(String text) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        int width = metrics.stringWidth(text);
        int height = metrics.getHeight();
        g.dispose();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(text, 0, metrics.getAscent());
        g.dispose();
        //change the BufferedImage data origin to bottom left
        int[] data = image.getRGB(0, 0, width, height, null, 0, width);
        int[] newData = new int[data.length];
        for (int i = 0; i < height; i++) {
            System.arraycopy(data, i * width, newData, (height - i - 1) * width, width);
        }
        image.setRGB(0, 0, width, height, newData, 0, width);
        return image;
    }
}
