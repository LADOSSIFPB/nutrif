package br.edu.ladoss.nutrif.view.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.model.input.ConfirmationKey;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.validation.Validate;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ConfirmationActivity extends AppCompatActivity {
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.codigo)
    EditText codigo;
    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null && bundle.getString("validaMatricula") != null) {
            matricula.setText(bundle.getString("validaMatricula"));
            codigo.requestFocus();
        }
    }

    public void confirmar(View v) {
        boolean isValidated = isValidated();

        if (isValidated) {
            change(false);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    ConfirmationKey key = new ConfirmationKey(
                            matricula.getText().toString(),
                            codigo.getText().toString());

                    Call<Void> call = ConnectionServer.getInstance()
                            .getService()
                            .confirmar(key);

                    Log.i(this.getClass().getName(),"inicializando chamada de confirmação.");

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Response<Void> response, Retrofit retrofit) {

                            if (response.isSuccess()) {

                                Log.i(this.getClass().getName(),"confirmado com sucesso!");

                                Intent intent = new Intent(ConfirmationActivity.this, EnterActivity.class);
                                Bundle bundle = ConfirmationActivity.this.getIntent().getExtras();
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();

                            } else {

                                Log.i(this.getClass().getName(),"servidor retornou um erro");

                                Erro erro = ErrorUtils.parseError(response,ConfirmationActivity.this);
                                showMessage(erro.getMensagem());

                                change(true);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t){
                            Log.i(this.getClass().getName(),"foi lançado um throwable.");
                            showMessage(getString(R.string.erroconexao));
                            change(true);
                        }
                    });
                }
            }).start();
        }
    }

    private void showMessage(final String string){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showSnackbar(ConfirmationActivity.this, string);
            }
        });
    }

    private boolean isValidated() {

        boolean validated = true;

        Log.i(this.getLocalClassName(),"validando matrícula");
        String mat = matricula.getText().toString();
        validated = Validate.validaMatricula(mat);
        if (!validated) {
            Log.i(this.getLocalClassName(),"matrícula inválida");
            matricula.setError(getString(R.string.invalido));
            matricula.setFocusable(true);
            matricula.requestFocus();
            return false;
        }

        Log.i(this.getLocalClassName(),"validando código");
        String cod = codigo.getText().toString();
        validated = Validate.validaCodigoAtivacao(cod);
        if (!validated) {
            Log.i(this.getLocalClassName(),"código inválido");
            codigo.setError(getString(R.string.invalido));
            codigo.setFocusable(true);
            codigo.requestFocus();
        }

        return validated;
    }

    private void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

}
