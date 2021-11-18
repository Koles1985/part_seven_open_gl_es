package com.koles.part_7opengles;

public class Launcher extends GLGame{
    @Override
    public Screen getStartScreen() {
        return new TextureTriangleScreen(this);
    }
}
