package com.koles.part_7opengles;

import javax.microedition.khronos.opengles.GL10;

public class AnimationScreen extends Screen{
    static final int NUM_CAVEMAN = 10;
    GLGraphics glGraphics;
    Caveman[] caveman;
    SpriteBatcher batcher;
    Camera2D camera;
    Texture texture;
    Animation walkAnim;
    float worldWidt, worldHeight;

    public AnimationScreen(Game game){
        super(game);
        glGraphics = game.getGLGraphics();
        worldWidt = 4.8f;
        worldHeight = 3.2f;
        caveman = new Caveman[NUM_CAVEMAN];
        for(int i = 0; i < NUM_CAVEMAN; i++){
            caveman[i] = new Caveman((float)Math.random(), (float)Math.random(),
                    1, 1, this.worldWidt, this.worldHeight);
        }
        batcher = new SpriteBatcher(glGraphics, NUM_CAVEMAN);
        camera = new Camera2D(glGraphics, worldWidt, worldHeight);
    }

    @Override
    public void resume() {
        texture = new Texture(game, "circle_anim.png");
        walkAnim = new Animation(0.2f,
                new TextureRegion(texture, 0,0, 64, 64),
                new TextureRegion(texture, 64, 0, 64, 64),
                new TextureRegion(texture, 128, 0, 64, 64),
                new TextureRegion(texture, 192, 0, 64, 64));
    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float deltaTime) {
        int len = caveman.length;
        for(int i = 0; i < len; i++){
            caveman[i].update(deltaTime);
        }
    }

    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGl10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.setViewportAndMatrices();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(texture);
        int len = caveman.length;
        for(int i = 0; i < len; i++){
            Caveman cave = caveman[i];
            TextureRegion keyFrame = walkAnim.getKeyFrame(cave.walkingTime,
                    Animation.ANIMATION_LOOPING);
            batcher.drawSprite(cave.position.x, cave.position.y,
                    cave.velocity.x < 0 ? 1 : -1, 1, keyFrame);
        }
        batcher.endBatch();
    }

    @Override
    public void dispose() {

    }
}
