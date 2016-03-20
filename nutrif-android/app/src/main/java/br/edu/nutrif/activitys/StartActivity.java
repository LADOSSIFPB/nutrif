package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.input.FormularioLogin;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import br.edu.nutrif.util.PreferencesUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }

    public void login() {
        AlunoDAO pessoaDAO = new AlunoDAO(this);
        Aluno user = pessoaDAO.find();
        if (user == null) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        } else{
                Call<Aluno> call = ConnectionServer.getInstance()
                        .getService()
                        .login(new FormularioLogin(user));
                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                        if(response.isSuccess()){
                            PreferencesUtils.setAccessKeyOnSharedPreferences(StartActivity.this,response.body().getKeyauth());
                            startActivity(new Intent(StartActivity.this, RefeitorioActivity.class));
                            finish();
                        }else {
                            AlunoDAO.getInstance(StartActivity.this).delete();
                            AndroidUtil.showSnackbar(StartActivity.this, ErrorUtils.parseError(response,retrofit,StartActivity.this).getMensagem());
                            startActivity(new Intent(StartActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        AlunoDAO.getInstance(StartActivity.this).delete();
                        AndroidUtil.showSnackbar(StartActivity.this, R.string.erroconexao);
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        finish();
                    }
                });
        }
    }
}
