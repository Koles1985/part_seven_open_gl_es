package com.koles.part_7opengles;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TouchEventHandler implements TouchInput{
    private boolean[] isTouched = new boolean[10];
    private int[]touchX = new int[10];
    private int[]touchY = new int[10];

    float scaleX;
    float scaleY;

    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEventList = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventListBuffer = new ArrayList<TouchEvent>();

    public TouchEventHandler(Context context, View view, float scaleX, float scaleY){
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };

        touchEventPool = new Pool<TouchEvent>(factory, 100);
    }

    @Override
    public boolean isTouched(int pointer) {
        synchronized (this){
            if(pointer < isTouched.length & pointer >= 0){
                return isTouched[pointer];
            }else{
                return false;
            }
        }
    }

    @Override
    public int touchX(int pointer) {
        synchronized (this){
            if(pointer < touchX.length & pointer >=0){
                return touchX[pointer];
            }else{
                return  0;
            }
        }
    }

    @Override
    public int touchY(int pointer) {
        synchronized (this){
            if(pointer < touchY.length & pointer >=0){
                return touchY[pointer];
            }else{
                return 0;
            }
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this){
            for(int i = 0; i < touchEventList.size(); i++){
                touchEventPool.tryAddObject(touchEventList.get(i));
            }

            touchEventList.clear();
            touchEventList.addAll(touchEventListBuffer);
            touchEventListBuffer.clear();
            return touchEventList;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this){
            int action =event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerID = event.getPointerId(pointerIndex);

            TouchEvent touchEvent;

            switch(action){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.setType(TouchEvent.TOUCH_DOWN);
                    touchEvent.setPointer(pointerID);
                    touchEvent.setX((int)(event.getX(pointerIndex) * scaleX));
                    touchX[pointerID] = touchEvent.getX();
                    touchEvent.setY((int)(event.getY(pointerIndex) * scaleY));
                    touchY[pointerID] = touchEvent.getY();
                    isTouched[pointerID] = true;
                    touchEventListBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_POINTER_UP:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.setType(TouchEvent.TOUCH_UP);
                    touchEvent.setPointer(pointerID);
                    touchEvent.setX((int)(event.getX(pointerIndex) * scaleX));
                    touchX[pointerID] = touchEvent.getX();
                    touchEvent.setY((int)(event.getY(pointerIndex) * scaleY));
                    touchY[pointerID] = touchEvent.getY();
                    isTouched[pointerID] = false;
                    touchEventListBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for(int i = 0; i < pointerCount; i++){
                        pointerIndex = i;
                        pointerID = event.getPointerId(pointerIndex);
                        touchEvent = touchEventPool.newObject();
                        touchEvent.setType(TouchEvent.TOUCH_MOVED);
                        touchEvent.setPointer(pointerID);
                        touchEvent.setX((int)(event.getX(pointerIndex) * scaleX));
                        touchX[pointerID] = touchEvent.getX();
                        touchEvent.setY((int)(event.getY(pointerIndex) * scaleY));
                        touchY[pointerID] = touchEvent.getY();
                        isTouched[pointerID] = true;
                        touchEventListBuffer.add(touchEvent);
                    }
                    break;
            }
            return  true;
        }
    }
}
