package com.koles.part_7opengles;

public interface Game {
    void setScreen(Screen screen);
    Screen getStartScreen();
    Screen getCurrentScreen();

    GLGraphics getGLGraphics();
    InOut getFileIO();
    TouchInput getTouchInput();
}
