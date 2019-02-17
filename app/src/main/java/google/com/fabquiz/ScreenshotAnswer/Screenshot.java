package google.com.fabquiz.ScreenshotAnswer;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import java.util.Locale;

import github.nisrulz.screenshott.ScreenShott;

/**
 * Created by pratik on 29-Jan-18.
 */
public class Screenshot {
    public static Bitmap takscreenshot(View v, int mWidth, int mHeight){
        v.setDrawingCacheEnabled(true);
        v.layout(0, 0, mWidth, mHeight);
        v.buildDrawingCache(true);
        Bitmap b= Bitmap.createScaledBitmap(v.getDrawingCache(),mWidth,mHeight,true);
        v.setDrawingCacheEnabled(false);
        return  b;
    }
    public static Bitmap takescreenshotofRoomView(View v, int mWidth, int mHeight){
        return takscreenshot(v.getRootView(),mWidth,mHeight);

    }
}
