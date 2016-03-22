package br.edu.nutrif.controller;

import android.content.Context;

import java.util.List;

import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Dia;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.PretencaoRefeicao;
import br.edu.nutrif.entitys.Refeicao;
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
public class PetensaoRefeicaoController{

    public static void pedirRefeicao(final Context context, int position, final Replyable<PretencaoRefeicao> ui) {
        DiaRefeicao refeicao = DiaRefeicaoController.refeicoes.get(position);
        String matricula = AlunoDAO.getInstance(context).find().getMatricula();

        PretencaoRefeicao pretencao = new PretencaoRefeicao();

        pretencao.getDiaRefeicao().setAluno(new Aluno(matricula, null, null));
        pretencao.getDiaRefeicao().setDia(new Dia(refeicao.getDia().getId(), null));
        pretencao.getDiaRefeicao().setRefeicao(new Refeicao(refeicao.getRefeicao().getId()));

        Call<PretencaoRefeicao> call = ConnectionServer
                .getInstance()
                .getService()
                .pedirRefeicao(PreferencesUtils.getAccessKeyOnSharedPreferences(context), pretencao);
        call.enqueue(new Callback<PretencaoRefeicao>() {
            @Override
            public void onResponse(Response<PretencaoRefeicao> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ui.onSuccess(response.body());
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
