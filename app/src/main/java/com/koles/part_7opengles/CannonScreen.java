package com.koles.part_7opengles;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class CannonScreen extends Screen{
    float FRUSTUM_WIDTH = 4.8f;
    float FRUSTUM_HEIGHT = 3.2f;
    GLGraphics glGraphics;
    Vertices vertices;
    Vector2 cannonPos = new Vector2(2.4f, 0.5f);
    float cannonAngle = 0;
    Vector2 touchPos = new Vector2();
    public CannonScreen(Game game) {
        super(game);
        glGraphics = game.getGLGraphics();
        vertices = new Vertices(glGraphics, 3, 0, false,
                false);
        vertices.setVertices(new float[]{
                -0.5f, -0.5f,
                0.5f, 0.0f,
                -0.5f, 0.5f
        }, 0, 6);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float deltaTime) {
        List<TouchInput.TouchEvent> touchEvents = game.getTouchInput().getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
            TouchInput.TouchEvent event = touchEvents.get(i);

            touchPos.x = (event.getX() / (float)glGraphics.getWidth()) * FRUSTUM_WIDTH;
            touchPos.y = (1 - event.getY() / (float)glGraphics.getHeight()) * FRUSTUM_HEIGHT;

            cannonAngle = touchPos.sub(cannonPos).angle();
        }
    }

    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGl10();
        gl.glViewport(0,0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, FRUSTUM_WIDTH, 0, FRUSTUM_HEIGHT, 1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(cannonPos.x, cannonPos.y, 0);
        gl.glRotatef(cannonAngle, 0, 0, 1);
        vertices.bind();
        vertices.draw(GL10.GL_TRIANGLES, 0, 3);

        vertices.unbind();
    }

    @Override
    public void dispose() {

    }
}
