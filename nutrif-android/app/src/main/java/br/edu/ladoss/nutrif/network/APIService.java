package br.edu.ladoss.nutrif.network;



import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.model.DiaRefeicao;
import br.edu.ladoss.nutrif.model.Pessoa;
import br.edu.ladoss.nutrif.model.PretensaoRefeicao;
import br.edu.ladoss.nutrif.model.input.ConfirmationKey;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Streaming;

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
    Call<PretensaoRefeicao>     infoRefeicao(@Header("Authorization") String accessKey,
                                         @Body PretensaoRefeicao refeicao);
    @Multipart
    @POST("arquivo/upload/ARQUIVO_FOTO_PERFIL")
     Call<Void> upload(@Header("Authorization") String accessKey,
                       @Part("fileName") RequestBody fileName,
                       @Part("uploadedFile") RequestBody file ,
                       @Part("idPessoa") int idPessoa);

    @GET("arquivo/download/perfil/aluno/{id}")
    @Streaming
    Call<ResponseBody> download(@Header("Authorization") String accessKey,
                                @Path("id")String id);

}
