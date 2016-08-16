package imageengine;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by zhangjunjun on 2016/8/16.
 */
public class DoubleCache implements MemoryCache{

    DiskCache mDiskCache;
    ImageCache mImageCache;

    public DoubleCache(Context context) {
        initCache(context);
    }

    private void initCache(Context context) {
        mDiskCache = new DiskCache(context);
        mImageCache = new ImageCache();
    }


    @Override
    public void put(String url, Bitmap bitmap) {
        mDiskCache.put(url,bitmap);
        mImageCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mImageCache.get(url);
        if(bitmap==null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
}
