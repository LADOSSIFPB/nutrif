package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import br.edu.ladoss.nutrif.view.activitys.CadastroActivity;
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
    public void doLogin(Aluno aluno) {

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
    public Aluno downloadPhoto(Integer id) throws Throwable {

        /*Call<ResponseBody> call = ConnectionServer
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
                AlunoDAO.getInstance(presenter.getContext()).updatePhoto(id, BitmapFactory.decodeStream(input));

            }
        } catch (IOException e) {
            Log.e(presenter.getContext().getString(R.string.app_name), e.getMessage());
            AndroidUtil.showToast(presenter.getContext(), R.string.erroconexao);
            return null;
        }*/
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

    }
}
