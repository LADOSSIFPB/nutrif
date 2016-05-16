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
import br.edu.ladoss.nutrif.validation.StringValidator;
import br.edu.ladoss.nutrif.validation.Validate;
import butterknife.*;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.identificadorEdit)
    EditText identificadorEditText;

    @Bind(R.id.passwordEdit)
    EditText senhaEditText;

    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;

    @Bind(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null
                && bundle.getString("email") != null) {

            identificadorEditText.setText(bundle.getString("email"));
            senhaEditText.requestFocus();
        }
    }

    public void login(View v) {

        boolean isValidated = isValidated();

        if (isValidated) {

            change(false);

            // Preparar aluno para envio da requisição.
            Aluno aluno = new Aluno();
            aluno.setEmail(identificadorEditText.getText().toString().trim());
            aluno.setMatricula(identificadorEditText.getText().toString().trim());
            aluno.setSenha(senhaEditText.getText().toString());

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

    public boolean isValidated() {

        boolean validated = true;

        String identificador = identificadorEditText.getText().toString();
        validated = Validate.identificador(identificador);
        if (!validated) {
            identificadorEditText.setError("E-mail ou matrícula inválidos.");
            identificadorEditText.setFocusable(true);
            identificadorEditText.requestFocus();
        }

        String senha = senhaEditText.getText().toString();
        validated = Validate.senha(senha);
        if (!validated) {
            senhaEditText.setError("Senha inválida.");
            senhaEditText.setFocusable(true);
            senhaEditText.requestFocus();
        }

        return validated;
    }

    public void autenticar(View view) {
        startActivity(new Intent(this, ConfirmationActivity.class));
    }

    public void change(final boolean ativo) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (LoginActivity.this.getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }
}