package br.edu.ladoss.nutrif.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.model.Matricula;
import br.edu.ladoss.nutrif.model.Pessoa;
import br.edu.ladoss.nutrif.model.PessoaAcesso;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import br.edu.ladoss.nutrif.view.mvp.LoginMVP;
import br.edu.ladoss.nutrif.view.mvp.LoginPresenterImp;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View{

    @Bind(R.id.informativo)
    TextView messages;

    private LoginMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        presenter = new LoginPresenterImp(this);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            Aluno aluno = new Aluno();
            aluno.setEmail(bundle.getString("email"));
            aluno.setSenha(bundle.getString("validaSenha"));

            if (!(aluno.getEmail() == null)){
                doLogin(aluno);
                return;
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Aluno aluno = retrieveFromDB();
                if (aluno == null) {
                    startActivity(new Intent(LoginActivity.this, EnterActivity.class));
                    finish();
                } else {
                    doLogin(aluno);
                }
            }
        }).start();

    }

    private Aluno retrieveFromDB() {
        AlunoDAO alunoDAO = new AlunoDAO(this);

        Log.i(getString(R.string.app_name), "tentando recuperar usuário");

        Aluno aluno = alunoDAO.findWithPhoto();
        if (aluno != null) {
            Log.i(getString(R.string.app_name), "usuário recuperado");
            aluno.setKeyAuth(PreferencesUtils.getAccessKeyOnSharedPreferences(LoginActivity.this));
        } else {
            Log.i(getString(R.string.app_name), "usuário não encontrado");
        }

        return aluno;
    }

    private void changeMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                String message = "Inicializando Aplicação...";
                switch (random.nextInt(5)) {
                    case 0:
                        message = getString(R.string.funnylogin1);
                        break;
                    case 1:
                        message = getString(R.string.funnylogin2);
                        break;
                    case 2:
                        message = getString(R.string.funnylogin3);
                        break;
                    case 3:
                        message = getString(R.string.funnylogin4);
                        break;
                    case 4:
                        message = getString(R.string.funnylogin5);
                        break;
                }
                messages.setText(message);
            }
        });
    }


    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showToast(LoginActivity.this, message);
            }
        });
    }

    private void doLogin(final Aluno alunoReferencial) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionServer.getInstance().updateServiceAdress();
                Aluno aluno = alunoReferencial;

                LoginActivity.this.changeMessage();
                if (aluno.getKeyAuth() == null || aluno.getKeyAuth().isEmpty()) {

                    changeMessage();
                    //Se usuário não tiver chave
                    aluno = receiveUser(aluno);
                    //Cria e testa se a chave é valida
                    if ((aluno.getKeyAuth() == null) || aluno.getKeyAuth().isEmpty()) {
                        Intent intent = new Intent(LoginActivity.this, EnterActivity.class);
                        intent.putExtras(getIntent().getExtras());
                        startActivity(intent);
                        finish();
                        return;
                    }
                    PreferencesUtils.setAccessKeyOnSharedPreferences(LoginActivity.this, aluno.getKeyAuth());
                }
                LoginActivity.this.changeMessage();
                //Tenta recuperar a matrícula
                if (aluno.getMatricula() == null)
                    //Recupera do serviço e testa se é valida
                    if (!tryAuthorize(aluno)) {
                        finish();
                        return;
                    }
                LoginActivity.this.changeMessage();
                //Recupera foto
                if (aluno.getPhoto() == null)
                    downloadPhoto(aluno);
                showMessage(getString(R.string.logindone));
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }).start();

    }


    private Aluno receiveUser(Aluno aluno) {
        Log.i(getString(R.string.app_name), " solicitando uma chave ao servidor");

        PessoaAcesso alunoAcesso = new PessoaAcesso();

        alunoAcesso.setEmail(aluno.getEmail());
        alunoAcesso.setSenha(aluno.getSenha());

        Call<Pessoa> call = ConnectionServer.getInstance().getService().login(alunoAcesso);

        try {
            Response<Pessoa> response = call.execute();

            if (response.isSuccess()) {
                Log.i(getString(R.string.app_name), " chave recuperada com sucesso");
                aluno.setTipo(response.body().getTipo());
                aluno.setId(response.body().getId());
                aluno.setNome(response.body().getNome());
                aluno.setKeyAuth(response.body().getKeyAuth());

            } else {
                Log.i(getString(R.string.app_name), " a chave não foi recuperada com êxito");
                Erro erro = ErrorUtils.parseError(response, this);
                //Gambiarra pois o servidor não retorna erro com mensagem
                if (response.code() == 401) {
                    showMessage(getString(R.string.campoerrado));
                } else {
                    showMessage(erro.getMensagem());
                }
            }
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), e.getMessage());
            showMessage(getString(R.string.erroconexao));
        }

        return aluno;
    }

    private boolean tryAuthorize(Aluno aluno) {
        Log.i(getString(R.string.app_name), " solicitando matrícula do aluno");

        Call<List<Matricula>> call = ConnectionServer.getInstance().getService().getMatriculaByAlunoId(
                aluno.getKeyAuth(),
                aluno.getId().toString());
        try {
            Response<List<Matricula>> response = call.execute();

            if (response.isSuccess()) {
                Log.i(getString(R.string.app_name), " matrícula recuperada com sucesso");
                List<Matricula> matriculas = response.body();
                aluno.setMatricula(matriculas.get(0).getNumero());
                AlunoDAO.getInstance(this).insertAluno(aluno);
            }

        } catch (IOException e) {
            Log.e(getString(R.string.app_name), e.getMessage());
            showMessage(getString(R.string.erroconexao));
            return false;
        }
        return true;
    }

    @Nullable
    private Aluno downloadPhoto(Aluno aluno) {

        Call<com.squareup.okhttp.ResponseBody> call = ConnectionServer
                .getInstance()
                .getService()
                .download(
                        aluno.getKeyAuth(),
                        aluno.getId().toString()
                );

        try {
            Response<com.squareup.okhttp.ResponseBody> response = call.execute();

            if (response.isSuccess()) {

                InputStream input = response.body().byteStream();
                AlunoDAO.getInstance(this).updatePhoto(aluno, BitmapFactory.decodeStream(input));

            }
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), e.getMessage());
            AndroidUtil.showToast(this, R.string.erroconexao);
            return null;
        }
        return aluno;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public AppCompatActivity get() {
        return this;
    }
}
