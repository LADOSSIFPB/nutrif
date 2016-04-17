package br.edu.nutrif.view.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.DiaRefeicaoController;
import br.edu.nutrif.controller.PretensaoRefeicaoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.database.dao.PretensaoRefeicaoDAO;
import br.edu.nutrif.entitys.PretensaoRefeicao;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.util.qrcode.Contents;
import br.edu.nutrif.util.qrcode.QRCodeEncoder;
import br.edu.nutrif.util.AndroidUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RefeicaoActivity extends AppCompatActivity {
    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;

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
    @Bind(R.id.pedirBtn)
            Button pedirBtn;
    @Bind(R.id.codeBtn)
            Button codeBtn;

    String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        buildContent();
    }


    public void buildContent() {
        change(false);
        final int position = this.getIntent().getIntExtra("position", -1);

        if (position != -1) {
            PretensaoRefeicaoController.retornarRefeicao(this, position, new Replyable<PretensaoRefeicao>() {
                @Override
                public void onSuccess(PretensaoRefeicao pretencaoRefeicao) {
                    RefeicaoActivity.this.organizarTela(pretencaoRefeicao);
                    change(true);
                }

                @Override
                public void onFailure(Erro erro) {
                    AndroidUtil.showToast(RefeicaoActivity.this, erro.getMensagem());
                    finish();
                }

                @Override
                public void failCommunication(Throwable throwable) {
                    PretensaoRefeicao pretensaoRefeicao = PretensaoRefeicaoDAO.
                            getInstance(RefeicaoActivity.this).find(
                            DiaRefeicaoController.getRefeicoes().get(position).getId());
                    if (pretensaoRefeicao != null) {
                        AndroidUtil.showSnackbar(RefeicaoActivity.this, R.string.recuperaqrcode);
                        RefeicaoActivity.this.organizarTela(pretensaoRefeicao);
                    } else {
                        AndroidUtil.showToast(RefeicaoActivity.this, R.string.erroconexao);
                        finish();
                    }
                }
            });
        }
    }

    public void organizarTela(PretensaoRefeicao pretencaoRefeicao) {
        dia.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getDia().getNome());
        hora_final.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraFinal());
        hora_inicial.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraInicio());
        data.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDataPretensao());
        tipo.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getTipo());

        if(pretencaoRefeicao.getKeyAccess() == null){
            pedirBtn.setVisibility(View.VISIBLE);
            codeBtn.setVisibility(View.GONE);
        }else{
            code = pretencaoRefeicao.getKeyAccess();
            pedirBtn.setVisibility(View.GONE);
            codeBtn.setVisibility(View.VISIBLE);
        }
        change(true);
    }

    public void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

    public void pedirRefeicao(View view) {
        int position = RefeicaoActivity.this.getIntent().getIntExtra("position", -1);

        if (position != -1) {
            change(false);
            PretensaoRefeicaoController.pedirRefeicao(RefeicaoActivity.this, position, new Replyable<PretensaoRefeicao>() {
                @Override
                public void onSuccess(PretensaoRefeicao pretensao) {
                    organizarTela(pretensao);
                }

                @Override
                public void onFailure(Erro erro) {
                    AndroidUtil.showSnackbar(RefeicaoActivity.this, erro.getMensagem());
                    change(true);
                }

                @Override
                public void failCommunication(Throwable throwable) {
                    AndroidUtil.showSnackbar(RefeicaoActivity.this, R.string.erroconexao);
                    change(true);
                }
            });
        }
    }

    public void gerarQRCode(View view) {
        Intent intent = new Intent(RefeicaoActivity.this, QRCodeActivity.class);
        intent.putExtra("qrcode", code);
        startActivity(intent);
    }
}
