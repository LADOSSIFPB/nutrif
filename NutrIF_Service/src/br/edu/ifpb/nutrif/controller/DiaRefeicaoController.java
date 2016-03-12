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
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.Refeicao;

@Path("diarefeicao")
public class DiaRefeicaoController {

	/**
	 * Entrada: JSON
	 * {
	 * 	"aluno":{"id":"[0-9]"},
	 * 	"dia":{"id":"[0-9]"},
	 * 	"refeicao":{"id":"[0-9]"}
	 * }
	 * 
	 * @param diaRefeicao
	 * @return builder
	 */
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.diaRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Aluno.
				int idAluno = diaRefeicao.getAluno().getId();
				Aluno aluno = AlunoDAO.getInstance().getById(idAluno);
				diaRefeicao.setAluno(aluno);
				
				// Recuperar Dia.
				int idDia = diaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				diaRefeicao.setDia(dia);
				
				// Recuperar Refeicao.
				int idRefeicao = diaRefeicao.getRefeicao().getId();
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao);
				diaRefeicao.setRefeicao(refeicao);
				
				if (aluno != null && dia != null && refeicao != null) {
					
					//Inserir o CronogramaRefeicao.
					Integer idCronogramaRefeicao = DiaRefeicaoDAO.getInstance()
							.insert(diaRefeicao);
					
					if (idCronogramaRefeicao != BancoUtil.IDVAZIO) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(diaRefeicao);
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
	public List<DiaRefeicao> getAll() {
		
		List<DiaRefeicao> diasRefeicao = 
				new ArrayList<DiaRefeicao>();
		
		diasRefeicao = DiaRefeicaoDAO.getInstance().getAll();
		
		return diasRefeicao;
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getCronogramaRefeicaoById(
			@PathParam("id") int idCronogramaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			DiaRefeicao cronogramaRefeicao = DiaRefeicaoDAO
					.getInstance().getById(idCronogramaRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramaRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getErro());
		}

		return builder.build();
	}	
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	@GET
	@Path("/buscar/aluno/nome/{nome}")
	@Produces("application/json")
	public Response getCronogramaRefeicaoByAlunoNome(
			@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> cronogramasRefeicao = DiaRefeicaoDAO
					.getInstance().getCronogramaRefeicaoByAlunoNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getErro());
		}		
		
		return builder.build();		
	}	
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	@GET
	@Path("/buscar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getCronogramaRefeicaoByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> cronogramasRefeicao = DiaRefeicaoDAO
					.getInstance().getCronogramaRefeicaoByAlunoNome(matricula);
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getErro());
		}		
		
		return builder.build();		
	}
}