package com.tk.img.opengl;

import com.jogamp.opengl.GLAutoDrawable;

public class GLEmptyObject implements IGLObject {

    public GLEmptyObject() {
    }

    @Override
    public void draw(GLAutoDrawable glDrawable)
    {
        //do nothing
    }

    @Override
    public String getName()
    {
        return null;
    }

}