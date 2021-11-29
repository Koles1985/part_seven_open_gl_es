package com.koles.part_7opengles;

import android.view.View.OnTouchListener;

import java.util.List;

public interface TouchInput extends OnTouchListener {
    class TouchEvent{
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_MOVED = 2;

        private int type;
        private int x;
        private int y;
        private int pointer;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getPointer() {
            return pointer;
        }

        public void setPointer(int pointer) {
            this.pointer = pointer;
        }
    }

    boolean isTouched(int pointer);
    int touchX(int pointer);
    int touchY(int pointer);
    List<TouchEvent> getTouchEvents();
}