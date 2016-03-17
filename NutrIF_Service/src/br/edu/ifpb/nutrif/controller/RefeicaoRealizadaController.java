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

import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.RefeicaoRealizada;

@Path("refeicaorealizada")
public class RefeicaoRealizadaController {

	/**
	 * 
	 * @param refeicaoRealizada
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(RefeicaoRealizada refeicaoRealizada) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.refeicaoRealizada(refeicaoRealizada);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar o Cronograma da Refeição.
				int idCronogramaRefeicao = refeicaoRealizada
						.getConfirmaRefeicaoDia()
						.getDiaRefeicao()
						.getId();
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO
						.getInstance().getById(idCronogramaRefeicao);
				
				if (diaRefeicao != null) {
					
					ConfirmaRefeicaoDia confirmaRefeicaoDia = new ConfirmaRefeicaoDia();
					confirmaRefeicaoDia.setDiaRefeicao(diaRefeicao);
					confirmaRefeicaoDia.setDataRefeicao(new Date());
					
					// Cronograma Refeição completo.
					refeicaoRealizada.setConfirmaRefeicaoDia(confirmaRefeicaoDia);
					refeicaoRealizada.setHoraRefeicao(new Date());
					
					//Inserir o Aluno.
					boolean success = RefeicaoRealizadaDAO.getInstance()
							.insertOrUpdate(refeicaoRealizada);					
					
					if (success) {
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
					}					
				}				
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			}
		} else {
			
			Erro erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}					
		
		return builder.build();		
	}
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<RefeicaoRealizada> getAll() {
		
		List<RefeicaoRealizada> refeicaoRealizada = 
				new ArrayList<RefeicaoRealizada>();
		
		refeicaoRealizada = RefeicaoRealizadaDAO.getInstance().getAll();
		
		return refeicaoRealizada;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getRefeicaoById(@PathParam("id") int idRefeicaoRealizada) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			RefeicaoRealizada refeicaoRealizada = RefeicaoRealizadaDAO
					.getInstance().getById(idRefeicaoRealizada); 
			
			builder.status(Response.Status.OK);
			builder.entity(refeicaoRealizada);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());			
		}

		return builder.build();
	}
}
