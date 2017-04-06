package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.dao.LoginDAO;
import br.edu.ifpb.nutrif.dao.PeriodoDAO;
import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ifpb.nutrif.dao.TurmaDAO;
import br.edu.ifpb.nutrif.dao.TurnoDAO;
import br.edu.ifpb.nutrif.exception.EmailExceptionNutrIF;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.EmailUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.AlunoAcesso;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Login;
import br.edu.ladoss.entity.Periodo;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.entity.PessoaAcesso;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.entity.Turma;
import br.edu.ladoss.entity.Turno;
import br.edu.ladoss.enumeration.TipoRole;

@Path("aluno")
public class AlunoController {

	private static Logger logger = LogManager.getLogger(AlunoController.class);
	
	/**
	 * Inserir Aluno na base de dados.
	 * 
	 * Entrada:
	 * {
	 * 	"nome":"[a-z]",
	 * 	"matricula":"[1-9]",
	 * 	"curso":{"id":[1-9]}
	 * }
	 * 
	 * @param aluno
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.inserirAluno(aluno);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Nome do aluno somente com a primeira letra em maiúsculo.
				String nome = aluno.getNome();
				aluno.setNome(StringUtil.upperCaseFirstChar(nome));
				
				// Curso.
				int idCurso = aluno.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				aluno.setCurso(curso);
				
				// Campus
				int idCampus = aluno.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				aluno.setCampus(campus);
				
				// Ano/Período
				int idPeriodo = aluno.getPeriodo().getId();
				Periodo periodo = PeriodoDAO.getInstance().getById(idPeriodo);
				aluno.setPeriodo(periodo);
				
				// Turno
				int idTurno = aluno.getTurno().getId();
				Turno turno = TurnoDAO.getInstance().getById(idTurno);
				aluno.setTurno(turno);
				
				// Turma
				int idTurma = aluno.getTurma().getId();
				Turma turma = TurmaDAO.getInstance().getById(idTurma);
				aluno.setTurma(turma);
				
				// Role de acesso.
				Role role = RoleDAO.getInstance().getById(
						Role.Tipo.COMENSAL.getId());
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);				
				aluno.setRoles(roles);
				
				// Inativar o Aluno.
				aluno.setAtivo(BancoUtil.INATIVO);
				
				if (curso != null
						&& campus != null
						&& periodo != null
						&& turno != null
						&& turma != null
						&& roles.size() > 0) {
					
					//Inserir o Aluno.
					Integer idAluno = AlunoDAO.getInstance().insert(aluno);
					
					if (idAluno != BancoUtil.ID_VAZIO) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(aluno);
						
					} else {
						
						builder.status(Response.Status.NOT_MODIFIED);
					}
					
				} else {
					
					//TODO: Mensagem de erro para Curso, Campus ou Roles não encontrados.
					builder.status(Response.Status.NOT_FOUND);
				}				
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());				
			}
		
		} else {
			
			builder.status(Response.Status.BAD_REQUEST).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();		
	}
	
	/**
	 * Atualizar dados do Aluno.
	 * 
	 * @param aluno
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/atualizar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.inserirAluno(aluno);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Curso.
				int idCurso = aluno.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				aluno.setCurso(curso);
				
				//Atualizar o Aluno.
				aluno = AlunoDAO.getInstance().update(aluno);
				
				if (aluno != null) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);					
					builder.entity(aluno);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			}
			
		} else {
			
			builder.status(Response.Status.BAD_REQUEST).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();		
	}
	
	/**
	 * Listar todos os Alunos.
	 * 
	 * @return
	 */
	@DenyAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Aluno> getAll() {
		
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		alunos = AlunoDAO.getInstance().getAll();
		
		return alunos;
	}
	
