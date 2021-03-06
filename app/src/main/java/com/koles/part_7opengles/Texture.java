package com.koles.part_7opengles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class Texture {
    GLGraphics glGraphics;
    InOut inOut;
    String fileName;
    int textureID;
    int minFilter;
    int magFilter;
    float width;
    float height;

    public Texture(Game game, String fileName){
        this.glGraphics = game.getGLGraphics();
        this.inOut = game.getFileIO();
        this.fileName = fileName;
        load();
    }

    private void load(){
        GL10 gl = glGraphics.getGl10();
        int[] textureIds = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        textureID = textureIds[0];

        InputStream in = null;
        try{
            in = inOut.readAsset(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        }catch(IOException e){
            throw new RuntimeException("Couldn't load texture '" +
                    fileName + "'", e);
        }finally{
            if (in != null){
                try{
                    in.close();
                }catch(IOException e){

                }
            }
        }
    }

    public void reload(){
        load();
        bind();
        setFilters(minFilter, magFilter);
        glGraphics.getGl10().glBindTexture(GL10.GL_TEXTURE_2D, 0);
    }

    public void setFilters(int minFilter, int magFilter){
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        GL10 gl = glGraphics.getGl10();
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
    }

    public void bind(){
        GL10 gl = glGraphics.getGl10();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
    }

    public void dispose(){
        GL10 gl = glGraphics.getGl10();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        int[] textureIds = {textureID};
        gl.glDeleteTextures(1, textureIds, 0);
    }
}
