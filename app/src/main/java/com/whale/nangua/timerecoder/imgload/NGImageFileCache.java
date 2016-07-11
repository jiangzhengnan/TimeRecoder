package com.whale.nangua.timerecoder.imgload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.whale.nangua.timerecoder.utils.DevicesUtils;
import com.whale.nangua.timerecoder.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * 文件缓存类
 * Created by nangua on 2016/7/8.
 */
public class NGImageFileCache {
    private int MB = 1024 * 1024;
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 60; //自定义文件最大容量60MB
    private static final String CACHE_SUFFIX = ".cache";
    private static final int CACHE_SIZE = 30; //30MB
    private static final float REDUCTION_RATIO = 0.5f;

    protected NGImageFileCache() {
        //先做清空缓存判断
        removeChache(FileUtils.getImgDirectory());
    }

    private boolean removeChache(String dirPath) {
        final File dir = new File(dirPath);
        final File[] files = dir.listFiles();
        if (files == null) {
            return true;
        }

        //如果存储设备不存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }

        int dirSize = 0;    //缓存文件总数
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(CACHE_SUFFIX)) {
                dirSize += files[i].length();
            }
        }

        //内存空间判断,按从新到旧与指定比例缩减
        if (dirSize > CACHE_SIZE * MB
                || FREE_SD_SPACE_NEEDED_TO_CACHE > DevicesUtils.freeSpaceOnSd()) {
            final int removeFactor = (int) ((REDUCTION_RATIO * files.length) + 1);
            Arrays.sort(files,new FileUtils.FileLastModifSort());
            for (int i = 0; i<removeFactor;i++) {
                if (files[i].getName().contains(CACHE_SUFFIX)) {
                    files[i].delete();
                }
            }
        }

        //如果剩余内存小于缓存空间大小
        if (DevicesUtils.freeSpaceOnSd() <= CACHE_SIZE*MB) {
            return false;
        }
        return  true;
    }

    public Bitmap getImage(final String url) {
        final String path = FileUtils.getImgDirectory() + "/" +
                FileUtils.convertUrlToFileName(url);
        final File file = new File(path);
        if (file.exists()) {
            try {
                final Bitmap bmp = BitmapFactory.decodeFile(path);
                if (bmp == null) {
                    file.delete();
                } else {
                    FileUtils.convertUrlToFileName(url);
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 存储图片
     * @param bitmap
     * @param url
     */
    public void saveBmp(Bitmap bitmap, String url) {
        if (bitmap == null) {
            return;
        }
        if (FREE_SD_SPACE_NEEDED_TO_CACHE*MB > DevicesUtils.freeSpaceOnSd()) {
            return;
        }
        saveBmpToSd(url, bitmap);
    }

    /**
     * 存储图片到SD卡
     * @param url
     * @param bitmap
     */
    public void saveBmpToSd(String url,Bitmap bitmap) {
        try {
            //判断SD卡是否可用
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String fileDirectory = FileUtils.getImgDirectory();
                File dir = new File(fileDirectory);
                //如果文件夹不存在
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = FileUtils.convertUrlToFileName(url);
                File file = new File(fileDirectory + "/" + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                    final OutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();

                }
            }

        } catch (Exception e) {

        }
    }

    /**
     * 删除内存中图片
     * @param url
     * @return
     */
    public boolean deleteImage(final String url) {
        final String path = FileUtils.getImgDirectory() + "/" + FileUtils.convertUrlToFileName(url);
        final File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


}