	/**
	 * Recuperar Aluno pelo identificador.
	 * 
	 * @param idAluno
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getAlunoById(@PathParam("id") int idAluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Aluno aluno = AlunoDAO.getInstance().getById(idAluno); 
			
			if (aluno != null) {
				
				// Remover a senha.
				aluno.setSenha(StringUtil.STRING_VAZIO);
				aluno.setKeyConfirmation(StringUtil.STRING_VAZIO);
				
				builder.status(Response.Status.OK);
				builder.entity(aluno);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND);
			}

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Recuperar Aluno pela matrícula.
	 * 
	 * @param matricula
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/matricula/{matricula}")
	@Produces("application/json")
	public Response getAlunoByMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Aluno aluno = AlunoDAO.getInstance().getByMatricula(matricula); 
			
			if (aluno != null) {
				
				// Remover a senha.
				aluno.setSenha(StringUtil.STRING_VAZIO);
				aluno.setKeyConfirmation(StringUtil.STRING_VAZIO);
				
				builder.status(Response.Status.OK);
				builder.entity(aluno);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND);
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/listar/nome/{nome}")
	@Produces("application/json")
	public Response listByNome(@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {

			alunos = AlunoDAO.getInstance().listByNome(nome);
			
			builder.status(Response.Status.OK);
			builder.entity(alunos);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Inserir acesso do Aluno ao sistema via dispositivo móvel.
	 * TODO: Reavaliar processo de login do aluno. Usar o login do PessoaController.
	 * 
	 * Entrada:
	 * {
	 * 	"matricula":"[1-9]",
	 * 	"email":"[a-z]",
	 * 	"senha":"[a-z]" 	
	 * }
	 * 
	 * @param aluno
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/inserir/acesso")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertAcesso(AlunoAcesso alunoAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.inserirAcessoAluno(alunoAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				String matricula = alunoAcesso.getMatricula();
				
				// Recuperar Aluno através da matrícula.
				Aluno aluno = AlunoDAO.getInstance().getByMatricula(matricula);
				
				if (aluno != null 
						&& aluno.getId() != BancoUtil.ID_VAZIO) {					

					String email = alunoAcesso.getEmail();
					
					// Verificar se o usuário já tem acesso solicitado.					
					if (!aluno.isAcesso()) {
						
						// E-mail do Aluno.
						aluno.setEmail(email);
						
					} else {
						
						email = aluno.getEmail();
					}
					
					String senhaPlana = alunoAcesso.getSenha();
					
					// Criptografar senha.
					String senhaCriptografada = StringUtil.criptografarBase64(
							senhaPlana);				
					aluno.setSenha(senhaCriptografada);
					
					// Gerar chave de confirmação: KeyConfirmation.
					String keyConfirmation = StringUtil.getRadomKeyConfirmation();
					aluno.setKeyConfirmation(keyConfirmation);
					
					// Período
					Periodo periodo = PeriodoDAO.getInstance().getById(alunoAcesso.getPeriodo().getId());
					aluno.setPeriodo(periodo);
					
					// Turma
					Turma turma = TurmaDAO.getInstance().getById(
							alunoAcesso.getTurma().getId());
					aluno.setTurma(turma);
					
					// Turno
					Turno turno = TurnoDAO.getInstance().getById(
							alunoAcesso.getTurno().getId());
					aluno.setTurno(turno);
					
					// Ativar Aluno.
					aluno.setAtivo(BancoUtil.ATIVO);
					
					// Ativar solicitação de acesso.
					aluno.setAcesso(BancoUtil.ATIVO);
					
					//Inserir o Aluno.
					aluno = AlunoDAO.getInstance().update(aluno);
					
					if (aluno.getId() != BancoUtil.ID_VAZIO) {

						// Enviar e-mail com a chave de acesso.
						/*
						EmailUtil emailUtil = new EmailUtil();
						emailUtil.sendChaveConfirmacaoAluno(
								email, keyConfirmation, aluno.getNome());
						*/
						
