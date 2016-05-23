package br.edu.ladoss.nutrif.controller;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.Pessoa;
import br.edu.ladoss.nutrif.entitys.input.ConfirmationKey;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
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
                        pessoa.setNome(response.body().getNome());
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
                            ui.onFailure(ErrorUtils.parseError(response,
                                    new Retrofit.Builder().baseUrl("http://ladoss:8080").addConverterFactory(GsonConverterFactory.create()).build()
                                    , context));
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

    public static void uploadPhoto(final Context context, final Replyable<Aluno> ui, final File file){

        //Convertendo imagem
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);

        // create RequestBody instance from file
        okhttp3.RequestBody requestFile =
                okhttp3.RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part uploadedFile =
                MultipartBody.Part.createFormData("uploadedFile", file.getName(), requestFile);

        RequestBody fileName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), file.getName());

        RequestBody idPessoa =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), AlunoDAO.getInstance(context).find().getId().toString());

        Call<Void> call = ConnectionServer.getInstance().getService().upload(fileName,uploadedFile,idPessoa);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    ui.onSuccess(null);
                }
                else {
                    ui.onFailure(ErrorUtils.parseError(response,retrofit,context));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                ui.failCommunication(t);
            }
        });
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
        AlunoDAO.getInstance(context).deleteAll();
        DiaRefeicaoDAO.getInstance(context).deleteAll();
    }
}
