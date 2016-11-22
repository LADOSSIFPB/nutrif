package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
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
import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.dao.RoleDAO;
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
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.enumeration.TipoRole;

@Path("aluno")
public class AlunoController {

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
	@PermitAll
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
				
				// Recuperar Curso.
				int idCurso = aluno.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				aluno.setCurso(curso);
				
				// Role 
				Role role = RoleDAO.getInstance().getById(
						Role.Tipo.COMENSAL.getId());
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);				
				aluno.setRoles(roles);
				
				// Campus
				int idCampus = aluno.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				aluno.setCampus(campus);
				
				// Inativar o Aluno.
				aluno.setAtivo(BancoUtil.INATIVO);
				
				if (curso != null
						&& campus != null
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
	@PermitAll
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
	@Path("/acesso/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertAcesso(AlunoAcesso alunoAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.acessoAluno(alunoAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				String senhaPlana = alunoAcesso.getSenha();
				String email = alunoAcesso.getEmail();
				String matricula = alunoAcesso.getMatricula();
				
				// Recuperar Aluno através da matrícula.
				Aluno aluno = AlunoDAO.getInstance().getByMatricula(matricula);
				
				if (aluno != null 
						&& aluno.getId() != BancoUtil.ID_VAZIO) {

					// Verificar se o usuário já tem acesso solicitado.					
					if (!aluno.isAcesso()) {
						
						// E-mail do Aluno.
						aluno.setEmail(email);
						
					} else {
						
						email = aluno.getEmail();
					}
					
					// Criptografar senha.
					String senhaCriptografada = StringUtil.criptografarBase64(
							senhaPlana);				
					aluno.setSenha(senhaCriptografada);
					
					// Gerar chave de autenticação: KeyAuth.
					Date agora = new Date();
					String keyAuth = StringUtil.criptografarSha256(
							agora.toString());
					aluno.setKeyAuth(keyAuth);
					
					// Gerar chave de confirmação: KeyConfirmation.
					String keyConfirmation = StringUtil.getRadomKeyConfirmation();
					aluno.setKeyConfirmation(keyConfirmation);
					
					// Inativar Aluno.
					aluno.setAtivo(BancoUtil.INATIVO);
					
					// Ativar solicitação de acesso.
					aluno.setAcesso(BancoUtil.ATIVO);
					
					//Inserir o Aluno.
					aluno = AlunoDAO.getInstance().update(aluno);
					
					if (aluno.getId() != BancoUtil.ID_VAZIO) {

						// Enviar e-mail com a chave de acesso.
						EmailUtil emailUtil = new EmailUtil();
						emailUtil.sendChaveConfirmacaoAluno(
								email, keyConfirmation, aluno.getNome());
						
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
				
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
					exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
			}
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
	
	@PermitAll	
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
}
