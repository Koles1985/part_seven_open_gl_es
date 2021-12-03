package com.koles.part_7opengles;

public class Rectangle {
    public final Vector2 lowerLeft;
    public float width, height;

    public Rectangle(float x, float y, float width, float height){
        this.lowerLeft = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public boolean overlapRectangles(Rectangle r1, Rectangle r2){
        if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
           r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
           r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
           r1.lowerLeft.y + r1.height > r2.lowerLeft.y){
            return true;
        }else{
            return false;
        }
    }

    public boolean overlapCircleRectangle(Circle c, Rectangle r){
        float closestX = c.center.x;
        float closestY = c.center.y;
        if(c.center.x < r.lowerLeft.x){
            closestX = r.lowerLeft.x;
        }else if(c.center.x > r.lowerLeft.x + r.width){
            closestX = r.lowerLeft.x + r.width;
        }

        if(c.center.y < r.lowerLeft.y){
            closestY = r.lowerLeft.y;
        }else if(c.center.y > r.lowerLeft.y + r.height){
            closestY = r.lowerLeft.y + r.height;
        }

        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
    }
}
