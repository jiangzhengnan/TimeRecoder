package com.whale.nangua.timerecoder.imgload;

import android.graphics.Bitmap;

import com.whale.nangua.timerecoder.utils.TextUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nangua on 2016/7/8.
 */
public class NGImageMemoryCache {
    private static final int MAX_CACHE_CAPACITY = 40; //自定义最大容量
    private HashMap<String, Bitmap> hashMap; //缓存集合
    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> mSoftBitmapCache =
            new ConcurrentHashMap<>(MAX_CACHE_CAPACITY);       //线程安全的软引用缓存集合

    /**
     * 初始化
     * 淘汰最老的键
     */
    protected NGImageMemoryCache() {
        //使用LinkedHashMap保证有序读取
        hashMap = new LinkedHashMap<String, Bitmap>(MAX_CACHE_CAPACITY, 0.75f, true) {
            //移除hashmap中最老的键值
            @Override
            protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
                if (size() > MAX_CACHE_CAPACITY) {
                    mSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                    return true; //返回true则移除最老的键值
                } else {
                    return false;
                }
            }
        };
    }

    /**
     * 从内存中获取Bitmap
     * @param url
     * @return
     */
    public Bitmap getBitmapFromCache(String url) {
        if (hashMap != null && hashMap.size() > 0) {
            synchronized (hashMap) {
                try {

                    final Bitmap bitmap = hashMap.get(url);
                    if (bitmap != null) {
                        // hashMap.remove(url);
                        hashMap.put(url, bitmap);//存入内存
                        return bitmap;
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
        }
        //如果hashmap为空，则从软缓存map里取并放入hashmap
        if (mSoftBitmapCache != null && !TextUtils.isEmpty(url)) {
            final SoftReference<Bitmap> bitmapReference = mSoftBitmapCache.get(url);
            if (bitmapReference != null) {
                try {
                    final Bitmap bitmap = bitmapReference.get();
                    if (bitmap != null) {
                        hashMap.put(url, bitmap);
                        mSoftBitmapCache.remove(url);
                        return bitmap;
                    } else {
                        mSoftBitmapCache.remove(url);
                    }
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 添加键值到内存
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url,Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (hashMap) {
                hashMap.put(url,bitmap);
            }
        }
    }

    /**
     * 从内存中删除
     * @param url
     */
    public void deleteBitmapFromCache(String url) {
        synchronized (hashMap) {
            if (hashMap.containsKey(url)) {
                hashMap.remove(url);
            }
        }

        if (mSoftBitmapCache.contains(url)) {
            mSoftBitmapCache.remove(url);
        }
    }

    /**
     * 清空缓存
     */
    public void deleteAllBitmapFromCache() {
        synchronized (hashMap) {
            hashMap.clear();
        }
        mSoftBitmapCache.clear();
    }

}
