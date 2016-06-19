package br.edu.ladoss.nutrif.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by juan on 12/05/16.
 */
public class ImageUtils {
    protected static final long TAM_IMAGE = 1000000; //Em byte

    public static byte[] BitmapToByteArray(Bitmap imageBitmap) {

        if (imageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,
                    calculaPorcentagem(imageBitmap.getByteCount())
                    , baos);
            byte[] byteData = baos.toByteArray();

            return byteData;
        } else
            return null;

    }

    protected static int calculaPorcentagem(int size){
        float percent = (TAM_IMAGE * 100) / size;
        return percent > 100 ? 100 : (int) percent;
    }

    public static byte[] convertFileToByte(File file){
        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }


    public static Bitmap byteArrayToBitmap(byte[] data) {
        if (data == null)
            return null;
        else
            return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
