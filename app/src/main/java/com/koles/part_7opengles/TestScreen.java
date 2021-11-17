package com.koles.part_7opengles;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class TestScreen extends Screen{
    GLGraphics glGraphics;
    Random random;
    public TestScreen(Game game){
        super(game);
        glGraphics = game.getGLGraphics();
        random = new Random();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGl10();

        gl.glClearColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }
}
