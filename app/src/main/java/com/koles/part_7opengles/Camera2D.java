package com.koles.part_7opengles;

import javax.microedition.khronos.opengles.GL10;

public class Camera2D {
    public final Vector2 POSITION;
    public float zoom;
    public final float FRUSTUM_WIDTH;
    public final float FRUSTUM_HEIGHT;
    final GLGraphics GLGRAPHICS;

    public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight){
        this.GLGRAPHICS = glGraphics;
        this.FRUSTUM_WIDTH = frustumWidth;
        this.FRUSTUM_HEIGHT = frustumHeight;
        this.POSITION = new Vector2(frustumWidth / 2, frustumHeight / 2);
        this.zoom = 1.0f;
    }

    public void setViewportAndMatrices(){
        GL10 gl = GLGRAPHICS.getGl10();
        gl.glViewport(0,0, GLGRAPHICS.getWidth(), GLGRAPHICS.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(POSITION.x - FRUSTUM_WIDTH * zoom / 2,
                    POSITION.x + FRUSTUM_WIDTH * zoom / 2,
                    POSITION.y - FRUSTUM_HEIGHT * zoom / 2,
                    POSITION.y + FRUSTUM_HEIGHT * zoom / 2,
                    1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void touchToWorld(Vector2 touch){
        touch.x = (touch.x / (float)GLGRAPHICS.getWidth()) * FRUSTUM_WIDTH * zoom;
        touch.y = (1 - touch.y / (float)GLGRAPHICS.getHeight()) * FRUSTUM_HEIGHT * zoom;

        touch.add(POSITION).sub(FRUSTUM_WIDTH * zoom / 2, FRUSTUM_HEIGHT * zoom / 2);
    }
}
