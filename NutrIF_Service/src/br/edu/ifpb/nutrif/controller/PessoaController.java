package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.LoginDAO;
import br.edu.ifpb.nutrif.dao.LogoutDAO;
import br.edu.ifpb.nutrif.dao.PessoaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Login;
import br.edu.ladoss.entity.Logout;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.entity.PessoaAcesso;

@Path("pessoa")
public class PessoaController {
	
	private static Logger logger = LogManager.getLogger(PessoaController.class);
	
	/**
	 * Login para Pessoa. Retorna a chave de autenticação caso o usuário esteja
	 * com o e-mail e senha corretos.
	 * 
	 * @param pessoaAcesso
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response loginPessoa(@HeaderParam("user-agent") String userAgent,
			@Context HttpServletRequest request,
			PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		logger.info("Login usuário: " + pessoaAcesso.getNome());
		logger.info("Host: " + request.getRemoteAddr());
		
		// Validação dos dados de entrada.
		int validacao = Validate.loginPessoa(pessoaAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Login Pessoa.
				Pessoa pessoa = PessoaDAO.getInstance().login(
						pessoaAcesso.getEmail(), 
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
						
						// Chave de autenticação gerada.
						pessoaAcesso.setKeyAuth(login.getKeyAuth());
						
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(pessoaAcesso);
					}					
				
				} else {
					
					builder.status(Response.Status.UNAUTHORIZED).entity(
							ErrorFactory.getErrorFromIndex(ErrorFactory.ACESSO_USUARIO_NAO_PERMITIDO));
					
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
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	@PermitAll
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(@HeaderParam("authorization") String authorization) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		logger.info("Logout usuário");
		logger.info("Authorization: " + authorization);
		
		// Validação dos dados de entrada.
		int validacao = Validate.logoutPessoa(authorization);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Login
				Login login = LoginDAO.getInstance()
						.getLoginByKeyAuth(authorization);
				
				if (login != null) {
					
					// Invalidando Login
					login.setLoged(BancoUtil.INATIVO);
					
					// Registro do Login
					Date agora = new Date();
					
					Logout logout = new Logout();
					logout.setLogin(login);
					logout.setRegistro(agora);
					
					int idLogout = LogoutDAO.getInstance().insert(logout);
					
					if (idLogout != BancoUtil.ID_VAZIO) {
						
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
					}					
				
				} else {
					
					builder.status(Response.Status.NOT_FOUND);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				logger.error("SQL: " + exception.getMessage());
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
	 * Gerar e-mail com url para reinicialização da senha para aluno e funcionário.
	 * 
	 * @param pessoaAcesso
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/login/request")
	@Consumes("application/json")
	@Produces("application/json")
	public Response requestResetSenha(PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		logger.info("Reset senha usuário");
		
		//TODO: Validação dos dados de entrada.
		int validacao = Validate.VALIDATE_OK;
		
		if (validacao == Validate.VALIDATE_OK) {
			
			//TODO: Mudar senha do usuário.
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	/**
	 * Reiniciar senha para aluno e funcionário.
	 * 
	 * @param pessoaAcesso
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/login/reset")
	@Consumes("application/json")
	@Produces("application/json")
	public Response resetSenha(PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		logger.info("Reset senha usuário");
		
		//TODO: Validação dos dados de entrada.
		int validacao = Validate.VALIDATE_OK;
		
		if (validacao == Validate.VALIDATE_OK) {
			
			//TODO: Mudar senha do usuário.
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
}
