package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
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
						.getCronogramaRefeicao()
						.getId();
				DiaRefeicao cronogramaRefeicao = DiaRefeicaoDAO
						.getInstance().getById(idCronogramaRefeicao);
				
				if (cronogramaRefeicao != null) {
					
					// Cronograma Refeição completo.
					refeicaoRealizada.getConfirmaRefeicaoDia()
						.setCronogramaRefeicao(cronogramaRefeicao);
					
					//Inserir o Aluno.
					Integer idRefeicaoRealizada = RefeicaoRealizadaDAO
							.getInstance().insert(refeicaoRealizada);
					
					if (idRefeicaoRealizada != BancoUtil.IDVAZIO) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(refeicaoRealizada);
					}
				}				
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getErro());			
			}
		} else {
			
			Erro erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}					
		
		return builder.build();		
	}
	
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<RefeicaoRealizada> getAll() {
		
		List<RefeicaoRealizada> refeicaoRealizada = 
				new ArrayList<RefeicaoRealizada>();
		
		refeicaoRealizada = RefeicaoRealizadaDAO.getInstance().getAll();
		
		return refeicaoRealizada;
	}
	
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
					exception.getErro());			
		}

		return builder.build();
	}
}
