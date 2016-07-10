package com.whale.nangua.timerecoder.imgload;

import android.widget.ImageView;

/**
 * 图片加载框架使用帮助类
 * Created by nangua on 2016/7/8.
 */
public class NGImageloadHelper {
    /**
     * 处理图片
     * @param view
     * @param url
     */
    public static void displayImage(ImageView view,
                                    String url) {
        NGDownloadImage.getInstance().addTask(url,
                view);
        NGDownloadImage.getInstance().doTask();
    }
}
