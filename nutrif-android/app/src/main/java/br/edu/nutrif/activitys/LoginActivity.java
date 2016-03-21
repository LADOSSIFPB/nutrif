package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.entitys.input.FormularioLogin;
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
    public void login(View v){
        if(!validate()){
            final Aluno aluno = new Aluno(null,email.getText().toString(),senha.getText().toString());
            Call<Aluno> call = ConnectionServer
                    .getInstance()
                    .getService()
                    .login(new FormularioLogin(aluno));
            call.enqueue(new Callback<Aluno>() {
                @Override
                public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        aluno.setNome(response.body().getNome());
                        aluno.setMatricula(response.body().getMatricula());
                        AlunoDAO.getInstance(LoginActivity.this).insertAluno(aluno);
                        PreferencesUtils.setAccessKeyOnSharedPreferences(LoginActivity.this, response.body().getKeyauth());
                        startActivity(new Intent(LoginActivity.this, RefeitorioActivity.class));
                        finish();
                    }else {
                        if(response.code() == 401){
                            AndroidUtil.showSnackbar(LoginActivity.this,R.string.campoerrado);
                        }else{
                            AndroidUtil.showSnackbar(LoginActivity.this, ErrorUtils.parseError(response,retrofit,LoginActivity.this).getMensagem());
                        }

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    AndroidUtil.showSnackbar(LoginActivity.this,R.string.erroconexao);
                }
            });
        }
    }

    public void redirecionar(View v){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public boolean validate(){
        if(email.getText().length() > Pessoa.LENGHT_MAX_EMAIL ||
                email.getText().length() < Pessoa.LENGHT_MIN_EMAIL ||
                email.getText().toString().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")){
            email.setError(this.getString(R.string.invalido));
            return true;
        }
        if(senha.getText().length() > Pessoa.LENGHT_MAX_SENHA ||
                senha.getText().length() < Pessoa.LENGHT_MIN_SENHA) {
            senha.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }

    public void autenticar(View view){
        startActivity(new Intent(this, ConfirmationActivity.class));
    }
}
