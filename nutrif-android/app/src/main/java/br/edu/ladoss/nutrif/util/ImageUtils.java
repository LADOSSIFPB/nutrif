package br.edu.ladoss.nutrif.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by juan on 12/05/16.
 */
public class ImageUtils {
    public static byte[] drawableToByteArray(Drawable d) {

        if (d != null) {
            Bitmap imageBitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteData = baos.toByteArray();

            return byteData;
        } else
            return null;

    }


    public static Drawable byteToDrawable(byte[] data) {

        if (data == null)
            return null;
        else
            return new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length));
    }
}
