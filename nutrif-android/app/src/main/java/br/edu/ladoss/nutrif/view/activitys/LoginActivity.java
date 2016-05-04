package br.edu.ladoss.nutrif.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.controller.PessoaController;
import br.edu.ladoss.nutrif.controller.Replyable;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.Pessoa;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ValidateUtil;
import butterknife.*;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.identificadorEdit)
    EditText identificador;
    @Bind(R.id.passwordEdit)
    EditText senha;
    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.ic_action_name);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.getString("email") != null) {
            identificador.setText(bundle.getString("email"));
            senha.requestFocus();
        }
    }

    public void login(View v) {
        change(false);
        Pessoa aluno = validate();
        if (aluno != null) {
            PessoaController.login(aluno, this,
                    new Replyable<Pessoa>() {
                        @Override
                        public void onSuccess(Pessoa aluno) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AndroidUtil.showToast(LoginActivity.this, R.string.logindone);
                                    startActivity(new Intent(LoginActivity.this, RefeitorioActivity.class));
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Erro erro) {
                            AndroidUtil.showSnackbar(LoginActivity.this, erro.getMensagem());
                            LoginActivity.this.change(true);
                        }

                        @Override
                        public void failCommunication(Throwable throwable) {
                            AndroidUtil.showSnackbar(LoginActivity.this, getString(R.string.erroconexao));
                            LoginActivity.this.change(true);
                        }
                    });
        }
    }

    public void redirecionar(View v) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public Aluno validate() {
        Aluno aluno = new Aluno();
        if (ValidateUtil.validateField(identificador.getText().toString(), ValidateUtil.EMAIL) == ValidateUtil.OK) {
            aluno.setEmail(identificador.getText().toString());
        } else if (ValidateUtil.validateField(identificador.getText().toString(), ValidateUtil.MATRICULA) == ValidateUtil.OK)
            aluno.setMatricula(identificador.getText().toString());
        else {
            identificador.setError(this.getString(R.string.invalido));
            return null;
        }

        if (ValidateUtil.validateField(senha.getText().toString(), ValidateUtil.SENHA) == ValidateUtil.OK) {
            aluno.setSenha(senha.getText().toString());
        } else {
            senha.setError(this.getString(R.string.invalido));
            return null;
        }

        return aluno;
    }

    public void autenticar(View view) {
        startActivity(new Intent(this, ConfirmationActivity.class));
    }


    public void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (LoginActivity.this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }
}
