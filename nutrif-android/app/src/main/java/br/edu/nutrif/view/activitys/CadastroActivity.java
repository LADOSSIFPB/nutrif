package br.edu.nutrif.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import butterknife.ButterKnife;
import butterknife.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class CadastroActivity extends AppCompatActivity {
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText senha;
    @Bind(R.id.matricula)
    EditText matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void registrar(View v) {
        if (!validate()) {
            Call<Aluno> call = ConnectionServer.getInstance()
                    .getService()
                    .inserir(
                            new Aluno(
                                    matricula.getText().toString(),
                                    email.getText().toString(),
                                    senha.getText().toString()
                            ));
            call.enqueue(new Callback<Aluno>() {
                @Override
                public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("matricula", matricula.getText().toString());
                        Intent intent = new Intent(CadastroActivity.this, ConfirmationActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        AndroidUtil.showSnackbar(CadastroActivity.this, ErrorUtils.parseError(response, retrofit, CadastroActivity.this).getMensagem());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    AndroidUtil.showSnackbar(CadastroActivity.this, R.string.erroconexao);
                }
            });
        }
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
        int tam = matricula.getText().length();
        if (tam != Pessoa.LENGHT_MATRICULA) {
            matricula.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }
}

