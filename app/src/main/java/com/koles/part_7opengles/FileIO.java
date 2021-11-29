package com.koles.part_7opengles;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileIO implements InOut{
    private String externalPath;
    private static String internalPath;
    private AssetManager assetManager;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public FileIO(AssetManager assetManager){
        this.assetManager = assetManager;
        externalPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator;

        //разобраться как создать свою папку и получить к ней путь
        internalPath = Environment.getDataDirectory().getAbsolutePath() + File.separator;
        System.out.println(internalPath);
    }

    @Override
    public InputStream readAsset(String assetName){
        InputStream in = null;
        try{
            in = assetManager.open(assetName);
        }catch(IOException e){
            Log.d("readAsset", "file - " + assetName + " not found! - "
                    + e.getMessage());
        }
        return in;
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        InputStream in = null;
        try{
            in = new FileInputStream(externalPath + fileName);
        }catch(IOException e){
            Log.d("ReadFile", "externalPath not found - " + e.getMessage());
            try{
                in = new FileInputStream(internalPath + fileName);
            }catch(IOException e1){
                Log.d("ReadFile", "file - " + fileName + " not found! - "
                            + e1.getMessage());
            }
        }
        return in;
    }

    @Override
    public OutputStream writeFile(String fileName){
        OutputStream out = null;
        try{
            out = new FileOutputStream(externalPath + fileName);
        }catch(IOException e) {
            Log.d("WriteFile", "externalPath not found" + e.getMessage());
            try {
                out = new FileOutputStream(internalPath + fileName);
            } catch (IOException e1) {
                Log.d("WriteFile", "some thing go wrong");
            }
        }
        return out;
    }

}
