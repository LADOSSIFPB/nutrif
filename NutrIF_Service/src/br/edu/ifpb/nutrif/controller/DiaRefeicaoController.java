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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.EditalDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.Refeicao;

@Path("diarefeicao")
public class DiaRefeicaoController {

	private static Logger logger = LogManager.getLogger(
			DiaRefeicaoController.class);
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
				
				// Recuperar Edital
				int idEdital = diaRefeicao.getEdital().getId();
				Edital edital = EditalDAO.getInstance().getById(idEdital);
				diaRefeicao.setEdital(edital);
				
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
				
				// Validar Edital: vigencia e quantidade de contemplados.
				long quantidadeBeneficiadosPrevista = DiaRefeicaoDAO
						.getInstance().getQuantidadeAlunoDiaRefeicao(
								idEdital);
				
				if (aluno != null
						&& edital != null
						&& dia != null 
						&& refeicao != null
						&& funcionario != null) {
					
					// Verifica se existe dia de refeição ativo para a mesma
					// refeição e dia.
					boolean isDiaRefeicaoAtivo = DiaRefeicaoDAO.getInstance()
							.isDiaRefeicaoAtivo(diaRefeicao);
					logger.info("DiaRefeição ativo: " + isDiaRefeicaoAtivo);
					
					if (!isDiaRefeicaoAtivo) {
						
						// Data e hora atual.
						Date agora = new Date();
						diaRefeicao.setDataInsercao(agora);
						
						//Inserir o CronogramaRefeicao.
						Integer idDiaRefeicao = DiaRefeicaoDAO.getInstance()
								.insert(diaRefeicao);
						
						if (idDiaRefeicao != BancoUtil.ID_VAZIO) {
		
							// Operação realizada com sucesso.
							builder.status(Response.Status.OK);
							builder.entity(diaRefeicao);
							
						} else {
							
							builder.status(Response.Status.NOT_MODIFIED);
						}
					
					} else {
						
						builder.status(Response.Status.CONFLICT).entity(
								ErrorFactory.getErrorFromIndex(
										ErrorFactory.DIA_REFEICAO_DIPLICADO));
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
		
		logger.info("Consulta do Dia da Refeição pela Matrícula: " + matricula);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
					.getInstance().getAllByAlunoMatricula(matricula);
			logger.debug("Dias das Refeições: " + diasRefeicao);
			
			builder.status(Response.Status.OK);
			builder.entity(diasRefeicao);

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();		
	}
	
	@PermitAll
	@GET
	@Path("/quantidadebeneficiado/edital/{id}")
	@Produces("application/json")
	public Response getQuantidadeAlunoDiaRefeicao(
			@PathParam("id") int idEdital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			Edital edital = EditalDAO.getInstance().getById(idEdital);
			
			if (edital != null) {
				
				Long quantidadeBeneficados = DiaRefeicaoDAO.getInstance()
						.getQuantidadeAlunoDiaRefeicao(idEdital);
				
				
				edital.setQuantidadeBeneficiadosReal(
						Integer.valueOf(quantidadeBeneficados.toString()));
				
				builder.status(Response.Status.OK);
				builder.entity(edital);
			}			

		} catch (SQLExceptionNutrIF qme) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					qme.getError());
		}		
		
		return builder.build();	
	}
}