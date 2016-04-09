package br.edu.nutrif.view.activitys;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.PretensaoRefeicaoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.PretensaoRefeicao;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.util.qrcode.Contents;
import br.edu.nutrif.util.qrcode.QRCodeEncoder;
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

    @Bind(R.id.dia)
    TextView dia;

    @Bind(R.id.hora_final)
    TextView hora_final;

    @Bind(R.id.hora_inicial)
    TextView hora_inicial;

    @Bind(R.id.tipo)
    TextView tipo;

    @Bind(R.id.data)
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        carregando();
        buildContent();

    }

    public void buildContent() {
        loadLayout.setVisibility(View.VISIBLE);
        final int position = this.getIntent().getIntExtra("position", -1);

        if (position != -1) {
            PretensaoRefeicaoController.retornarRefeicao(this, position, new Replyable<PretensaoRefeicao>() {
                @Override
                public void onSuccess(PretensaoRefeicao pretencaoRefeicao) {
                    dia.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getDia().getNome());
                    hora_final.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraFinal());
                    hora_inicial.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraInicio());

                    java.text.SimpleDateFormat dateformate = new SimpleDateFormat("dd-MM HH:mm:ss");
                    String dataconvertida = dateformate.format(pretencaoRefeicao.getConfirmaPretensaoDia().getDataPretensao());

                    data.setText(dataconvertida);
                    tipo.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getTipo());
                }

                @Override
                public void onFailure(Erro erro) {
                    AndroidUtil.showToast(RefeicaoActivity.this, erro.getMensagem());
                    finish();

                }

                @Override
                public void failCommunication(Throwable throwable) {
                    AndroidUtil.showToast(RefeicaoActivity.this, R.string.erroconexao);
                    finish();
                }
            });
        }
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
        content.setVisibility(content.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    public void carregando(){
        loadLayout.setVisibility(loadLayout.getVisibility() == View.GONE? View.VISIBLE : View.GONE);
        content.setVisibility(content.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
