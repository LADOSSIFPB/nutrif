package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.AlunoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.input.FormularioLogin;
import br.edu.nutrif.entitys.output.Erro;
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
        AlunoController.login(this, new Replyable<Aluno>() {
            @Override
            public void onSuccess(Aluno aluno) {
                startActivity(new Intent(StartActivity.this, RefeitorioActivity.class));
                finish();
            }

            @Override
            public void onFailure(Erro erro) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void failCommunication(Throwable throwable) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
