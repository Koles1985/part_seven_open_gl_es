package com.koles.part_7opengles;

public abstract class Screen {
    Game game;

    public Screen(Game game){
        this.game = game;
    }

    public abstract void resume();
    public abstract void pause();
    public abstract void update(float deltaTime);
    public abstract void render(float deltaTime);
    public abstract void dispose();
}
