package br.edu.nutrif.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.nutrif.database.DatabaseCore;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.network.ConnectionServer;
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
                    ui.onSuccess(DiaRefeicaoController.organizaDiaRefeicao(context, response.body()));
                } else {
                    ui.onFailure(ErrorUtils.parseError(response, retrofit, context));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                refeicoes = DiaRefeicaoDAO.getInstance(context).findAll();
                ui.failCommunication(t);
            }
        });

    }

    public static List<DiaRefeicao> organizaDiaRefeicao(Context context, List<DiaRefeicao> server) {
        DiaRefeicaoDAO bd = new DiaRefeicaoDAO(context);
        List<DiaRefeicao> diarefeicaobd = bd.findAll();

        for (DiaRefeicao diaRefeicao : server) {
            if (diarefeicaobd == null) {
                bd.insert(diaRefeicao);
            } else {
                for (DiaRefeicao bdRefeicao : diarefeicaobd)
                    if (bdRefeicao.getId().equals(diaRefeicao.getId())) {
                        diaRefeicao.setCodigo(bdRefeicao.getCodigo());
                        break;
                    }
            }
        }
        refeicoes = server;
        return refeicoes;
    }

}
