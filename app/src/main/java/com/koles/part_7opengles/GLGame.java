package com.koles.part_7opengles;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLGame extends Activity implements Game, Renderer {
    enum GlGameState{
        Initialisation,
        Running,
        Paused,
        Finished,
        Idle
    }
    InOut inOut;
    GLSurfaceView glView;
    GLGraphics glGraphics;
    Screen screen;
    GlGameState state = GlGameState.Initialisation;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    TouchInput touchInput;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        glGraphics = new GLGraphics(glView);
        inOut = new FileIO(getAssets());
        touchInput = new TouchEventHandler(this, glView, 1, 1);
        setContentView(glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    protected void onPause() {
        synchronized (stateChanged){
            if(isFinishing()){
                state = GlGameState.Finished;
            }else{
                state = GlGameState.Paused;
            }
            while (true){
                try{
                    stateChanged.wait();
                    break;
                }catch(InterruptedException e){
                    System.out.println("GLGame.onPause.exception = " + e.getMessage());
                }
            }
        }
        glView.onPause();
        super.onPause();
    }

    @Override
    public void setScreen(Screen screen){
        if(screen == null){
            throw new IllegalArgumentException("Screen must not be null");
        }
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getStartScreen() {
        return  null;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public GLGraphics getGLGraphics() {
        return glGraphics;
    }

    @Override
    public InOut getFileIO() {
        return inOut;
    }

    @Override
    public TouchInput getTouchInput() {
        return touchInput;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGl10(gl);

        synchronized (stateChanged){
            if(state == GlGameState.Initialisation){
                screen = getStartScreen();
            }
            state = GlGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GlGameState state = null;

        synchronized (stateChanged){
            state = this.state;
        }
        if(state == GlGameState.Running){
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            screen.update(deltaTime);
            screen.render(deltaTime);

        }

        if(state == GlGameState.Paused){
            screen.pause();
            synchronized (stateChanged){
                this.state = GlGameState.Idle;
                stateChanged.notifyAll();
            }
        }

        if(state == GlGameState.Finished){
            screen.pause();
            screen.dispose();
            synchronized (stateChanged){
                this.state = GlGameState.Idle;
                stateChanged.notifyAll();
            }
        }
    }


}
