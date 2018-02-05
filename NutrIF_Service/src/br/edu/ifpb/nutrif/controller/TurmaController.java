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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.enumeration.TipoRole;

@Path("turma")
public class TurmaController {
	
	/**
	 * Inserir dados da Turma.
	 * 
	 * @param turma
	 * @return response
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Turma turma) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.turma(turma);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir a Turma.
				Integer idSetor = TurmaDAO.getInstance().insert(turma);
				
				if (idSetor != BancoUtil.ID_VAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(turma);
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
	@Produces("application/json")
	public List<Turma> getAll() {
		
		List<Turma> turmas = new ArrayList<Turma>();
		
		turmas = TurmaDAO.getInstance().getAll();
		
		return turmas;
	}
	
	/**
	 * Atualizar dados da Turma.
	 * 
	 * @param turma
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Turma turma) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.turma(turma);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Atualizar a Turma.
				turma = TurmaDAO.getInstance().update(turma);
				
				if (turma != null) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);					
					builder.entity(turma);
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
