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

import br.edu.ifpb.nutrif.dao.SetorDAO;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Setor;

@Path("setor")
public class SetorController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Setor setor) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.setor(setor);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				//Inserir o Aluno.
				Integer idSetor = SetorDAO.getInstance().insert(setor);
				
				if (idSetor != BancoUtil.ID_VAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(setor);
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
	@Path("/listar")
	@Produces("application/json")
	public List<Setor> getAll() {
		
		List<Setor> setor = new ArrayList<Setor>();
		
		setor = SetorDAO.getInstance().getAll();
		
		return setor;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getRefeicaoById(@PathParam("id") int idSetor) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Setor setor = SetorDAO.getInstance().getById(idSetor); 
			
			builder.status(Response.Status.OK);
			builder.entity(setor);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
}
