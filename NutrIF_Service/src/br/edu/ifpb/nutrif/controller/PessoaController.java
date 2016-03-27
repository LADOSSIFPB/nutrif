package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.PessoaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Pessoa;
import br.edu.ladoss.entity.PessoaAcesso;

@Path("pessoa")
public class PessoaController {
	
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
	public Response login(PessoaAcesso pessoaAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.acessoPessoa(pessoaAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Login Pessoa.
				Pessoa pessoa = PessoaDAO.getInstance().login(
						pessoaAcesso.getEmail(), 
						pessoaAcesso.getSenha());
				
				if (pessoa != null) {

					pessoaAcesso = PessoaAcesso.getInstance(
							pessoa);
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(pessoaAcesso);
				
				} else {
					
					builder.status(Response.Status.UNAUTHORIZED);
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
				
			} catch (UnsupportedEncodingException 
					exception) {

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
}
