package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;
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
				
				// Recuperar Refeição.
				int idRefeicao = diaRefeicao.getRefeicao().getId();
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao);
				diaRefeicao.setRefeicao(refeicao);
				
				// Recuperar Funcionário
				int idFuncionario = diaRefeicao.getFuncionario().getId();
				Funcionario funcionario = FuncionarioDAO.getInstance()
						.getById(idFuncionario);
				diaRefeicao.setFuncionario(funcionario);
				
				if (aluno != null 
						&& dia != null 
						&& refeicao != null
						&& funcionario != null) {
					
					// Data e hora atual.
					Date agora = new Date();
					diaRefeicao.setDataInsercao(agora);
					
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
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}				
		
		return builder.build();		
	}
	
	/**
	 * Remover o dia da refeição através do id. A remoção desativa o registro
	 * pela mudança de estado da variável <b>ativo<b> para o valor false.
	 * 
	 * Entrada: JSON
	 * {
	 * 	"id":"[0-9]"
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
				
				// Recuperar Dia da Refeição.
				diaRefeicao = DiaRefeicaoDAO.getInstance().getById(
						diaRefeicao.getId());
				
				// Desabilitar.
				diaRefeicao.setAtivo(false);
				
				// Atualizar.
				diaRefeicao = DiaRefeicaoDAO.getInstance().update(diaRefeicao);
				
				if (diaRefeicao != null) {
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
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
	
	@RolesAllowed({"admin"})
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<DiaRefeicao> getAll() {
		
		List<DiaRefeicao> diasRefeicao = 
				new ArrayList<DiaRefeicao>();
		
		diasRefeicao = DiaRefeicaoDAO.getInstance().getAll();
		
		return diasRefeicao;
	}
	
	/**
	 * Recupera o dia da refeição (ativa ou inativa) através do id.
	 * @param idCronogramaRefeicao
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getDiaRefeicaoById(
			@PathParam("id") int idCronogramaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			DiaRefeicao diaRefeicao = DiaRefeicaoDAO
					.getInstance().getById(idCronogramaRefeicao); 
			
			if (diaRefeicao != null) {
				
				builder.status(Response.Status.OK);
				builder.entity(diaRefeicao);
				
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
	 * Buscar os dias de refeição de um Aluno através do seu Nome. Somente serão
	 * retornados os registros que não estejam como refeição realizada.
	 *  
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/buscar/refeicaorealizada/aluno/nome/{nome}")
	@Produces("application/json")
	public Response getDiaRefeicaoRealizacaoByAlunoNome(
			@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoRealizadaByAlunoNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(diasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}	
	
	/**
	 * Buscar os dias de refeição de um Aluno através do sua Matrícula. 
	 * Somente serão retornados os registros que não estejam como refeição
	 * realizada.
	 *   
	 * @param matricula
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/buscar/refeicaorealizada/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getDiaRefeicaoRealizacaoByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoRealizadaByAlunoMatricula(
							matricula);
			
			builder.status(Response.Status.OK);
			builder.entity(diasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}
	
	/**
	 * Listar todos os dias de refeições <b>ativos<b> de um Aluno pela Matrícula.
	 * 
	 * @param matricula
	 * @return diasRefeicao
	 */
	@PermitAll
	@GET
	@Path("/listar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getAllByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
					.getInstance().getAllByAlunoMatricula(matricula);
			
			builder.status(Response.Status.OK);
			builder.entity(diasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}
}