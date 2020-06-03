package br.edu.ladoss.nutrif.view.activitys;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.qrcode.Contents;
import br.edu.ladoss.nutrif.util.qrcode.QRCodeEncoder;

public class QRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        gerandoQrcode(getIntent().getStringExtra("qrcode"));
    }

    public void gerandoQrcode(String str) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(str,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.code);
            myImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            AndroidUtil.showSnackbar(this, R.string.errocode);
        }
    }
}
