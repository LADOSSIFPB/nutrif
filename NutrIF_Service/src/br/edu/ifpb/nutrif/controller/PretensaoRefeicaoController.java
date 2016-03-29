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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Period;

import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.PretensaoRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.ConfirmaPretensaoDia;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;

@Path("pretensaorefeicao")
public class PretensaoRefeicaoController {

	private static Logger logger = LogManager.getLogger(
			PretensaoRefeicaoController.class);
	
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
					
					//Inserir a Pretensão.
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
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();		
	}
	
	/**
	 * - Data da refeição baseado no dia.
	 * - Analisar a diferença de tempo entre a data da solicitação e data da 
	 * refeição menor ou igual a 24hs ou 48hs.
	 * 
	 * @param pretensaoRefeicao
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/diarefeicao/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verifyDiaRefeicao(PretensaoRefeicao pretensaoRefeicao) {
		
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
									
					// Extrair da Refeição a hora máxima para solicitação da pretensão.
					Refeicao refeicao = diaRefeicao.getRefeicao();
					
					// Dia da semana para lançar a pretensão.
					int diaPretensao = diaRefeicao.getDia().getId();
					
					// Dia da semana que da solicitação para a pretensão.
					int diaSolicitacao = DateUtil.getCurrentDayOfWeek().getId();
					
					// Diferença de dias entre solicitação e pretensão.
					int diferenca = diaPretensao - diaSolicitacao;
					
					if (diaPretensao == diaSolicitacao) {
						
						diferenca = 7;						
					}
					
					// Data Atual
					Date dataSolicitacao = new Date();
					
					// Data da pretensão.
					Date dataPretensao = DateUtil.addDays(dataSolicitacao, 
							diferenca);
					dataPretensao = DateUtil.setTimeInDate(
							dataPretensao, refeicao.getHoraPretensao());
					
					// Verificações de período de solicitação da pretensão.					
					int diferencaMinutos = DateUtil.getMinutesBetweenDate(
							dataSolicitacao, 
							dataPretensao);
					
					logger.info("Diferença em minutos: " + diferencaMinutos);
					
					// Verificar se solicitação está sendo lançada dentro do prazo					
					if (diferencaMinutos <= DateUtil.UM_DIA_MINUTOS) {
						
						// Atribuição das datas de pretensão e solicitação.
						confirmaPretensaoDia.setDataPretensao(dataPretensao);
						pretensaoRefeicao.setConfirmaPretensaoDia(
								confirmaPretensaoDia);
						pretensaoRefeicao.setDataSolicitacao(dataSolicitacao);
						
						builder.status(Response.Status.OK).entity(
								pretensaoRefeicao);
					}					
					
				} else {
					
					builder.status(Response.Status.NOT_FOUND).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.ID_DIA_REFEICAO_INVALIDO));
				}
			
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());			
			}
			
		} else {
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
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
				
				// Confirmação da refeição realizada através da chave de acesso.
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
			
			Error erro = ErrorFactory.getErrorFromIndex(validacao);
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
		}
		
		return builder.build();
	}
	
	@DenyAll	
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
