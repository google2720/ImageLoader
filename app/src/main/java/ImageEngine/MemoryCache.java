package imageengine;

import android.graphics.Bitmap;

/**
 * Created by zhangjunjun on 2016/8/16.
 */
public interface MemoryCache {
    public void put(String url, Bitmap bitmap);
    public Bitmap get(String url);
}
