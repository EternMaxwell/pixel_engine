package com.maxwell_dev.globj;

import java.util.LinkedList;

public class WriteMask {
    private final boolean indexed;
    private final ColorMask colorMask;
    private final LinkedList<ColorMask> colorMasks;
    private final DepthMask depthMask;
    
    public class ColorMask {
        private boolean red, green, blue, alpha;

        public ColorMask() {
            red = true;
            green = true;
            blue = true;
            alpha = true;
        }

        public ColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        /**
         * set the color mask
         * @param red whether to write to the red channel
         * @param green whether to write to the green channel
         * @param blue whether to write to the blue channel
         * @param alpha whether to write to the alpha channel
         * @return this ColorMask
         */
        public ColorMask set(boolean red, boolean green, boolean blue, boolean alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
            return this;
        }

        /**
         * set the red channel mask
         * @return the red channel mask
         */
        public boolean red() {
            return red;
        }

        /**
         * set the green channel mask
         * @return the green channel mask
         */
        public boolean green() {
            return green;
        }

        /**
         * set the blue channel mask
         * @return the blue channel mask
         */
        public boolean blue() {
            return blue;
        }

        /**
         * set the alpha channel mask
         * @return the alpha channel mask
         */
        public boolean alpha() {
            return alpha;
        }
    }

    public class DepthMask {
        private boolean depth;

        public DepthMask() {
            depth = true;
        }

        public DepthMask(boolean depth) {
            this.depth = depth;
        }

        /**
         * set the depth mask 
         * @param depth whether to write to the depth buffer
         * @return this DepthMask
         */
        public DepthMask set(boolean depth) {
            this.depth = depth;
            return this;
        }

        /**
         * get the depth mask 
         * @return the depth mask
         */
        public boolean depth() {
            return depth;
        }
    }

    public WriteMask() {
        colorMask = null;
        colorMasks = null;
        depthMask = null;
        indexed = false;
    }

    public WriteMask(boolean color, boolean indexed, boolean depth) {
        this.indexed = indexed;
        if (color) {
            if (indexed) {
                colorMasks = new LinkedList<ColorMask>();
                colorMask = null;
            } else {
                colorMask = new ColorMask();
                colorMasks = null;
            }
        } else {
            colorMask = null;
            colorMasks = null;
        }
        if (depth)
            depthMask = new DepthMask();
        else
            depthMask = null;
    }

    public boolean indexed() {
        return indexed;
    }

    public ColorMask colorMask() {
        return colorMask;
    }

    public ColorMask colorMask(int index) {
        if (index >= colorMasks.size()) {
            for (int i = colorMasks.size(); i <= index; i++)
                colorMasks.add(null);
        }
        if (colorMasks.get(index) == null)
            colorMasks.set(index, new ColorMask());
        return colorMasks.get(index);
    }

    public LinkedList<ColorMask> colorMasks() {
        return colorMasks;
    }

    public DepthMask depthMask() {
        return depthMask;
    }
}