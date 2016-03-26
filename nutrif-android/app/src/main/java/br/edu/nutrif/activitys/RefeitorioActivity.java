package br.edu.nutrif.activitys;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.List;

import br.edu.nutrif.R;
import br.edu.nutrif.adapters.HorarioAdapter;
import br.edu.nutrif.callback.CallbackButton;
import br.edu.nutrif.controller.DiaRefeicaoController;
import br.edu.nutrif.controller.PetensaoRefeicaoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.PretensaoRefeicao;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.qrcode.Contents;
import br.edu.nutrif.qrcode.QRCodeEncoder;
import br.edu.nutrif.util.AndroidUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RefeitorioActivity extends AppCompatActivity implements CallbackButton {
    @Bind(R.id.loadinglayout)
    LinearLayout loadLayout;

    @Bind(R.id.content)
    RelativeLayout content;

    @Bind(R.id.recycle)
    RecyclerView recycle;

    @Bind(R.id.codelayout)
    LinearLayout codelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);
        getSupportActionBar().setLogo(R.drawable.logo);
        DiaRefeicaoController.gerarHorario(this, new Replyable<List<DiaRefeicao>>() {
            @Override
            public void onSuccess(List<DiaRefeicao> diaRefeicaos) {
                montaTabela(diaRefeicaos);
            }

            @Override
            public void onFailure(Erro erro) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, erro.getMensagem());
            }

            @Override
            public void failCommunication(Throwable throwable) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                montaTabela(DiaRefeicaoController.refeicoes);
            }
        });
    }

    public void montaTabela(List<DiaRefeicao> refeicoes) {
        loadLayout.setVisibility(View.GONE);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(gridLayoutManager);
        recycle.setAdapter(new HorarioAdapter(this, refeicoes, this));
        if (recycle.getAdapter().getItemCount() == 0) {
            TextView textView = new TextView(this);
            textView.setText(R.string.nodays);
            content.addView(textView);
        }

    }

    @Override
    public void onClickCallback(View view, final int position) {
        PetensaoRefeicaoController.pedirRefeicao(this, position, new Replyable<PretensaoRefeicao>() {
            @Override
            public void onSuccess(PretensaoRefeicao pretencaoRefeicao) {
                gerandoQrcode(pretencaoRefeicao.getKeyAccess());
            }

            @Override
            public void onFailure(Erro erro) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, erro.getMensagem());
                String msg = DiaRefeicaoController.refeicoes.get(position).getCodigo();
                if (msg != null) {
                    gerandoQrcode(msg);
                }
            }

            @Override
            public void failCommunication(Throwable throwable) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.erroconexao);
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
