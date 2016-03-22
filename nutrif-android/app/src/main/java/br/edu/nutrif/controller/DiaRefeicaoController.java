package br.edu.nutrif.controller;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import br.edu.nutrif.util.PreferencesUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by juan on 21/03/16.
 */
public class DiaRefeicaoController {

    public static List<DiaRefeicao> refeicoes = new ArrayList<>();

    public static void gerarHorario(final Context context, final Replyable<List<DiaRefeicao>> ui) {

        String matricula = AlunoDAO.getInstance(context).find().getMatricula();
        String auth = PreferencesUtils.getAccessKeyOnSharedPreferences(context);

        Call<List<DiaRefeicao>> call = ConnectionServer.getInstance().getService().listaRefeicoes(auth,
                matricula);
        call.enqueue(new Callback<List<DiaRefeicao>>() {
            @Override
            public void onResponse(Response<List<DiaRefeicao>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    refeicoes = response.body();
                    ui.onSuccess(refeicoes);
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

}
