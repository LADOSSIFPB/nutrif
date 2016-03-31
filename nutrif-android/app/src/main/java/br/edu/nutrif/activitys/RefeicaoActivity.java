package br.edu.nutrif.activitys;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.DiaRefeicaoController;
import br.edu.nutrif.controller.PetensaoRefeicaoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.PretensaoRefeicao;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.qrcode.Contents;
import br.edu.nutrif.qrcode.QRCodeEncoder;
import br.edu.nutrif.util.AndroidUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RefeicaoActivity extends AppCompatActivity {
    @Bind(R.id.loadinglayout)
    LinearLayout loadLayout;

    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.codelayout)
    LinearLayout codelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

    }

    public void on(){
        final int position = this.getIntent().getIntExtra("position",-1);

        PetensaoRefeicaoController.pedirRefeicao(this, position, new Replyable<PretensaoRefeicao>() {
            @Override
            public void onSuccess(PretensaoRefeicao pretencaoRefeicao) {
                gerandoQrcode(pretencaoRefeicao.getKeyAccess());
            }

            @Override
            public void onFailure(Erro erro) {
                AndroidUtil.showSnackbar(RefeicaoActivity.this, erro.getMensagem());
                String msg = DiaRefeicaoController.refeicoes.get(position).getCodigo();
                if (msg != null) {
                    gerandoQrcode(msg);
                }
            }

            @Override
            public void failCommunication(Throwable throwable) {
                AndroidUtil.showSnackbar(RefeicaoActivity.this, R.string.erroconexao);
                String msg = DiaRefeicaoController.refeicoes.get(position).getCodigo();
                if (msg != null) {
                    gerandoQrcode(msg);
                }
            }
        });
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
            alteraView(null);

        } catch (WriterException e) {
            AndroidUtil.showSnackbar(this, R.string.errocode);
        }
    }

    public void alteraView(View view) {
        codelayout.setVisibility(codelayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        content.setVisibility(content.getVisibility() == View.GONE?  View.VISIBLE : View.GONE);
    }
}
