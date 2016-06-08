package br.edu.ladoss.nutrif.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by juan on 12/05/16.
 */
public class ImageUtils {
    public static byte[] BitmapToByteArray(Bitmap imageBitmap) {

        if (imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteData = baos.toByteArray();

            return byteData;
        } else
            return null;

    }


    public static Bitmap byteArrayToBitmap(byte[] data) {
        if (data == null)
            return null;
        else
            return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
