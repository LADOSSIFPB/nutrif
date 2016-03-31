package br.edu.nutrif.controller;

import android.content.Context;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.output.Erro;
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
public class AlunoController {
    public static void login(final Aluno aluno, final Context context, final Replyable<Aluno> ui) {
        Call<Aluno> call = ConnectionServer
                .getInstance()
                .getService()
                .login(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    aluno.setNome(response.body().getNome());
                    aluno.setMatricula(response.body().getMatricula());
                    aluno.setEmail(response.body().getEmail());
                    AlunoDAO.getInstance(context).insertAluno(aluno);
                    PreferencesUtils.setAccessKeyOnSharedPreferences(context, response.body().getKeyAuth());
                    ui.onSuccess(aluno);
                } else {
                    if (response.code() == 401) {
                        Erro erro = new Erro();
                        erro.setCodigo(0);
                        erro.setMensagem(context.getString(R.string.campoerrado));
                        ui.onFailure(erro);
                    } else {
                        ui.onFailure(ErrorUtils.parseError(response, retrofit, context));
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                ui.failCommunication(t);
            }
        });
    }

    public static void login(final Context context, final Replyable<Aluno> ui) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlunoDAO pessoaDAO = new AlunoDAO(context);

                final Aluno aluno = pessoaDAO.find();
                if (aluno == null) {
                    ui.onFailure(null);
                    return;
                }

                Call<Aluno> call = ConnectionServer
                        .getInstance()
                        .getService()
                        .login(aluno);

                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Response<Aluno> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            aluno.setNome(response.body().getNome());
                            aluno.setMatricula(response.body().getMatricula());
                            aluno.setEmail(response.body().getEmail());
                            PreferencesUtils.setAccessKeyOnSharedPreferences(context, response.body().getKeyAuth());
                            ui.onSuccess(aluno);
                        } else {
                            if (response.code() == 401) {
                                AlunoDAO.getInstance(context).delete();
                                Erro erro = new Erro();
                                erro.setCodigo(0);
                                erro.setMensagem(context.getString(R.string.campoerrado));
                                ui.onFailure(erro);

                            } else {
                                ui.onFailure(ErrorUtils.parseError(response, retrofit, context));
                            }

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        ui.failCommunication(t);
                    }
                });
            }
        }).start();
    }
}
