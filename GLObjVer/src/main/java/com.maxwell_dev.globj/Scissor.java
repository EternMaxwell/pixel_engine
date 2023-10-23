package com.maxwell_dev.globj;

import java.util.LinkedList;

public class Scissor {
    private final RecBinding scissor;
    private final LinkedList<RecBinding> scissorIndexed;
    private final boolean indexed;
    
    public class Rectangle {
        private int x, y, width, height;

        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int x() {
            return x;
        }
        
        public int y() {
            return y;
        }

        public int width() {
            return width;
        }

        public int height() {
            return height;
        }

        public Rectangle set(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }
    }

    public class RecBinding {
        private Rectangle rectangle;

        public Rectangle get() {
            return rectangle;
        }

        public RecBinding set(Rectangle rectangle) {
            this.rectangle = rectangle;
            return this;
        }
    }

    public Scissor(boolean indexed) {
        this.indexed = indexed;
        if (indexed) {
            scissor = null;
            scissorIndexed = new LinkedList<RecBinding>();
        } else {
            scissor = new RecBinding();
            scissorIndexed = null;
        }
    }
    
    public RecBinding scissor() {
        return scissor;
    }

    public RecBinding scissor(int index) {
        if (index > this.scissorIndexed.size())
            for (int i = this.scissorIndexed.size(); i <= index; i++)
                this.scissorIndexed.add(new RecBinding());
        return scissorIndexed.get(index);
    }

    public LinkedList<RecBinding> scissorIndexed() {
        return scissorIndexed;
    }
    
    public boolean indexed() {
        return indexed;
    }
}
