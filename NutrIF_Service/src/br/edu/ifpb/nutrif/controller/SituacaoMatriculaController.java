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

import br.edu.ifpb.nutrif.dao.SituacaoMatriculaDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.SituacaoMatricula;

@Path("situacaomatricula")
public class SituacaoMatriculaController {

	@PermitAll
	@GET
	@Produces("application/json")
	public List<SituacaoMatricula> getAll() {

		List<SituacaoMatricula> situacaoMatricula = new ArrayList<SituacaoMatricula>();

		situacaoMatricula = SituacaoMatriculaDAO.getInstance().getAll();

		return situacaoMatricula;
	}

	@PermitAll
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getSituacaoMatriculaById(@PathParam("id") int idSituacaoMatricula) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			SituacaoMatricula situacaoMatricula = SituacaoMatriculaDAO.getInstance().getById(idSituacaoMatricula);

			builder.status(Response.Status.OK);
			builder.entity(situacaoMatricula);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
}
