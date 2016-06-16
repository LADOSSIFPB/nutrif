package br.edu.ladoss.nutrif.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Juan on 07/06/2016.
 */

public class StorageUtil {


    public static final String PHOTO_NAME = "profile.png";

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context) throws IOException {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, PHOTO_NAME);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            Log.w("StorageNutrif", "erro ao guardar foto do usuário");
            throw e;
        }
        return mypath.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path, PHOTO_NAME);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
