package com.whale.nangua.timerecoder.imgload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.whale.nangua.timerecoder.utils.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载类
 * Created by nangua on 2016/7/8.
 */
public class NGDownloadImage {
    private ExecutorService executorService; //线程池服务
    private NGImageMemoryCache imageMemoryCache;
    private NGImageFileCache imageFileCache;
    private NGDownloadImageMode downloadImageMode; //图片实例
    private Map<String, View> taskMap;
    private static NGDownloadImage instance; //自身私有化实例
    private int POOL_SIZE = 5;//线程池自定义大小

    private NGDownloadImage() {
        final int cpuNums = Runtime.getRuntime().availableProcessors();//cpu数
        executorService = Executors.newFixedThreadPool(cpuNums * POOL_SIZE);
        imageMemoryCache = new NGImageMemoryCache();
        imageFileCache = new NGImageFileCache();
        downloadImageMode = new NGDownloadImageMode();
        taskMap = new HashMap<>();
    }

    //获得唯一实例
    public static synchronized NGDownloadImage getInstance() {
        if (instance == null) {
            instance = new NGDownloadImage();
        }
        return instance;
    }

    /**
     * 添加任务
     *
     * @param url
     * @param img
     */
    public void addTask(String url, ImageView img) {
        addTask(null, url, img, null);
    }

    public void addTask(Object parent, String url, View img,
                        NGImageCallback callback) {
        if (img == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            return;
        }

        if (callback != null) {
            downloadImageMode = new NGDownloadImageMode();
            downloadImageMode.setCallback(callback);
            downloadImageMode.setParent(parent);
            downloadImageMode.setImgUrl(url);
            img.setTag(downloadImageMode);
        } else {
            img.setTag(url);
        }

        //生成Bitmap
        final Bitmap bitmap = imageMemoryCache.getBitmapFromCache(url);
        //如果缓存里有
        if (bitmap != null) {
            //如果有实现的回调接口，则用回调接口加载图片
            if (callback != null) {
                callback.imageLoaded(parent, img, bitmap, downloadImageMode);
            } else {
                //如果没有，则直接设置该图片为bitmap
                if (img instanceof ImageView)
                    ((ImageView) img).setImageBitmap(bitmap);
            }
        } else {
            //如果缓存没有这个图片
            if (taskMap != null) {
                //添加到任务集合里去
                synchronized (taskMap) {
                    final String mapKey = Integer.toString(img.hashCode());
                    if (!taskMap.containsKey(mapKey)) {
                        taskMap.put(mapKey, img);
                    }
                }
            }
        }
    }

    public void doTask() {
        if (taskMap == null) {
            return;
        } else {
            synchronized (taskMap) {
                Collection<View> collection = taskMap.values();
                for (View view : collection) {
                    if (view != null) {
                        Object object = view.getTag();
                        String url = "";
                        if (object instanceof NGDownloadImageMode) {
                            url = ((NGDownloadImageMode) object).getImgUrl();
                        } else {
                            url = (String) object;
                        }
                        if (!TextUtils.isEmpty(url)) {
                            loadImage(url, view);
                        }
                    }
                }
            }
        }
    }

    private void loadImage(final String url, final View img) {
        loadImage(url, img, null);
    }

    private void loadImage(final String url, final View img,
                           NGImageCallback callback) {
        executorService.submit(new TaskWithResult(new TaskHandler(url, img,
                callback), url));
    }

    private class TaskWithResult implements Callable<String> {
        private String url;
        private Handler handler;

        public TaskWithResult(Handler handler, String url) {
            this.url = url;
            this.handler = handler;
        }

        @Override
        public String call() throws Exception {
            // TODO Auto-generated method stub
            final Message message = handler.obtainMessage(0, getBitmap(url));
            handler.sendMessage(message);
            return url;
        }
    }

    private class TaskHandler extends Handler {
        private String url;
        private View img;
        private NGImageCallback callback;

        public TaskHandler(String url, View img, NGImageCallback callback) {
            this.url = url;
            this.img = img;
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            final Object object = img.getTag();

            if (object instanceof NGDownloadImageMode) {
                final NGDownloadImageMode imageMode = (NGDownloadImageMode) object;
                imageMode.getCallback().imageLoaded(imageMode.getParent(), img,
                        (Bitmap) msg.obj, imageMode);
                if (taskMap != null) {
                    taskMap.remove(Integer.toString(img.hashCode()));
                }
            } else if (object instanceof String) {
                if (callback != null) {
                    callback.imageLoaded(null, img, (Bitmap) msg.obj, url);
                } else {
                    if (object.equals(url) && msg.obj != null) {
                        final Bitmap bitmap = (Bitmap) msg.obj;
                        if (bitmap != null) {
                            if (img instanceof ImageView) {
                                ((ImageView) img).setImageBitmap(bitmap);
                            }
                        }
                    }
                }
                if (taskMap != null) {
                    taskMap.remove(Integer.toString(img.hashCode()));
                }
            }
        }
    }

    /**
     * @param url
     * @return Bitmap
     */
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = imageMemoryCache.getBitmapFromCache(url);
        if (bitmap == null) {
            bitmap = imageFileCache.getImage(url);
            if (bitmap == null) {
                bitmap = getBitmapFromUrl(url);
                if (bitmap != null) {
                    imageMemoryCache.addBitmapToCache(url, bitmap);
                    imageFileCache.saveBmpToSd(url,bitmap);
                }
            } else {
                imageMemoryCache.addBitmapToCache(url, bitmap);
            }
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromUrl(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface NGImageCallback {
        public void imageLoaded(Object parent, View img, Bitmap imageBitmap,
                                NGDownloadImageMode callBackTag);

        public void imageLoaded(Object parent, View img, Bitmap imageBitmap,
                                String imageUrl);
    }
}
