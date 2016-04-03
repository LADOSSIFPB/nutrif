package br.edu.nutrif.controller;

import android.content.Context;

import br.edu.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.PretensaoRefeicao;
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
public class PetensaoRefeicaoController {

    public static void pedirRefeicao(final Context context, int position, final Replyable<PretensaoRefeicao> ui) {

        final DiaRefeicao refeicao = DiaRefeicaoController.refeicoes.get(position);

        PretensaoRefeicao pretencao = new PretensaoRefeicao();

        pretencao.getConfirmaPretensaoDia().getDiaRefeicao().setId(refeicao.getDia().getId());

        Call<PretensaoRefeicao> call = ConnectionServer
                .getInstance()
                .getService()
                .pedirRefeicao(PreferencesUtils.getAccessKeyOnSharedPreferences(context), pretencao);
        call.enqueue(new Callback<PretensaoRefeicao>() {
            @Override
            public void onResponse(Response<PretensaoRefeicao> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    refeicao.setCodigo(response.body().getKeyAccess());
                    DiaRefeicaoDAO.getInstance(context).update(refeicao);
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

    public static void retornarRefeicao(final Context context, int position, final Replyable<PretensaoRefeicao> ui){
        PretensaoRefeicao pretensaoRefeicao = new PretensaoRefeicao();
        pretensaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao().setId(
                DiaRefeicaoController.refeicoes.get(position).getId()
        );

        Call<PretensaoRefeicao> call = ConnectionServer.getInstance().getService().infoRefeicao(
                PreferencesUtils.getAccessKeyOnSharedPreferences(context),
                pretensaoRefeicao
        );
        call.enqueue(new Callback<PretensaoRefeicao>() {
            @Override
            public void onResponse(Response<PretensaoRefeicao> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    ui.onSuccess(response.body());
                }else {
                    ui.onFailure(ErrorUtils.parseError(response,retrofit,context));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                ui.failCommunication(t);
            }
        });
    }

}
