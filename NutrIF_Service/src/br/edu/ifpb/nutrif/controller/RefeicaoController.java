package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.Refeicao;

@Path("refeicao")
public class RefeicaoController {

	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Refeicao refeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.refeicao(refeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idRefeicao = RefeicaoDAO.getInstance().insert(refeicao);
				
				if (idRefeicao != BancoUtil.IDVAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(refeicao);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getErro());			
			}
		}				
		
		return builder.build();		
	}
	
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Refeicao> getAll() {
		
		List<Refeicao> refeicoes = new ArrayList<Refeicao>();
		
		refeicoes = RefeicaoDAO.getInstance().getAll();
		
		return refeicoes;
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getRefeicaoById(@PathParam("id") int idRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(refeicao);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getErro());
		}

		return builder.build();
	}
}
