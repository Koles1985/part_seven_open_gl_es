package com.koles.part_7opengles;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class GLGraphics {
    private GLSurfaceView glView;
    private GL10 gl10;

    public GLGraphics(GLSurfaceView glView){
        this.glView = glView;
    }

    public void setGl10(GL10 gl){
        this.gl10 = gl;
    }

    public GL10 getGl10(){
        return gl10;
    }

    public int getWidth(){
        return glView.getWidth();
    }

    public int getHeight(){
        return glView.getHeight();
    }
}
