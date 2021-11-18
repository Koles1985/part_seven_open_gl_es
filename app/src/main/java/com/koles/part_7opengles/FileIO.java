package com.koles.part_7opengles;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileIO implements InOut{
    private String externalPath;
    private String internalPath;
    private AssetManager assetManager;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public FileIO(AssetManager assetManager){
        this.assetManager = assetManager;
        externalPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator;

        internalPath = Environment.getStorageDirectory().getAbsolutePath() +
                File.separator;
    }

    @Override
    public InputStream readAsset(String assetName) throws IOException {
        return assetManager.open(assetName);
    }
}
