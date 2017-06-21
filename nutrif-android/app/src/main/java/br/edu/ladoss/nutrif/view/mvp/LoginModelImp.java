package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
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
    public Aluno retrieveFromDB(Context context) {
        AlunoDAO alunoDAO = new AlunoDAO(context);

        Log.i(context.getString(R.string.app_name), "tentando recuperar usuário");

        Aluno aluno = alunoDAO.findWithPhoto();
        if (aluno != null) {
            Log.i(context.getString(R.string.app_name), "usuário recuperado");
            aluno.setKeyAuth(PreferencesUtils.getAccessKeyOnSharedPreferences(context));
        } else {
            Log.i(context.getString(R.string.app_name), "usuário não encontrado");
        }

        return aluno;
    }

    @Override
    public Aluno downloadPhoto(Aluno aluno, Context context) {
        Call<ResponseBody> call = ConnectionServer
                .getInstance()
                .getService()
                .download(
                        aluno.getKeyAuth(),
                        aluno.getId().toString()
                );

        try {
            Response<ResponseBody> response = call.execute();

            if (response.isSuccess()) {

                InputStream input = response.body().byteStream();
                AlunoDAO.getInstance(context).updatePhoto(aluno, BitmapFactory.decodeStream(input));

            }
        } catch (IOException e) {
            Log.e(context.getString(R.string.app_name), e.getMessage());
            AndroidUtil.showToast(context, R.string.erroconexao);
            return null;
        }
        return aluno;
    }

    @Override
    public void onDestroy() {
        presenter = null;
    }
}
