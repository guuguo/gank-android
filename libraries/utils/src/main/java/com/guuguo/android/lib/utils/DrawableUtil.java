package com.guuguo.android.lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by mimi on 2017-01-06.
 */

public class DrawableUtil {
    private DrawableUtil(){}
    public static Drawable getDrawableWithName(Context context, String resourceName) {
        Resources res = context.getResources();
        int picid = res.getIdentifier(resourceName, "drawable",
                context.getPackageName());
        return res.getDrawable(picid);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }
}
