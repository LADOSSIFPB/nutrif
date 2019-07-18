package br.edu.ladoss.nutrif.view.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.exceptions.DadoInvalidoException;
import br.edu.ladoss.nutrif.model.Aluno;
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


public class CadastroActivity extends Activity {
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText senha;
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.carregando)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void registrar(View v) {
        change(false);
        if (isValid()) {
            Aluno aluno = null;
            try {
                aluno = new Aluno(
                                matricula.getText().toString(),
                                email.getText().toString(),
                                senha.getText().toString());
            } catch (DadoInvalidoException e) {
                e.printStackTrace();
            }

            final Aluno finalAluno = aluno;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Call<Aluno> call = ConnectionServer.getInstance()
                            .getService()
                            .inserir(finalAluno);

                    Log.i(this.getClass().getName(),"realizando chamada ao serviço de cadastro");

                    call.enqueue(new Callback<Aluno>() {
                        @Override
                        public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                            if (response.isSuccess()) {

                                Log.i(this.getClass().getName(),"cadastro realizado com sucesso!");

                                Bundle bundle = new Bundle();
                                bundle.putString("validaMatricula", matricula.getText().toString());
                                bundle.putString("email", email.getText().toString());
                                bundle.putString("validaSenha", senha.getText().toString());
                                Intent intent = new Intent(CadastroActivity.this, ConfirmationActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.i(this.getClass().getName(),"servidor retornou um erro: " + response.code());

                                Erro erro = ErrorUtils.parseError(response, CadastroActivity.this);
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
                AndroidUtil.showSnackbar(CadastroActivity.this, string);
            }
        });
    }

    private boolean isValid() {
        Log.i(this.getLocalClassName(),"validando matrícula");
        String mat = matricula.getText().toString();
        boolean validated = Validate.validaMatricula(mat);
        if (!validated) {
            Log.i(this.getLocalClassName(),"matrícula inválida");
            matricula.setError(getString(R.string.invalido));
            matricula.setFocusable(true);
            matricula.requestFocus();
            return false;
        }

        String emailText = email.getText().toString();
        validated = Validate.validaIdentificador(emailText);
        if (!validated) {
            email.setError("E-mail inválido.");
            email.setFocusable(true);
            email.requestFocus();
            return false;
        }

        String senhaText = senha.getText().toString();
        validated = Validate.validaSenha(senhaText);
        if (!validated) {
            senha.setError("Esta validaSenha é inválida");
            senha.setFocusable(true);
            senha.requestFocus();
        }
        return true;
    }

    private void change(final boolean ativo) {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

}

