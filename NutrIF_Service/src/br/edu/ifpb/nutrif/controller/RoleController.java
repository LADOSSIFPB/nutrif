package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Role;

@Path("role")
public class RoleController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Role role) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.role(role);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idCurso = RoleDAO.getInstance().insert(role);
				
				if (idCurso != BancoUtil.IDVAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(role);
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
	@Path("/listar")
	@Produces("application/json")
	public List<Role> getAll() {
		
		List<Role> roles = new ArrayList<Role>();
		
		roles = RoleDAO.getInstance().getAll();
		
		return roles;
	}
}
