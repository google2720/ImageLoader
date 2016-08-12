package imageengine;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import imageengine.disklrucache.DiskLruCache;

/**
 * Created by zhangjunjun on 2016/8/12.
 */
public class DiskCache {

    private DiskLruCache mDiskLruCache=null;


    public DiskCache(Context context) {
        initDiskCache(context);
    }


    private void initDiskCache(Context context) {
        //public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize)
        //第一个参数指定的是数据的缓存地址
        //第二个参数指定当前应用程序的版本号：每当版本号改变，缓存路径下存储的所有数据都会被清除掉,DiskLruCache认为当应用程序有版本更新的时候，所有的数据都应该从网上重新获取
        //第三个参数指定同一个key可以对应多少个缓存文件，基本都是传 1
        //第四个参数指定最多可以缓存多少字节的数据。通常传入10M的大小就够了,根据自身调节
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        String key = hashKeyForDisk(imageUrl);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void put(String url, Bitmap bimap)
    {

    }


    public Bitmap get(String url)
    {
        Bitmap bimap=null;

        return  bimap;
    }

    /**
     * 获取缓存地址
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径: /sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //否则就调用getCacheDir()方法来获取缓存路径 :/data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 取得APP版本号
     * @param context
     * @return
     */
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 生成MD5值
     * @param key
     * @return
     */
    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
