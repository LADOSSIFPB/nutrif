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

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
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
	@PermitAll
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
				
				if (aluno != null 
						&& dia != null 
						&& refeicao != null) {
					
					//Inserir o CronogramaRefeicao.
					Integer idDiaRefeicao = DiaRefeicaoDAO.getInstance()
							.insert(diaRefeicao);
					
					if (idDiaRefeicao != BancoUtil.IDVAZIO) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(diaRefeicao);
						
					} else {
						
						builder.status(Response.Status.NOT_MODIFIED);
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
	@PermitAll
	@POST
	@Path("/remover")
	@Consumes("application/json")
	@Produces("application/json")
	public Response remover(DiaRefeicao diaRefeicao) {
		
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
				
				//TODO: Implementar remoção.
			
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
	public List<DiaRefeicao> getAll() {
		
		List<DiaRefeicao> diasRefeicao = 
				new ArrayList<DiaRefeicao>();
		
		diasRefeicao = DiaRefeicaoDAO.getInstance().getAll();
		
		return diasRefeicao;
	}
	
	@PermitAll
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
			
			if (cronogramaRefeicao != null) {
				
				builder.status(Response.Status.OK);
				builder.entity(cronogramaRefeicao);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND);
			}			

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}

		return builder.build();
	}	
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/buscar/aluno/nome/{nome}")
	@Produces("application/json")
	public Response getDiaRefeicaoByAlunoNome(
			@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> cronogramasRefeicao = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoByAlunoNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}	
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/buscar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getDiaRefeicaoByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> cronogramasRefeicao = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoByAlunoMatricula(matricula);
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}
	
	@PermitAll
	@GET
	@Path("/listar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getAllByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> cronogramasRefeicao = DiaRefeicaoDAO
					.getInstance().getAllByAlunoMatricula(matricula);
			
			builder.status(Response.Status.OK);
			builder.entity(cronogramasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}
}