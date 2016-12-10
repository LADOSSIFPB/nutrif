package br.edu.ladoss.nutrif.controller;

import android.content.Context;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by juan on 21/03/16.
 */
public class PessoaController {

    public static void uploadPhoto(final Context context, final Replyable<Aluno> ui, final File file) {

        String auth = PreferencesUtils.getAccessKeyOnSharedPreferences(context);

        RequestBody uploadedFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        int idPessoa = AlunoDAO.getInstance(context).find().getId();

        Call<Void> call = ConnectionServer.getInstance().getService().upload(auth, fileName, uploadedFile, idPessoa);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ui.onSuccess(null);
                } else {
                    ui.onFailure(ErrorUtils.parseError(response, retrofit, context));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                ui.failCommunication(t);
            }
        });
    }


    public static void logoff(final Context context) {
        AlunoDAO.getInstance(context).deleteAll();
        PreferencesUtils.setAccessKeyOnSharedPreferences(context,"");
        DiaRefeicaoDAO.getInstance(context).deleteAll();
    }
}