						// Remover a senha.
						aluno.setSenha(StringUtil.STRING_VAZIO);
						aluno.setKeyConfirmation(StringUtil.STRING_VAZIO);
						
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(aluno);
					}
				
				} else {
					
					builder.status(Response.Status.UNAUTHORIZED).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.ALUNO_NAO_ENCONTRADO));
				}
			
			} catch (SQLExceptionNutrIF | EmailExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
				
			} catch (UnsupportedEncodingException exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
			}
			
		} else {
			
			builder.status(Response.Status.BAD_REQUEST).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();
	}
	
	/**
	 * Confirma chave de acesso do Aluno.
	 * 
	 * Entrada:
	 * {
	 * 	"keyConfirmation":"[a-zA-Z]*",
	 * 	"matricula":"[0-9]*"
	 * }
	 * 
	 * @param aluno
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/confirmar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response confirmarChave(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.confirmacaoChaveAluno(aluno);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				String matricula = aluno.getMatricula();
				String keyConfirmation = aluno.getKeyConfirmation();
				
				// Consultar chave de confirmação.
				boolean isKeyConfirmation = AlunoDAO.getInstance()
						.isKeyConfirmation(matricula, keyConfirmation);
				
				// Ativar aluno.
				if (isKeyConfirmation) {
					
					aluno = AlunoDAO.getInstance().getByMatricula(matricula);
					aluno.setAtivo(true);
					
					aluno = AlunoDAO.getInstance().update(aluno);
					builder.status(Response.Status.OK);
					
				} else {
					
					// Chave de autorização inválida.
					builder.status(Response.Status.UNAUTHORIZED).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.CHAVE_CONFIRMACAO_INVALIDA));
				}
				
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
			}
			
		} else {
			
			builder.status(Response.Status.BAD_REQUEST).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();
	}
	
	@PermitAll	
	@POST
	@Path("/email")
	@Consumes("application/json")
	@Produces("application/json")
	public Response sendEmail(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.expires(new Date());
		
		try {
		
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.send(aluno.getEmail(), "Ativação da conta.", 
				"Chave de confirmação: ");
		
		} catch (EmailExceptionNutrIF exception) {
			
			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}
		
		return builder.build();
	}
	
	@DenyAll	
	@GET
	@Path("/inserir/role")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertRole() {
		
		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.expires(new Date());
		
		try {
		
			List<Aluno> alunos = AlunoDAO.getInstance().getAll();
			
			for (Aluno aluno: alunos) {
				
				// Inserir role Aluno aos registros antigos.
				Role role = RoleDAO.getInstance().getById(
						Role.Tipo.COMENSAL.getId());
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);				
				aluno.setRoles(roles);
				
				AlunoDAO.getInstance().update(aluno);				
			}
			
			builder.status(Response.Status.OK);
		
		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}
		
		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/verificar/acesso/matricula/{matricula}")
	@Produces("application/json")
	public Response verificarAcesso(@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.verificarAcessoAluno(matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				Aluno aluno = AlunoDAO.getInstance().getByMatricula(matricula);
				
				if (aluno != null) {
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(aluno);
					
				} else {					
					
					Error error =  ErrorFactory.getErrorFromIndex(
							ErrorFactory.ALUNO_NAO_ENCONTRADO);
					
					builder.status(Response.Status.NOT_FOUND)
						.entity(error);
				}
				
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
			}
			
		} else {
			
			builder.status(Response.Status.BAD_REQUEST).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();
	}
	
	/**
	 * Login para Aluno. Retorna a chave de autenticação caso o usuário esteja
	 * com a matrícula e senha corretas.
	 * 
	 * @param pessoaAcesso
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response loginAluno(@HeaderParam("user-agent") String userAgent,
			@Context HttpServletRequest request,
			PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		logger.info("Login aluno: " + pessoaAcesso.getNome());
		logger.info("Host: " + request.getRemoteAddr());
		
		// Validação dos dados de entrada.
		int validacao = Validate.loginAluno(pessoaAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				String matricula = pessoaAcesso.getMatricula();
				Aluno aluno = AlunoDAO.getInstance()
						.getByMatricula(matricula);
				
				if (aluno != null) {			
				
					//Login Pessoa.
					Pessoa pessoa = AlunoDAO.getInstance().login(
							pessoaAcesso.getMatricula(), 
							pessoaAcesso.getSenha());
					
					if (pessoa != null) {
						
						// Registro do Login
						Date agora = new Date();
						
						// Gerar AuthKey.
						String keyAuth = StringUtil.criptografarSha256(
								agora.toString());
						pessoaAcesso.setKeyAuth(keyAuth);
						
						Login login = new Login();
						login.setPessoa(pessoa);
						login.setRegistro(agora);
						login.setUserAgent(userAgent);
						login.setRemoteAddr(request.getRemoteAddr());
						login.setKeyAuth(keyAuth);
						login.setLoged(true);
						
						// Registro de Login para a Pessoa.
						int idLogin = LoginDAO.getInstance().insert(login);
						
						if (idLogin != BancoUtil.ID_VAZIO) {
							
							// Pessoa
							pessoaAcesso = PessoaAcesso.getInstance(
									pessoa);
							pessoaAcesso.setMatricula(matricula);
							
							// Chave de autenticação gerada.
							pessoaAcesso.setKeyAuth(login.getKeyAuth());
							
							// Operação realizada com sucesso.
							builder.status(Response.Status.OK);
							builder.entity(pessoaAcesso);
						}					
					
					} else {
						
						// Matrícula do aluno não encontrada.
						Error error =  ErrorFactory.getErrorFromIndex(
								ErrorFactory.SENHA_USUARIO_INVALIDA);
						
						builder.status(Response.Status.UNAUTHORIZED).entity(error);
					}
					
				} else {
					
					// Autenticação não permitida: senha não confere.
					Error error =  ErrorFactory.getErrorFromIndex(
							ErrorFactory.ALUNO_NAO_ENCONTRADO);
					
					builder.status(Response.Status.UNAUTHORIZED).entity(error);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				logger.error("SQL: " + exception.getMessage());
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
				
			} catch (UnsupportedEncodingException exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
			
			} catch (NoSuchAlgorithmException e) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));
			}
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.BAD_REQUEST).entity(erro);
		}
		
		return builder.build();		
	}
}