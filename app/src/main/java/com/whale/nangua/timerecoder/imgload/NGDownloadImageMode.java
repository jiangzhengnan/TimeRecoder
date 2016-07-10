package com.whale.nangua.timerecoder.imgload;

/**
 * Created by nangua on 2016/7/8.
 */
public class NGDownloadImageMode {
    private String imgUrl;
    private NGDownloadImage.NGImageCallback callback;

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public NGDownloadImage.NGImageCallback getCallback() {
        return callback;
    }

    public void setCallback(NGDownloadImage.NGImageCallback callback) {
        this.callback = callback;
    }

    private Object parent;


}
