package br.edu.ladoss.nutrif.view.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.PretensaoRefeicaoDAO;
import br.edu.ladoss.nutrif.model.DiaRefeicao;
import br.edu.ladoss.nutrif.model.PretensaoRefeicao;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RefeicaoActivity extends Activity {
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

    private PretensaoRefeicao pretensaoRefeicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.refeicao));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        buildContent();
    }

    private void buildContent() {
        change(false);
        DiaRefeicao refeicao = (DiaRefeicao) this.getIntent().getSerializableExtra("refeicao");

        if (refeicao != null) {

            if (!retriveFromBd(refeicao)) {
                PretensaoRefeicao pretensaoRefeicao = new PretensaoRefeicao();
                pretensaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().setId(refeicao.getId());
                Call<PretensaoRefeicao> call = ConnectionServer.getInstance().getService().infoRefeicao(
                        PreferencesUtils.getAccessKeyOnSharedPreferences(this),
                        pretensaoRefeicao);

                call.enqueue(new Callback<PretensaoRefeicao>() {
                    @Override
                    public void onResponse(Response<PretensaoRefeicao> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            PretensaoRefeicaoDAO.getInstance(getBaseContext()).insertOrUpdate(response.body());
                            RefeicaoActivity.this.organizarTela(response.body());
                        } else {
                            Erro erro = ErrorUtils.parseError(response, getBaseContext());
                            RefeicaoActivity.this.errorInRequest(erro.getMensagem());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        RefeicaoActivity.this.errorInRequest(getString(R.string.erroconexao));
                    }
                });
            }
        }
    }

    private void errorInRequest(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showToast(RefeicaoActivity.this, message);
                finish();
            }
        });
    }

    private boolean retriveFromBd(DiaRefeicao dia) {
        PretensaoRefeicaoDAO dao = new PretensaoRefeicaoDAO(this);
        PretensaoRefeicao pretensao = dao.find(dia.getId());

        if (pretensao != null) {
            Date now = new Date();
            Date refeicaoDate =  new Date(pretensao.getConfirmaPretensaoDia().getDataPretensao());
                if (!now.after(refeicaoDate)) {
                    organizarTela(pretensao);
                    return true;
                }

        }
        return false;
    }

    private void organizarTela(PretensaoRefeicao pretencaoRefeicao) {
        dia.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getDia().getNome());
        hora_final.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraFinal());
        hora_inicial.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getHoraInicio());

        Date date = new Date(pretencaoRefeicao.getConfirmaPretensaoDia().getDataPretensao());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM");

        data.setText(format.format(date));
        tipo.setText(pretencaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getRefeicao().getTipo());

        this.pretensaoRefeicao = pretencaoRefeicao;

        if (pretencaoRefeicao.getKeyAccess() == null) {
            pedirBtn.setVisibility(View.VISIBLE);
            codeBtn.setVisibility(View.GONE);
        } else {
            pretensaoRefeicao = pretencaoRefeicao;
            pedirBtn.setVisibility(View.GONE);
            codeBtn.setVisibility(View.VISIBLE);
        }
        change(true);
    }

    private void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

    public void pedirRefeicao(View view) {
        change(false);

        PretensaoRefeicao refeicao = new PretensaoRefeicao();
        refeicao.getConfirmaPretensaoDia().getDiaRefeicao().setId(
                pretensaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().getId()
        );

        Call<br.edu.ladoss.nutrif.model.PretensaoRefeicao> call = ConnectionServer
                .getInstance()
                .getService()
                .pedirRefeicao(PreferencesUtils.getAccessKeyOnSharedPreferences(this), refeicao);
        call.enqueue(new Callback<br.edu.ladoss.nutrif.model.PretensaoRefeicao>() {
            @Override
            public void onResponse(Response<br.edu.ladoss.nutrif.model.PretensaoRefeicao> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    PretensaoRefeicaoDAO.getInstance(getBaseContext()).insertOrUpdate(response.body());
                    organizarTela(response.body());
                } else {
                    Erro erro = ErrorUtils.parseError(response,getBaseContext());
                    AndroidUtil.showSnackbar(RefeicaoActivity.this, erro.getMensagem());
                    change(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AndroidUtil.showSnackbar(RefeicaoActivity.this,R.string.erroconexao);
                change(true);
            }
        });
    }

    public void gerarQRCode(View view) {
        Intent intent = new Intent(RefeicaoActivity.this, QRCodeActivity.class);
        intent.putExtra("qrcode", pretensaoRefeicao.getKeyAccess());
        startActivity(intent);
    }
}
