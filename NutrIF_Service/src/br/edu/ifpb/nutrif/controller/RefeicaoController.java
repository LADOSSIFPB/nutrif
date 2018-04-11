package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.enumeration.TipoRole;

@Path("refeicao")
public class RefeicaoController {

	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Refeicao refeicao) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Validação dos dados de entrada.
		int validacao = Validate.refeicao(refeicao);

		if (validacao == Validate.VALIDATE_OK) {

			try {
				
				int idCampus = refeicao.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);

				if (campus != null) {
					
					refeicao.setCampus(campus);
					
					// Inserir a Refeição.
					Integer idRefeicao = RefeicaoDAO.getInstance().insert(refeicao);

					if (idRefeicao != BancoUtil.ID_VAZIO) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(refeicao);
					}
				} else {

					// Dados não encontrados na busca para compor a entidade a ser inserida.
					builder.entity(ErrorFactory.getErrorFromIndex(
							ErrorFactory.DADOS_NAO_ECONTRADOS));
				}

			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Produces("application/json")
	public List<Refeicao> getAll() {
		
		List<Refeicao> refeicoes = new ArrayList<Refeicao>();
		
		refeicoes = RefeicaoDAO.getInstance().getAll();
		
		return refeicoes;
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/{id}")
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
					exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Listar Refeição pesquisando pelo Tipo.
	 * 
	 * @param tipo
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@PermitAll
	@GET
	@Path("/tipo/{tipo}")
	@Produces("application/json")
	public Response listByTipo(@PathParam("tipo") String tipo) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Refeicao> refeicoes = new ArrayList<Refeicao>();
		
		try {

			refeicoes = RefeicaoDAO.getInstance().listByTipo(tipo);
			
			builder.status(Response.Status.OK);
			builder.entity(refeicoes);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Atualizar dados da Refeição.
	 * 
	 * @param refeicao
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Refeicao refeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.refeicao(refeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				int idCampus = refeicao.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);

				if (campus != null) {
					
					refeicao.setCampus(campus);
				
					//Atualizar o Aluno.
					refeicao = RefeicaoDAO.getInstance().update(refeicao);
					
					if (refeicao != null) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);					
						builder.entity(refeicao);
					}
				} else {

					// Dados não encontrados na busca para compor a entidade a ser inserida.
					builder.entity(ErrorFactory.getErrorFromIndex(
							ErrorFactory.DADOS_NAO_ECONTRADOS));
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			} 
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}				
		
		return builder.build();		
	}
}
