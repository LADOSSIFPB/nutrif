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

import br.edu.ifpb.nutrif.dao.EventoDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Evento;

@Path("evento")
public class EventoController {
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Evento> getAll() {
		
		List<Evento> eventos = new ArrayList<Evento>();
		
		eventos = EventoDAO.getInstance().getAll();
		
		return eventos;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getEditalById(@PathParam("id") int idEvento) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Evento evento = EventoDAO.getInstance().getById(idEvento); 
			
			builder.status(Response.Status.OK).entity(evento);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
}
