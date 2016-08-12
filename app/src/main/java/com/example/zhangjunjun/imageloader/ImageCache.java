package com.example.zhangjunjun.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhangjunjun on 2016/8/12.
 */
public class ImageCache {
    LruCache<String,Bitmap> mImageCache;

    public ImageCache() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的的最大内存 : KB
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //取1/4作为缓存
        int cacheSize = maxMemory/4;

        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()* value.getHeight()/1024;
            }
        };
    }

    public void put(String url,Bitmap bimap){
        mImageCache.put(url,bimap);
    }

    public Bitmap get(String url) {
        return mImageCache.get(url);
    }
}
