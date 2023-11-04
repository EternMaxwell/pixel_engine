package com.maxwell_dev.engine.ui;

import com.maxwell_dev.engine.render.Visible;

public abstract class UINode extends Container implements Visible {
    protected UIShape shape;

    public UINode() {
        super();
    }

    public UIShape shape() {
        return shape;
    }

    public abstract boolean clicked();
    public abstract boolean hover();
}
