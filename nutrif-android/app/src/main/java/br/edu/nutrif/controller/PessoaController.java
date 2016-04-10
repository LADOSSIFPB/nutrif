package br.edu.nutrif.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.entitys.input.ConfirmationKey;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import br.edu.nutrif.util.PreferencesUtils;
import br.edu.nutrif.view.activitys.ConfirmationActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by juan on 21/03/16.
 */
public class PessoaController {

    public static void login(final Pessoa pessoa, final Context context, final Replyable<Pessoa> ui) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Pessoa> call = ConnectionServer
                        .getInstance()
                        .getService()
                        .login(pessoa);
                try {
                    Response<Pessoa> response = call.execute();
                    if (response.isSuccess()) {
                        pessoa.setTipo(response.body().getTipo());
                        pessoa.setId(response.body().getId());
                        pessoa.setKeyAuth(response.body().getKeyAuth());
                        if (PessoaController.salvaPessoa(context, pessoa))
                            ui.onSuccess(response.body());
                        else
                            ui.failCommunication(new Throwable());
                    } else {
                        if (response.code() == 401) {
                            Erro erro = new Erro();
                            erro.setCodigo(0);
                            erro.setMensagem(context.getString(R.string.campoerrado));
                            ui.onFailure(erro);
                        } else {
                            ui.failCommunication(new Throwable());
                        }

                    }
                } catch (IOException e) {
                    ui.failCommunication(e);
                }
            }
        }

        ).start();
    }

    public static void login(final Context context, final Replyable<Pessoa> ui) {

        AlunoDAO alunoDAO = new AlunoDAO(context);

        final Pessoa pessoa = alunoDAO.find();
        if (pessoa == null) {
            ui.onFailure(null);
            return;
        }
        login(pessoa, context, ui);
    }

    private static boolean salvaPessoa(Context context, Pessoa pessoa) {

        if (!pessoa.getTipo().equals("2"))
            return false;

        Call<Aluno> call = ConnectionServer.getInstance().getService().getMatricula(
                pessoa.getKeyAuth(),
                pessoa.getId().toString());
        try {
            Response<Aluno> response = call.execute();

            if (response.isSuccess()) {
                Aluno aluno = (Aluno) pessoa;
                aluno.setMatricula(response.body().getMatricula());
                AlunoDAO.getInstance(context).insertAluno(aluno);
                PreferencesUtils.setAccessKeyOnSharedPreferences(context, response.body().getKeyAuth());
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void cadastrar(final Aluno aluno, final Context context, final Replyable<Aluno> ui) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Call<Aluno> call = ConnectionServer.getInstance()
                        .getService()
                        .inserir(aluno);
                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Response<Aluno> response, Retrofit retrofit) {
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
        }).start();
    }

    public static void validar(final ConfirmationKey key, final Context context, final Replyable<Aluno> ui) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Void> call = ConnectionServer.getInstance()
                        .getService()
                        .confirmar(key);
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
        }).start();
    }

    public static void logoff(final Context context) {
        AlunoDAO.getInstance(context).delete();
    }
}
