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
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.enumeration.TipoRole;

@Path("campus")
public class CampusController {

	//TODO: @RolesAllowed({TipoRole.ADMIN})
	@PermitAll
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Campus campus) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.campus(campus);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Sigla
				String sigla = campus.getSigla();
				campus.setSigla(sigla.toUpperCase());
				
				//Inserir o Campus.
				Integer idCampus = CampusDAO.getInstance().insert(campus);
				
				if (idCampus != BancoUtil.ID_VAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(campus);
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
	
	@PermitAll
	@GET
	@Produces("application/json")
	public List<Campus> getAll() {
		
		List<Campus> campi = new ArrayList<Campus>();
		
		campi = CampusDAO.getInstance().getAll();
		
		return campi;
	}
	
	/**
	 * Atualizar dados de um Campus.
	 * 
	 * @param campus
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Campus campus) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.campus(campus);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Atualizar o Curso.
				campus = CampusDAO.getInstance().update(campus);
				
				if (campus != null) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);					
					builder.entity(campus);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			} 
		}				
		
		return builder.build();		
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getCampusById(@PathParam("id") int idCampus) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Campus campus = CampusDAO.getInstance().getById(idCampus); 
			
			builder.status(Response.Status.OK).entity(campus);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
	
	/**
	 * Listar Campi pesquisando pela Cidade.
	 * 
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/cidade/{cidade}")
	@Produces("application/json")
	public Response listByCidade(@PathParam("cidade") String cidade) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Campus> campi = new ArrayList<Campus>();

		try {

			campi = CampusDAO.getInstance().listByCidade(cidade);

			builder.status(Response.Status.OK);
			builder.entity(campi);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getError());
		}

		return builder.build();
	}
}
