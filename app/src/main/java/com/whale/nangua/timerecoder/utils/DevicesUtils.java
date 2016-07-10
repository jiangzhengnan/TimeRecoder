package com.whale.nangua.timerecoder.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by nangua on 2016/7/8.
 */
public class DevicesUtils {
    /**
     * 得到sd卡剩余空间
     * @return
     */
    public static int freeSpaceOnSd() {
        long val=0;
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        val=availableBlocks * blockSize;
        return (int) val;
    }
}
