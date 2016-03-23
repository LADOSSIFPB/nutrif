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
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.ConfirmaPretensaoDia;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Erro;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;

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
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicao.getConfirmaPretensaoDia();
				
				// Verifica dia da refeição.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				
				if (diaRefeicao != null) {
					
					// Atribuindo dia da refeição com dados completos.
					confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);
					
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
					
				} else {
					
					builder.status(Response.Status.NOT_FOUND).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.ID_DIA_REFEICAO_INVALIDO));
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
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.pretensaoRefeicaoKeyAccess(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			pretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
					.getPretensaoRefeicaoByKeyAccess(pretensaoRefeicao
							.getKeyAccess());
			
			if (pretensaoRefeicao != null) {
				
				// Realização da refeição
				
				ConfirmaRefeicaoDia confirmaRefeicaoDia = new ConfirmaRefeicaoDia();
				confirmaRefeicaoDia.setDiaRefeicao(
						pretensaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao());
				RefeicaoRealizada refeicaoRealizada = new RefeicaoRealizada();
				refeicaoRealizada.setConfirmaRefeicaoDia(confirmaRefeicaoDia);
				
				int idRefeicaoRealizada = RefeicaoRealizadaDAO.getInstance()
						.insert(refeicaoRealizada);
				
				if (idRefeicaoRealizada != BancoUtil.IDVAZIO) {
					
					builder.status(Response.Status.OK);
				}				
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.PRETENSAO_REFEICAO_NAO_ENCONTRADA));
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
	public Response getPretencaoRefeicaoById(
			@PathParam("id") int idPretensaoRefeicao) {
		
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
