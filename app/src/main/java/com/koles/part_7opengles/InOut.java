package com.koles.part_7opengles;

import java.io.IOException;
import java.io.InputStream;

public interface InOut {

    InputStream readAsset(String assetName) throws IOException;
}
