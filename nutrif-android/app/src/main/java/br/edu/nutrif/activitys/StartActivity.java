package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Pessoa;

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

    public void login(){
        AlunoDAO pessoaDAO = new AlunoDAO(this);
        Pessoa user = pessoaDAO.find();
        if(user == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }
            }).start();

        }else {
        //    Call<> call = ConnectionServer.getInstance().getService().login();
      //      call.enqueue();
        }
    }
}
