package com.koles.part_7opengles;

public class Caveman extends  DynamicGameObject {
    public float walkingTime = 0;
    float worldWidth, worldHeight;


    public Caveman(float x, float y, float width, float height, float worldWidth,
                   float worldHeight) {
        super(x, y, width, height);
        this.position.set((float) Math.random() * worldWidth,
                (float) Math.random() * worldHeight);
        this.velocity.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
        this.walkingTime = (float) Math.random() * 10;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(float deltaTime){
        position.add(-velocity.x * deltaTime, velocity.y * deltaTime);
        if(position.x < 0) position.x = worldWidth;
        if(position.x > worldWidth) position.x = 0;
        walkingTime +=deltaTime;
    }

}
