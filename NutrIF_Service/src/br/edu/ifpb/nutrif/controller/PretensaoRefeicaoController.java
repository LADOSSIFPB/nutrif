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

import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.PretensaoRefeicaoDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.PretensaoRefeicao;

@Path("pretensaorefeicao")
public class PretensaoRefeicaoController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(PretensaoRefeicao pretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.pretencaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Verifica dia da refeição.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance().find(
						pretensaoRefeicao.getDiaRefeicao());
				
				if (diaRefeicao != null) {
					
					// Atribuindo dia da refeição com dados completos.
					pretensaoRefeicao.setDiaRefeicao(diaRefeicao);
					
					// Chave de acesso ao Refeitório através da pretensão lançada.
					Date agora = new Date();
					pretensaoRefeicao.setKeyAccess(
							StringUtil.criptografarSha256(agora.toString()));
					
					//Inserir o Aluno.
					Integer idPretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
							.insert(pretensaoRefeicao);
					
					if (idPretensaoRefeicao != BancoUtil.IDVAZIO) {

						// Operação realizada com sucesso.
						builder.status(Response.Status.OK);
						builder.entity(pretensaoRefeicao);
					}
				}				
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			
			}  catch (UnsupportedEncodingException | NoSuchAlgorithmException 
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
	@Path("/chaveacesso/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verifyChaveAcesso(PretensaoRefeicao pretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.OK);
		builder.expires(new Date());
		
		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<PretensaoRefeicao> getAll() {
		
		List<PretensaoRefeicao> pretensaoRefeicao = 
				new ArrayList<PretensaoRefeicao>();
		
		pretensaoRefeicao = PretensaoRefeicaoDAO.getInstance().getAll();
		
		return pretensaoRefeicao;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getPretencaoRefeicaoById(@PathParam("id") int idPretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			PretensaoRefeicao pretensaoRefeicao = PretensaoRefeicaoDAO
					.getInstance().getById(idPretensaoRefeicao); 
			
			builder.status(Response.Status.OK);
			builder.entity(pretensaoRefeicao);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
}
