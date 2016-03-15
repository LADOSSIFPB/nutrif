package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import br.edu.ifpb.nutrif.dao.CursoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Erro;

@Path("aluno")
public class AlunoController {

	/**
	 * {
    "nome":"Maria da Conceição Ferreira",
    "email":"maria.conceicao@gmail.com",
    "senha":"12345",
    "matricula":"20151234567",
    "curso":{"id":1}
}
	 * @param aluno
	 * @return
	 */
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.aluno(aluno);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Criptografar senha.
				String senhaCriptografada = StringUtil.criptografarBase64(
						aluno.getSenha());				
				aluno.setSenha(senhaCriptografada);
				
				// Gerar AuthKey.
				Date hoje = new Date();
				String key = StringUtil.criptografarSha256(hoje.toString());
				aluno.setKey(key);
				
				// Recuperar Curso.
				int idCurso = aluno.getCurso().getId();
				Curso curso = CursoDAO.getInstance().getById(idCurso);
				aluno.setCurso(curso);
				
				// Inativar Aluno.
				aluno.setAtivo(false);
				
				//Inserir o Aluno.
				Integer idAluno = AlunoDAO.getInstance().insert(aluno);
				
				if (idAluno != BancoUtil.IDVAZIO) {

					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(aluno);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getErro());
				
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
					exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
			}
		}				
		
		return builder.build();		
	}
	
	@POST
	@Path("/atualizar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Aluno aluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.aluno(aluno);
		
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
			
			} catch (SQLExceptionNutrIF qme) {
				
				Erro erro = new Erro();
				erro.setCodigo(qme.getErrorCode());
				erro.setMensagem(qme.getMessage());

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);			
			}
		}				
		
		return builder.build();		
	}
	
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Aluno> getAll() {
		
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		alunos = AlunoDAO.getInstance().getAll();
		
		return alunos;
	}
	
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getAlunoById(@PathParam("id") int idAluno) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Aluno aluno = AlunoDAO.getInstance().getById(idAluno); 
			
			builder.status(Response.Status.OK);
			builder.entity(aluno);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getErro());
		}

		return builder.build();
	}
	
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
				
				builder.status(Response.Status.OK);
				builder.entity(aluno);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND);
			}
			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getErro());
		}

		return builder.build();
	}
}
