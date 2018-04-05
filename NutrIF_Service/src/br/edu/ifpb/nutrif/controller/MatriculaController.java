package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.MatriculaDAO;
import br.edu.ifpb.nutrif.dao.PeriodoDAO;
import br.edu.ifpb.nutrif.dao.SituacaoMatriculaDAO;
import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ifpb.nutrif.dao.TurnoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Matricula;
import br.edu.ladoss.entity.Periodo;
import br.edu.ladoss.entity.SituacaoMatricula;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.entity.Turno;
import br.edu.ladoss.enumeration.TipoRole;

@Path("matricula")
public class MatriculaController {

	@RolesAllowed({ TipoRole.ADMIN })
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Matricula matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.inserirMatricula(matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				// Aluno
				int idAluno = matricula.getAluno().getId();
				Aluno aluno = AlunoDAO.getInstance().getById(idAluno);
				
				// Curso
				int idCurso = matricula.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				
				// Turno
				int idTurno = matricula.getTurno().getId();
				Turno turno = TurnoDAO.getInstance().getById(idTurno);
				
				// Periodo
				int idPeriodo = matricula.getPeriodo().getId();
				Periodo periodo = PeriodoDAO.getInstance().getById(idPeriodo);
						
				// Turma
				int idTurma = matricula.getTurma().getId();
				Turma turma = TurmaDAO.getInstance().getById(idTurma);
						
				if (aluno != null
						&& curso != null
						&& turno != null 
						&& turma != null
						&& periodo != null) {
					
					matricula.setAluno(aluno);
					matricula.setCurso(curso);
					matricula.setTurno(turno);
					matricula.setTurma(turma);
					matricula.setPeriodo(periodo);
					
					// SituacaoMatricula
					int idSituacaoMatricula = SituacaoMatricula.ID_MATRICULADO;
					SituacaoMatricula situacaoMatricula = SituacaoMatriculaDAO.getInstance()
							.getById(idSituacaoMatricula);					
					matricula.setSituacao(situacaoMatricula);
					
					//Inserir a Matrícula.
					Integer idMatricula = MatriculaDAO.getInstance()
							.insert(matricula);
					
					if (idMatricula != BancoUtil.ID_VAZIO) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(matricula);
					}
				} else {
					
					// Dados não encontrados na busca para compor a entidade a ser inserida.
					builder.entity(ErrorFactory.getErrorFromIndex(
									ErrorFactory.DADOS_NAO_ECONTRADOS));
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
	
	@RolesAllowed({TipoRole.ADMIN})
	@DELETE
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response remover(@PathParam("id") int idMatricula) {		
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.removerMatricula(idMatricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Matrícula.
				Matricula matricula = MatriculaDAO.getInstance().getById(idMatricula);
				
				// Desabilitar.
				matricula.setAtivo(BancoUtil.INATIVO);
				
				// Atualizar.
				matricula = MatriculaDAO.getInstance().update(matricula);
				
				if (matricula != null) {
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
				} else {
					
					// Dados não encontrados na busca para compor a entidade a ser atualizada para remoção.
					builder.entity(ErrorFactory.getErrorFromIndex(
									ErrorFactory.DADOS_NAO_ECONTRADOS));
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
	
	@DenyAll
	@GET
	@Produces("application/json")
	public List<Matricula> getAll() {
		
		List<Matricula> matriculas = new ArrayList<Matricula>();
		
		matriculas = MatriculaDAO.getInstance().getAll();
		
		return matriculas;
	}
	
	@PermitAll
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getMatriculaById(@PathParam("id") int idMatricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Matricula matricula = MatriculaDAO.getInstance().getById(idMatricula); 
			
			builder.status(Response.Status.OK).entity(matricula);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
	
	/**
	 * Listar Matrícula pesquisando pelo número.
	 * 
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/numero/{numero}")
	@Produces("application/json")
	public Response getByNumero(@PathParam("numero") String numero) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Matricula matricula = MatriculaDAO.getInstance().getByNumero(numero);

			builder.status(Response.Status.OK);
			builder.entity(matricula);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Listar Matrícula pesquisando pelo número.
	 * 
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/aluno/{id}")
	@Produces("application/json")
	public Response getByAlunoId(@PathParam("id") Integer idAluno) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Matricula> matriculas = new ArrayList<Matricula>();
		
		try {
						
			matriculas = MatriculaDAO.getInstance().getByAlunoId(idAluno);

			builder.status(Response.Status.OK);
			builder.entity(matriculas);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getError());
		}

		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/aluno/{id}/vigentes")
	@Produces("application/json")
	public Response getVigentesByAlunoId(@PathParam("id") Integer idAluno) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Matricula> matriculas = new ArrayList<Matricula>();

		try {

			matriculas = MatriculaDAO.getInstance().getVigentesByAlunoId(idAluno);

			builder.status(Response.Status.OK);
			builder.entity(matriculas);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Atualizar dados básicos do Aluno: nome, matrícula, campus, curso, período,
	 * turma e turno.
	 * 
	 * @param matricula
	 * @return
	 */
	@RolesAllowed({ TipoRole.ADMIN })
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Matricula matricula) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Validação dos dados de entrada.
		int validacao = Validate.atualizarMatricula(matricula);

		if (validacao == Validate.VALIDATE_OK) {

			try {
				// Aluno
				int idAluno = matricula.getAluno().getId();
				Aluno aluno = AlunoDAO.getInstance().getById(idAluno);
				
				// Curso
				int idCurso = matricula.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				
				// Turno
				int idTurno = matricula.getTurno().getId();
				Turno turno = TurnoDAO.getInstance().getById(idTurno);
				
				// Periodo
				int idPeriodo = matricula.getPeriodo().getId();
				Periodo periodo = PeriodoDAO.getInstance().getById(idPeriodo);
						
				// Turma
				int idTurma = matricula.getTurma().getId();
				Turma turma = TurmaDAO.getInstance().getById(idTurma);
				
				int idSituacao = matricula.getSituacao().getId();
				SituacaoMatricula situacao = SituacaoMatriculaDAO.getInstance().getById(idSituacao);
				
				if (aluno != null
						&& curso != null
						&& turno != null 
						&& turma != null
						&& periodo != null
						&& situacao != null) {
					
					matricula.setAluno(aluno);
					matricula.setCurso(curso);
					matricula.setTurno(turno);
					matricula.setTurma(turma);
					matricula.setPeriodo(periodo);
					matricula.setSituacao(situacao);
					
					// Atualizar o Aluno.
					matricula = MatriculaDAO.getInstance().update(matricula);

					if (matricula != null) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(matricula);
					}
				}
				
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}
}
