package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.RoleDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.AlunoAcesso;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.enumeration.TipoRole;

@Path("aluno")
public class AlunoController {

	private static Logger logger = LogManager.getLogger(AlunoController.class);

	/**
	 * Inserir Aluno na base de dados.
	 * 
	 * Entrada: { "nome":"[a-z]", "matricula":"[1-9]", "curso":{"id":[1-9]} }
	 * 
	 * @param aluno
	 * @return
	 */
	//TODO: @RolesAllowed({ TipoRole.ADMIN })
	@PermitAll
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Aluno aluno) {

		// Data e hora atual.
		Date agora = new Date();
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(agora);

		// Validação dos dados de entrada.
		int validacao = Validate.inserirAluno(aluno);

		if (validacao == Validate.VALIDATE_OK) {

			try {

				// Nome do aluno somente com a primeira letra de cada palavra em maiúsculo.
				String nome = aluno.getNome();
				aluno.setNome(StringUtil.upperCaseFirstChar(nome));

				// Role de acesso.
				Role role = RoleDAO.getInstance().getById(Role.Tipo.COMENSAL.getId());
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				aluno.setRoles(roles);
				
				// Campus
				int idCampus = aluno.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				aluno.setCampus(campus);

				// Inativar o Aluno.
				aluno.setAtivo(BancoUtil.INATIVO);

				// Data Inserção
				aluno.setDataInsercao(agora);
				
				// Inserir o Aluno.
				Integer idAluno = AlunoDAO.getInstance().insert(aluno);

				if (idAluno != BancoUtil.ID_VAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(aluno);

				} else {

					builder.status(Response.Status.NOT_MODIFIED);
				}

			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}

	/**
	 * Atualizar dados básicos do Aluno: nome, matrícula, campus, curso, período,
	 * turma e turno.
	 * 
	 * @param aluno
	 * @return
	 */
	@RolesAllowed({ TipoRole.ADMIN })
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Aluno aluno) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Validação dos dados de entrada.
		int validacao = Validate.inserirAluno(aluno);

		if (validacao == Validate.VALIDATE_OK) {

			try {

				// Nome do aluno somente com a primeira letra de cada palavra em maiúsculo.
				String nome = aluno.getNome();
				aluno.setNome(StringUtil.upperCaseFirstChar(nome));

				// Role de acesso.
				Role role = RoleDAO.getInstance().getById(Role.Tipo.COMENSAL.getId());
				List<Role> roles = new ArrayList<Role>();
				roles.add(role);
				aluno.setRoles(roles);
				
				// Campus
				int idCampus = aluno.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				aluno.setCampus(campus);

				// Atualizar o Aluno.
				aluno = AlunoDAO.getInstance().update(aluno);

				if (aluno != null) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(aluno);
				}

			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(ErrorFactory.getErrorFromIndex(validacao));
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
	@Path("/{id}")
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

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}

	/**
	 * Listar Aluno pesquisando pela matrícula.
	 * 
	 * @param matricula
	 * @return
	 */
	//TODO: @RolesAllowed({TipoRole.ADMIN})
	@PermitAll
	@GET
	@Path("/matricula/{matricula}")
	@Produces("application/json")
	public Response getByMatricula(@PathParam("matricula") String matricula) {

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

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}

	/**
	 * Listar Aluno pesquisando pelo Nome.
	 * 
	 * @param nome
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/nome/{nome}")
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

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}

	/**
	 * Inserir acesso do Aluno ao sistema via dispositivo móvel. 
	 * 
	 * Entrada: { "matricula":"[1-9]", "email":"[a-z]", "senha":"[a-z]" }
	 * 
	 * @param aluno
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/acesso")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertAcesso(AlunoAcesso alunoAcesso) {

		ResponseBuilder builder = Response.status(Response.Status.NOT_IMPLEMENTED);
		builder.expires(new Date());

		return builder.build();
	}
	
	/**
	 * Atualizar dados de acesso do Aluno: e-mail e senha.
	 * 
	 * @param aluno
	 * @return
	 */
	@RolesAllowed({ TipoRole.ADMIN })
	@PUT
	@Path("/acesso")
	@Consumes("application/json")
	@Produces("application/json")
	public Response atualizarAcesso(Aluno aluno) {

		ResponseBuilder builder = Response.status(Response.Status.NOT_IMPLEMENTED);
		builder.expires(new Date());

		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/acesso/matricula/{matricula}")
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

					Error error = ErrorFactory.getErrorFromIndex(ErrorFactory.ALUNO_NAO_ENCONTRADO);

					builder.status(Response.Status.NOT_FOUND).entity(error);
				}

			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}

	/**
	 * Confirma chave de acesso do Aluno.
	 * 
	 * Entrada: { "keyConfirmation":"[a-zA-Z]*", "matricula":"[0-9]*" }
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
				
				// TODO: Ajustar para e-mail ou cpf.
				String matricula = null;
				String keyConfirmation = aluno.getKeyConfirmation();

				// Consultar chave de confirmação.
				boolean isKeyConfirmation = AlunoDAO.getInstance().isKeyConfirmation(matricula, keyConfirmation);

				// Ativar aluno.
				if (isKeyConfirmation) {

					aluno = AlunoDAO.getInstance().getByMatricula(matricula);
					aluno.setAtivo(true);

					aluno = AlunoDAO.getInstance().update(aluno);
					builder.status(Response.Status.OK);

				} else {

					// Chave de autorização inválida.
					builder.status(Response.Status.UNAUTHORIZED)
							.entity(ErrorFactory.getErrorFromIndex(ErrorFactory.CHAVE_CONFIRMACAO_INVALIDA));
				}

			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}

	@PermitAll
	@POST
	@Path("/email/{email}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response sendEmail(@PathParam("email") String email) {

		ResponseBuilder builder = Response.status(Response.Status.NOT_IMPLEMENTED);
		builder.expires(new Date());

		return builder.build();
	}
}