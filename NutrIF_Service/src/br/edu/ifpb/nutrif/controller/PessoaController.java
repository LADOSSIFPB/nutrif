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

import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.FuncionarioAcesso;

@Path("pessoa")
public class PessoaController {
	
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(FuncionarioAcesso funcionarioAcesso) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.acessoFuncionario(funcionarioAcesso);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				//Login usuário.
				Funcionario funcionario = FuncionarioDAO.getInstance().login(
						funcionarioAcesso.getEmail(), 
						funcionarioAcesso.getSenha());
				
				if (funcionario != null) {

					funcionarioAcesso = FuncionarioAcesso.getInstance(
							funcionario);
					
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
					builder.entity(funcionarioAcesso);
				
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
