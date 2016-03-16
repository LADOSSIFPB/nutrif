package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.Funcionario;

@Path("funcionario")
public class FuncionarioController {

	/**
	 * Entrada: 
	 * {
	 * 	"nome":"[a-z]",
	 * 	"senha":"[a-z]"
	 *  "email":"[a-z]"
	 * }
	 * 
	 * @param funacionario
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Funcionario funacionario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.funcionario(funacionario);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				// Criptografar senha.
				String senhaCriptografada = StringUtil.criptografarBase64(
						funacionario.getSenha());				
				funacionario.setSenha(senhaCriptografada);
				
				// Gerar AuthKey.
				Date hoje = new Date();
				String keyAuth = StringUtil.criptografarSha256(hoje.toString());
				funacionario.setKeyAuth(keyAuth);
				
				// Ativar Funacionário.
				funacionario.setAtivo(true);
				
				//Inserir o usuário.
				Integer idUsuario = FuncionarioDAO.getInstance().insert(
						funacionario);
				
				if (idUsuario != BancoUtil.IDVAZIO) {

					// Remover a senha.
					funacionario.setSenha(StringUtil.STRING_VAZIO);
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(funacionario);
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
		} else {
			
			Erro erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(Funcionario usuario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.funcionario(usuario);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Login usuário.
				usuario = FuncionarioDAO.getInstance().login(
						usuario.getNome(), usuario.getSenha());
				
				if (usuario != null) {

					// Remover a senha.
					usuario.setSenha(StringUtil.STRING_VAZIO);
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(usuario);
				
				} else {
					
					builder.status(Response.Status.UNAUTHORIZED);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getErro());
				
			} catch (UnsupportedEncodingException 
					exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.IMPOSSIVEL_CRIPTOGRAFAR_VALOR));			
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
	public List<Funcionario> getAll() {
		
		List<Funcionario> usuarios = new ArrayList<Funcionario>();
		
		usuarios = FuncionarioDAO.getInstance().getAll();
		
		return usuarios;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getUsuarioById(@PathParam("id") int idUsuario) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Funcionario usuario = FuncionarioDAO.getInstance().getById(idUsuario); 
			
			builder.status(Response.Status.OK);
			builder.entity(usuario);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getErro());			
		}

		return builder.build();
	}
}
