package br.edu.nutrif.network;

import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.input.ConfirmationKey;
import br.edu.nutrif.entitys.input.FormularioLogin;
import br.edu.nutrif.entitys.PretencaoRefeicao;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by juan on 14/03/16.
 */
public interface APIService {

    @POST("aluno/acesso/inserir")
    Call<Aluno> inserir(@Body Aluno aluno);

    @POST("aluno/confirmar")
    Call<Void> confirmar(@Body ConfirmationKey confirmation);

    @POST("aluno/login")
    Call<Aluno> login(@Body FormularioLogin aluno);

    @POST("pretensaorefeicao/inserir")
    Call<PretencaoRefeicao> pedirRefeicao(@Body PretencaoRefeicao pretencaoRefeicao);

}
