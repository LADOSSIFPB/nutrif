package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.AlunoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.output.Erro;

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
                startActivity(new Intent(StartActivity.this, RefeitorioActivity.class));
                finish();
            }
        });
    }
}
