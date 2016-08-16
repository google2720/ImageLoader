package imageengine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhangjunjun on 2016/8/16.
 */
public class MD5Util {

    private static MD5Util md5Util;


    public static MD5Util getInstance()
    {
        if(md5Util==null)
        {
            md5Util = new MD5Util();
        }
        return md5Util;
    }

    /**
     * 生成MD5值
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
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

    private  String bytesToHexString(byte[] bytes) {
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
