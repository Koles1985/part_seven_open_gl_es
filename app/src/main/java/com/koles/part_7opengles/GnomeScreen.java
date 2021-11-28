package com.koles.part_7opengles;

import javax.microedition.khronos.opengles.GL10;

public class GnomeScreen extends Screen{
    static final int NUM_GNOMES = 100;
    GLGraphics glGraphics;
    Texture gnomeTexture;
    Vertices gnomeModel;
    Gnome[] gnomes;
    FPSCounter fpsCounter;

    public GnomeScreen(Game game) {
        super(game);
        fpsCounter = new FPSCounter();
        glGraphics = game.getGLGraphics();
        gnomeTexture = new Texture(game, "gnome.png");
        gnomeModel = new Vertices(glGraphics, 4, 12, false, true);
        gnomeModel.setVertices(new float[]{
                -16, -16, 0, 1,
                16, - 16, 1, 1,
                16, 16, 1, 0,
                -16, 16, 0, 0
        }, 0, 16);

        gnomeModel.setIndices(new short[]{
                0, 1, 2,
                2, 3, 0
        }, 0, 6);

        gnomes = new Gnome[100];
        for(int i = 0; i < gnomes.length; i++){
            gnomes[i] = new Gnome();
        }
    }

    @Override
    public void resume() {
        GL10 gl = glGraphics.getGl10();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glClearColor(1, 0, 0, 1);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, 320, 0, 480, 1, -1);

        gnomeTexture.reload();
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gnomeTexture.bind();

        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float deltaTime) {
        for(int i = 0; i < NUM_GNOMES; i++){
            gnomes[i].update(deltaTime);
        }
    }

    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGl10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        gnomeModel.bind();
        for(int i = 0; i < NUM_GNOMES; i++){
            gl.glLoadIdentity();
            gl.glTranslatef(gnomes[i].x, gnomes[i].y, 0);
            //gl.glRotatef(45, 0, 0, 1);
            //gl.glScalef(2,0.5f, 0);
            gnomeModel.draw(GL10.GL_TRIANGLES, 0, 6);
        }
        gnomeModel.unbind();

        fpsCounter.logFrame();
    }

    @Override
    public void dispose() {

    }
}
