package br.edu.nutrif.controller;

import android.content.Context;

import java.io.IOException;

import br.edu.nutrif.R;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
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

        if (pessoa.getTipo() == null || Integer.parseInt(pessoa.getTipo()) != 2)
            return false;

        Call<Aluno> call = ConnectionServer.getInstance().getService().getMatricula(
                PreferencesUtils.getAccessKeyOnSharedPreferences(context),
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
}
