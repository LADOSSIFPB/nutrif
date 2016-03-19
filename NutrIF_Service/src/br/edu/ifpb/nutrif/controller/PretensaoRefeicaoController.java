package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.PretensaoRefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.PretensaoRefeicao;

@Path("pretencaorefeicao")
public class PretensaoRefeicaoController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(PretensaoRefeicao pretencaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.pretencaoRefeicao(pretencaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idPretencaoRefeicao = PretensaoRefeicaoDAO.getInstance()
						.insert(pretencaoRefeicao);
				
				if (idPretencaoRefeicao != BancoUtil.IDVAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(pretencaoRefeicao);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			}
			
		} else {
			
			Erro erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	@PermitAll
	@POST
	@Path("/chaveacesso/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verifyChaveAcesso(PretensaoRefeicao pretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.expires(new Date());
		
		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<PretensaoRefeicao> getAll() {
		
		List<PretensaoRefeicao> pretencaoRefeicao = 
				new ArrayList<PretensaoRefeicao>();
		
		pretencaoRefeicao = PretensaoRefeicaoDAO.getInstance().getAll();
		
		return pretencaoRefeicao;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getPretencaoRefeicaoById(@PathParam("id") int idPretencaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			PretensaoRefeicao pretencaoRefeicao = PretensaoRefeicaoDAO
					.getInstance().getById(idPretencaoRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(pretencaoRefeicao);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
}
