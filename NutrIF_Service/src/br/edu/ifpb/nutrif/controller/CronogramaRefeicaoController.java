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

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CronogramaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.CronogramaRefeicao;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.Refeicao;

@Path("cronogramarefeicao")
public class CronogramaRefeicaoController {

	/**
	 * Entrada: JSON
	 * {
	 * 	"aluno":{"id":"[0-9]"},
	 * 	"dia":{"id":"[0-9]"},
	 * 	"refeicao":{"id":"[0-9]"}
	 * }
	 * 
	 * @param cronogramaRefeicao
	 * @return builder
	 */
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(CronogramaRefeicao cronogramaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.cronogramaRefeicao(cronogramaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Aluno.
				int idAluno = cronogramaRefeicao.getAluno().getId();
				Aluno aluno = AlunoDAO.getInstance().getById(idAluno);
				cronogramaRefeicao.setAluno(aluno);
				
				// Recuperar Dia.
				int idDia = cronogramaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				cronogramaRefeicao.setDia(dia);
				
				// Recuperar Refeicao.
				int idRefeicao = cronogramaRefeicao.getRefeicao().getId();
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao);
				cronogramaRefeicao.setRefeicao(refeicao);
				
				if (aluno != null && dia != null && refeicao != null) {
					
					//Inserir o CronogramaRefeicao.
					Integer idCronogramaRefeicao = CronogramaRefeicaoDAO.getInstance()
							.insert(cronogramaRefeicao);
					
					if (idCronogramaRefeicao != BancoUtil.IDVAZIO) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(cronogramaRefeicao);
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
	public List<CronogramaRefeicao> getAll() {
		
		List<CronogramaRefeicao> cronogramaRefeicao = 
				new ArrayList<CronogramaRefeicao>();
		
		cronogramaRefeicao = CronogramaRefeicaoDAO.getInstance().getAll();
		
		return cronogramaRefeicao;
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getCronogramaRefeicaoById(
			@PathParam("id") int idCronogramaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			CronogramaRefeicao cronogramaRefeicao = CronogramaRefeicaoDAO
					.getInstance().getById(idCronogramaRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramaRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getErro());
		}

		return builder.build();
	}	
	
	@POST
	@Path("/buscar/aluno")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getCronogramaRefeicaoByAluno(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<CronogramaRefeicao> cronogramasRefeicao = CronogramaRefeicaoDAO
					.getInstance().getCronogramaRefeicaoByAluno(aluno.getNome());
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getErro());
		}		
		
		return builder.build();		
	}
}