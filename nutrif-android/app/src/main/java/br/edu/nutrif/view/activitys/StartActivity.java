package br.edu.nutrif.view.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.nutrif.R;
import br.edu.nutrif.controller.PessoaController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.util.AndroidUtil;

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
        PessoaController.login(this, new Replyable<Pessoa>() {
            @Override
            public void onSuccess(Pessoa pessoa) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AndroidUtil.showToast(StartActivity.this, R.string.logindone);
                        startActivity(new Intent(StartActivity.this, RefeitorioActivity.class));
                        finish();
                    }
                });
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
