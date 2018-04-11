package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.NivelDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Nivel;

@Path("nivel")
public class NivelController {
	
	@PermitAll
	@GET
	@Produces("application/json")
	public List<Nivel> getAll() {
		
		List<Nivel> niveis = new ArrayList<Nivel>();
		
		niveis = NivelDAO.getInstance().getAll();
		
		return niveis;
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getById(@PathParam("id") int idNivel) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Nivel nivel = NivelDAO.getInstance().getById(idNivel); 
			
			builder.status(Response.Status.OK);
			builder.entity(nivel);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}	
}
