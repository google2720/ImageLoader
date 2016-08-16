package imageengine;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhangjunjun on 2016/8/16.
 */
public class BitMapUtil {

    private static BitMapUtil mapUtil;


    public static BitMapUtil getInstance()
    {
        if(mapUtil==null) {
            mapUtil = new BitMapUtil();
        }
        return mapUtil;
    }


    public Boolean bitMapToStream(Bitmap bitmap, OutputStream outputStream)
    {
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try {
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
