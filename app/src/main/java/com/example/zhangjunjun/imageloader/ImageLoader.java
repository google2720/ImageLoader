package com.example.zhangjunjun.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjunjun on 2016/8/8.
 */
public class ImageLoader {
    LruCache<String,Bitmap> mImageCache;
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public ImageLoader()
    {
        initImageCache();
    }

    private void initImageCache() {
        
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                 //重写此方法来衡量每张图片的大小
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    public void displayImage(final String url, final ImageView imageView)
    {
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
              Bitmap bitmap = downloadImage(url);
                if(bitmap==null) {
                    return;
                }
                if(imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);
                }

            }
        });
    }

    public Bitmap downloadImage(String imageUrl)
    {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
