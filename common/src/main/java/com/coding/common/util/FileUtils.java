package com.coding.common.util;

import java.io.File;

/**
 * Created by luoziyihao on 4/24/17.
 */
public class FileUtils {

    public static boolean legalDirectory(File file) {
        return file.exists() && file.isDirectory();
    }

}
