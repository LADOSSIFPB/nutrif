package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.model.Pessoa;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import br.edu.ladoss.nutrif.view.activitys.CadastroActivity;
import br.edu.ladoss.nutrif.view.activitys.EnterActivity;
import br.edu.ladoss.nutrif.view.activitys.HomeActivity;
import br.edu.ladoss.nutrif.view.activitys.LoginActivity;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by LADOSS on 21/06/2017.
 */

public class LoginModelImp implements LoginMVP.Model{

    private LoginMVP.Presenter presenter;

    public LoginModelImp(LoginMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        presenter = null;
    }

    @Override
    public void doLogin(final Aluno alunoReferencial, final Bundle extra) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionServer.getInstance().updateServiceAdress();
                Aluno aluno = alunoReferencial;

                if (aluno.getKeyAuth() == null || aluno.getKeyAuth().isEmpty()) {

                    //Se usuário não tiver chave
                    aluno = receiveUser(aluno);
                    //Cria e testa se a chave é valida
                    if ((aluno.getKeyAuth() == null) || aluno.getKeyAuth().isEmpty()) {
                        Intent intent = new Intent(presenter.getContext(), EnterActivity.class);
                        intent.putExtras(extra);
                        presenter.getContext().startActivity(intent);
                        presenter.finishActivity();
                        return;
                    }
                    PreferencesUtils.setAccessKeyOnSharedPreferences(presenter.getContext(), aluno.getKeyAuth());
                }

                //Tenta recuperar a matrícula
                if (aluno.getMatricula() == null)
                    //Recupera do serviço e testa se é valida
                    if (!tryAuthorize(aluno)) {
                        presenter.finishActivity();
                        return;
                    }

                //Recupera foto
                if (aluno.getPhoto() == null)
                    try {
                        downloadPhoto(aluno);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                presenter.getContext().startActivity(new Intent(presenter.getContext(), HomeActivity.class));
                presenter.finishActivity();
            }
        }).start();
    }

    @Override
    public boolean tryAuthorize(Aluno aluno) {
        Context context = presenter.getContext();

        Log.i(context.getString(R.string.app_name), " solicitando matrícula do aluno");

        Call<Aluno> call = ConnectionServer.getInstance().getService().getMatricula(
                aluno.getKeyAuth(),
                aluno.getId().toString());
        try {
            Response<Aluno> response = call.execute();

            if (response.isSuccess()) {
                Log.i(context.getString(R.string.app_name), " matrícula recuperada com sucesso");
                aluno.setMatricula(response.body().getMatricula());
                AlunoDAO.getInstance(context).insertAluno(aluno);
            }

        } catch (IOException e) {
            Log.e(context.getString(R.string.app_name), e.getMessage());
            presenter.showMessage(context.getString(R.string.erroconexao));
            return false;
        }
        return true;
    }

    @Override
    public Aluno receiveUser(Aluno aluno) {
        Log.i(presenter.getContext().getString(R.string.app_name), " solicitando uma chave ao servidor");

        Call<Pessoa> call = ConnectionServer.getInstance().getService().login(aluno);

        try {
            Response<Pessoa> response = call.execute();

            if (response.isSuccess()) {
                Log.i(presenter.getContext().getString(R.string.app_name), " chave recuperada com sucesso");
                aluno.setTipo(response.body().getTipo());
                aluno.setId(response.body().getId());
                aluno.setNome(response.body().getNome());
                aluno.setKeyAuth(response.body().getKeyAuth());

            } else {
                Log.i(presenter.getContext().getString(R.string.app_name), " a chave não foi recuperada com êxito");
                Erro erro = ErrorUtils.parseError(response, presenter.getContext());
                //Gambiarra pois o servidor não retorna erro com mensagem
                if (response.code() == 401) {
                    presenter.showMessage(presenter.getContext().getString(R.string.campoerrado));
                } else {
                    presenter.showMessage(erro.getMensagem());
                }
            }
        } catch (IOException e) {
            Log.e(presenter.getContext().getString(R.string.app_name), e.getMessage());
            presenter.showMessage(presenter.getContext().getString(R.string.erroconexao));
        }

        return aluno;
    }

    @Override
    public Aluno retrieveFromDB() throws Resources.NotFoundException {
        AlunoDAO alunoDAO = new AlunoDAO(presenter.getContext());

        Log.i(presenter.getContext().getString(R.string.app_name), "tentando recuperar usuário");

        Aluno aluno = alunoDAO.findWithPhoto();
        if (aluno != null) {
            Log.i(presenter.getContext().getString(R.string.app_name), "usuário recuperado");
            aluno.setKeyAuth(PreferencesUtils.getAccessKeyOnSharedPreferences(presenter.getContext()));
        } else {
            Log.i(presenter.getContext().getString(R.string.app_name), "usuário não encontrado");
        }

        return aluno;
    }

    @Override
    public Aluno downloadPhoto(Aluno aluno) throws Throwable {

        Integer id = aluno.getId();

        Call<ResponseBody> call = ConnectionServer
                .getInstance()
                .getService()
                .download(
                        PreferencesUtils.getAccessKeyOnSharedPreferences(presenter.getContext()),
                        id.toString()
                );

        try {
            Response<ResponseBody> response = call.execute();

            if (response.isSuccess()) {

                InputStream input = response.body().byteStream();
                AlunoDAO.getInstance(presenter.getContext()).updatePhoto(aluno, BitmapFactory.decodeStream(input));

            }
        } catch (IOException e) {
            Log.e(presenter.getContext().getString(R.string.app_name), e.getMessage());
            AndroidUtil.showToast(presenter.getContext(), R.string.erroconexao);
            return null;
        }
        return null;
    }

    @Override
    public void doRegister() {
        Intent intent = new Intent(presenter.getContext(), CadastroActivity.class);
        presenter.getContext().startActivity(intent);
    }

    @Override
    public String getAlunoWithMatricula(Integer id) throws Throwable {
        return null;
    }

    @Override
    public void redirectHomeActivity() {
        presenter.getContext().startActivity(new Intent(presenter.getContext(), HomeActivity.class));
    }
}
