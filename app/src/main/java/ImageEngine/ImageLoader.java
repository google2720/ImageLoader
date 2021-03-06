package imageengine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjunjun on 2016/8/8.
 */
public class ImageLoader {

    MemoryCache memoryCache;

    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }



    public void displayImage(final Context context, final String url, final ImageView imageView)
    {

        //从缓存中取图
       Bitmap bitmap = memoryCache.get(url);
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            return ;
        }

        //如果没有,从网络中下载
        imageView.setTag(MD5Util.getInstance().hashKeyForDisk(url));
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
              final Bitmap bitmap = downloadImage(url);
                if(bitmap==null) {return;}
                if(imageView.getTag().equals(MD5Util.getInstance().hashKeyForDisk(url))) {
                    //在主线程中更新UI
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
                memoryCache.put(url,bitmap);

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
