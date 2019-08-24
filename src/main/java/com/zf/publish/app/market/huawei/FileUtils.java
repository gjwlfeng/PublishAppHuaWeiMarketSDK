package com.zf.publish.app.market.huawei;

import java.io.File;

public class FileUtils {
    public static String getFileSuffix(File file) {
        String name = file.getName();
        int suffixStartPosition = name.lastIndexOf(".");
        if (suffixStartPosition == -1) {
            return null;
        }
        return name.substring(suffixStartPosition + 1);
    }
}
