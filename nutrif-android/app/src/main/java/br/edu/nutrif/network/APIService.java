package br.edu.nutrif.network;

import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.ConfirmationKey;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by juan on 14/03/16.
 */
public interface APIService {

    @POST("aluno/acesso/inserir")
    Call<Aluno> inserir(@Body Aluno aluno);

    @POST("aluno/confirmar")
    Call<Void> confirmar(@Body ConfirmationKey confirmation);

}
