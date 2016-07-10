package com.whale.nangua.timerecoder.utils;

/**
 * Created by nangua on 2016/7/8.
 */
public class TextUtils {

    //判断是否为空
    public static boolean isEmpty(String txt) {
        if ((txt == null)||(txt.equals(null))||(txt.length()==0))
            return true;
        else
            return false;
    }
}
