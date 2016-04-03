package br.edu.nutrif.network;

import java.util.List;

import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.Pessoa;
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

    @GET("status")
    Call<Void> status();

    @POST("aluno/acesso/inserir")
    Call<Aluno> inserir(@Body Aluno aluno);

    @GET("aluno/id/{id}")
    Call<Aluno> getMatricula(@Header("Authorization") String accessKey, @Path("id")String id);

    @POST("aluno/confirmar")
    Call<Void> confirmar(@Body ConfirmationKey confirmation);

    @POST("pessoa/login")
    Call<Pessoa> login(@Body Pessoa pessoa);

    @POST("pretensaorefeicao/inserir")
    Call<PretensaoRefeicao> pedirRefeicao(@Header("Authorization") String accessKey,
                                          @Body PretensaoRefeicao pretencaoRefeicao);

    @GET("diarefeicao/listar/aluno/matricula/{matricula}")
    Call<List<DiaRefeicao>> listaRefeicoes(@Header("Authorization") String accessKey,
                                           @Path("matricula")String matricula);

    @POST("pretensaorefeicao/diarefeicao/verificar")
    Call<PretensaoRefeicao> infoRefeicao(@Header("Authorization") String accessKey,
                                         @Body PretensaoRefeicao refeicao);

}
