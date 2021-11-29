package com.koles.part_7opengles;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface InOut {

    InputStream readAsset(String assetName) throws IOException;
    InputStream readFile(String fileName) throws IOException;
    OutputStream writeFile(String fileName) throws IOException;
}
