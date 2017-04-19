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

import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.dao.EventoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Error;
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
	
	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Evento evento) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.evento(evento);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idRefeicao = EventoDAO.getInstance().insert(evento);
				
				if (idRefeicao != BancoUtil.ID_VAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(evento);
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
	
	/**
	 * Atualizar dados dos eventos.
	 * 
	 * @param evento
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/atualizar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Evento evento) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.evento(evento);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Atualizar o Curso.
				evento = EventoDAO.getInstance().update(evento);
				
				if (evento != null) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);					
					builder.entity(evento);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			} 
		}				
		
		return builder.build();		
	}
	
	/**
	 * Listar Evento(s) pelo nome.
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/listar/nome/{nome}")
	@Produces("application/json")
	public Response listByNome(@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Evento> eventos = new ArrayList<Evento>();
		
		try {

			eventos = EventoDAO.getInstance().listByNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(eventos);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}	
}