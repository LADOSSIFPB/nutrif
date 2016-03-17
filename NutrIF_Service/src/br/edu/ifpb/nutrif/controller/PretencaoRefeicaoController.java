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

import br.edu.ifpb.nutrif.dao.PretencaoRefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.PretencaoRefeicao;

@Path("pretencaorefeicao")
public class PretencaoRefeicaoController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(PretencaoRefeicao pretencaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.pretencaoRefeicao(pretencaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idPretencaoRefeicao = PretencaoRefeicaoDAO.getInstance()
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
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<PretencaoRefeicao> getAll() {
		
		List<PretencaoRefeicao> pretencaoRefeicao = 
				new ArrayList<PretencaoRefeicao>();
		
		pretencaoRefeicao = PretencaoRefeicaoDAO.getInstance().getAll();
		
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

			PretencaoRefeicao pretencaoRefeicao = PretencaoRefeicaoDAO
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
