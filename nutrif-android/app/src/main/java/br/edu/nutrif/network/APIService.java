package br.edu.nutrif.network;

import java.util.List;

import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.PretensaoRefeicao;
import br.edu.nutrif.entitys.input.ConfirmationKey;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
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

    @POST("aluno/login")
    Call<Aluno> login(@Body Aluno aluno);

    @POST("pretensaorefeicao/inserir")
    Call<PretensaoRefeicao> pedirRefeicao(@Header("Authorization") String accessKey,
                                          @Body PretensaoRefeicao pretencaoRefeicao);

    @GET("diarefeicao/listar/pretensaorefeicao/aluno/matricula/{matricula}")
    Call<List<DiaRefeicao>> listaRefeicoes(@Header("Authorization") String accessKey,
                                           @Path("matricula")String matricula);

}
