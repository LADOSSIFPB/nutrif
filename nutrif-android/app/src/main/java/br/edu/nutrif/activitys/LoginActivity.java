package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.AlunoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.entitys.input.FormularioLogin;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import br.edu.nutrif.util.PreferencesUtils;
import butterknife.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.emailEdit)
    EditText email;
    @Bind(R.id.passwordEdit)
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void login(View v) {
        if (!validate()) {
            AlunoController.login(new Aluno(null, email.getText().toString(), senha.getText().toString()), this,
                    new Replyable<Aluno>() {
                        @Override
                        public void onSuccess(Aluno aluno) {
                            startActivity(new Intent(LoginActivity.this, RefeitorioActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Erro erro) {
                            AndroidUtil.showSnackbar(LoginActivity.this, erro.getMensagem());
                        }

                        @Override
                        public void failCommunication(Throwable throwable) {
                            AndroidUtil.showSnackbar(LoginActivity.this, getString(R.string.erroconexao));
                        }
                    });
        }
    }

    public void redirecionar(View v) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public boolean validate() {
        if (email.getText().length() > Pessoa.LENGHT_MAX_EMAIL ||
                email.getText().length() < Pessoa.LENGHT_MIN_EMAIL ||
                email.getText().toString().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")) {
            email.setError(this.getString(R.string.invalido));
            return true;
        }
        if (senha.getText().length() > Pessoa.LENGHT_MAX_SENHA ||
                senha.getText().length() < Pessoa.LENGHT_MIN_SENHA) {
            senha.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }

    public void autenticar(View view) {
        startActivity(new Intent(this, ConfirmationActivity.class));
    }
}
