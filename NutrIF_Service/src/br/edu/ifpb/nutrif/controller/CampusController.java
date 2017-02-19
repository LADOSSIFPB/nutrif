package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.enumeration.TipoRole;

@Path("campus")
public class CampusController {

	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Campus> getAll() {
		
		List<Campus> campi = new ArrayList<Campus>();
		
		campi = CampusDAO.getInstance().getAll();
		
		return campi;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getCursoById(@PathParam("id") int idCampus) {
		
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
}
