package br.edu.ladoss.nutrif.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.controller.PessoaController;
import br.edu.ladoss.nutrif.controller.Replyable;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.input.ConfirmationKey;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ValidateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmationActivity extends AppCompatActivity implements Replyable<Aluno> {
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.codigo)
    EditText codigo;
    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.getString("matricula") != null) {
            matricula.setText(bundle.getString("matricula"));
            codigo.requestFocus();
        }
    }

    public void confirmar(View v) {
        if (ValidateUtil.validateField(matricula.getText().toString(), ValidateUtil.MATRICULA) != ValidateUtil.OK) {
            matricula.setError(getString(R.string.invalido));
            return;
        }
        if (codigo.getText().toString().length() == 0) {
            codigo.setError(getString(R.string.invalido));
            return;
        }
        change(false);
        PessoaController.validar(new ConfirmationKey(
                matricula.getText().toString(),
                codigo.getText().toString()), this, this);
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

    @Override
    public void onSuccess(Aluno aluno) {
        Intent intent = new Intent(ConfirmationActivity.this, EnterActivity.class);
        Bundle bundle = this.getIntent().getExtras();
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Erro erro) {
        AndroidUtil.showSnackbar(ConfirmationActivity.this,
                erro.getMensagem());
        change(true);
    }

    @Override
    public void failCommunication(Throwable throwable) {
        AndroidUtil.showSnackbar(ConfirmationActivity.this, R.string.erroconexao);
        change(true);
    }
}
