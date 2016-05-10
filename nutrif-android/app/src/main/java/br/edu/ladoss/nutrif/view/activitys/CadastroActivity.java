package br.edu.ladoss.nutrif.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.controller.PessoaController;
import br.edu.ladoss.nutrif.controller.Replyable;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ValidateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;


public class CadastroActivity extends AppCompatActivity implements Replyable<Aluno> {
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText senha;
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.carregando)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void registrar(View v) {
        change(false);
        if (!validate()) {
            PessoaController.cadastrar(new Aluno(
                            matricula.getText().toString(),
                            email.getText().toString(),
                            senha.getText().toString()),
                    this, this);
        }
    }

    public boolean validate() {
        if (ValidateUtil.validateField(email.getText().toString(), ValidateUtil.EMAIL) != ValidateUtil.OK) {
            email.setError(this.getString(R.string.invalido));
            return true;
        }
        if (ValidateUtil.validateField(senha.getText().toString(), ValidateUtil.SENHA) != ValidateUtil.OK) {
            senha.setError(this.getString(R.string.invalido));
            return true;
        }
        if (ValidateUtil.validateField(matricula.getText().toString(), ValidateUtil.MATRICULA) != ValidateUtil.OK) {
            matricula.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }

    public void change(final boolean ativo) {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void onSuccess(Aluno aluno) {
        Bundle bundle = new Bundle();
        bundle.putString("matricula", matricula.getText().toString());
        bundle.putString("email", email.getText().toString());
        Intent intent = new Intent(CadastroActivity.this, ConfirmationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Erro erro) {
        AndroidUtil.showSnackbar(CadastroActivity.this, erro.getMensagem());
        change(true);
    }

    @Override
    public void failCommunication(Throwable throwable) {
        AndroidUtil.showSnackbar(CadastroActivity.this, R.string.erroconexao);
        change(true);
    }
}

