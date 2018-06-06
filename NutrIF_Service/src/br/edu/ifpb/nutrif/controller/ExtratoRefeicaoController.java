package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import br.edu.ifpb.nutrif.dao.ExtratoRefeicaoDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.ExtratoRefeicao;
import br.edu.ladoss.enumeration.TipoRole;

@Path("extratorefeicao")
public class ExtratoRefeicaoController {

	@PermitAll
	@GET
	@Produces("application/json")
	public List<ExtratoRefeicao> getAll() {
		
		List<ExtratoRefeicao> extratosRefeicoes = new ArrayList<ExtratoRefeicao>();
		
		extratosRefeicoes = ExtratoRefeicaoDAO.getInstance().getAll();
		
		return extratosRefeicoes;
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getById(@PathParam("id") int idExtratoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			ExtratoRefeicao extratoRefeicao = ExtratoRefeicaoDAO.getInstance()
					.getById(idExtratoRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(extratoRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/inicio/{inicio}/fim/{fim}")
	@Produces("application/json")
	public Response listByPeriodo(@PathParam("inicio") Long inicio, @PathParam("fim") Long fim) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {
			
			Date dataInicial = DateUtil.longToDate(inicio);
			Date dataFinal = DateUtil.longToDate(fim);
			
			List<ExtratoRefeicao> extratosRefeicoes = ExtratoRefeicaoDAO.getInstance()
					.listByPeriodo(dataInicial, dataFinal);
			builder.status(Response.Status.OK);
			builder.entity(extratosRefeicoes);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
}
