package com.whale.nangua.timerecoder.utils;

import android.os.Environment;

import com.whale.nangua.timerecoder.Config;

import java.io.File;
import java.util.Comparator;

/**
 * Created by nangua on 2016/7/8.
 */
public class FileUtils {

    /**
     * 根据文件更新时间排序
     */
    public static class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 得到图片缓存目录
     * @return
     */
    public static  String getImgDirectory() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        String path = sdcardDir.getParent() + "/" + sdcardDir.getName();
        final String fileDirectory = path + "/"
                + Config.CACHEIR_IMAGE_PATH;
        return fileDirectory;
    }

    /**
     * 从URL中得到文件名
     * @param url
     * @return
     */
    public static String convertUrlToFileName(String url) {
        final String[] strs = url.split("/");
        String fileName = strs[strs.length - 1];
        if (fileName.contains("@")) {
            String[] mystr = url.split("@");
            fileName = mystr[0];
        }
        return fileName;
    }

    /**
     * 更新文件时间
     * @param path
     */
    public void updateFileTime(String path) {
        final File file = new File(path);
        final long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }
}
