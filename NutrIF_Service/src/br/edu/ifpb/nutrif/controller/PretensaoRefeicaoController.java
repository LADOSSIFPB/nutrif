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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.MapaPretensaoRefeicao;
import br.edu.ladoss.entity.MapaRefeicaoRealizada;
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
		
		logger.info("Inserção da Pretensão para a Refeição: " 
				+ pretensaoRefeicao);
		
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
				logger.info("Dia da Refeição: " + diaRefeicao);
				
				if (diaRefeicao != null) {					
					
					pretensaoRefeicao = verifyPretensao(diaRefeicao);
					
					if (pretensaoRefeicao != null) {
						
						// Chave de acesso ao Refeitório através da pretensão lançada.
						Date agora = new Date();
						pretensaoRefeicao.setKeyAccess(
								StringUtil.criptografarSha256(agora.toString()));
						
						//Inserir a Pretensão.
						Integer idPretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
								.insert(pretensaoRefeicao);
						
						if (idPretensaoRefeicao != BancoUtil.ID_VAZIO) {

							// Operação realizada com sucesso.
							builder.status(Response.Status.OK);
							builder.entity(pretensaoRefeicao);
						}
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
	 * @param pretensaoRefeicaoCalculada
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/diarefeicao/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verifyDiaRefeicao(PretensaoRefeicao pretensaoRefeicaoCalculada) {
		
		logger.info("Verificação da Pretensão para a Refeição: " 
				+ pretensaoRefeicaoCalculada);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicaoCalculada);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicaoCalculada.getConfirmaPretensaoDia();
				
				// Verifica dia da refeição.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				logger.info("Dia da Refeição: " + diaRefeicao);
				
				if (diaRefeicao != null) {
									
					pretensaoRefeicaoCalculada = verifyPretensao(diaRefeicao);
					
					if (pretensaoRefeicaoCalculada != null) {
						
						Date dataPretensao = pretensaoRefeicaoCalculada
								.getConfirmaPretensaoDia()
								.getDataPretensao();
						int idDiaRefeicao = diaRefeicao.getId();
						
						// Recuperar pretensão já lançada para a refeição e data.
						PretensaoRefeicao pretencaoRefeicaoLancada = 
								PretensaoRefeicaoDAO.getInstance()
									.getPretensaoRefeicaoByDiaRefeicao(
											idDiaRefeicao, dataPretensao);
						
						if (pretencaoRefeicaoLancada != null) {
							
							// Pretensão já lançada.
							builder.status(Response.Status.OK).entity(
									pretencaoRefeicaoLancada);
							
						} else {
							
							// Pretensão calculada.
							builder.status(Response.Status.OK).entity(
									pretensaoRefeicaoCalculada);
						}						
						
					} else {
						
						builder.status(Response.Status.NOT_ACCEPTABLE).entity(
								ErrorFactory.getErrorFromIndex(
										ErrorFactory.PRETENSAO_REFEICAO_INVALIDA));
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
	
	/**
	 * Analisar pretensão lançada baseada na data da solicitação e no dia da
	 * refeição.
	 * 
	 * @param diaRefeicao
	 * @return pretensaoRefeicao
	 */
	private PretensaoRefeicao verifyPretensao(DiaRefeicao diaRefeicao) {
		
		logger.info("Analise da Pretensão de Refeição: " + diaRefeicao);
		
		PretensaoRefeicao pretensaoRefeicao = null;
		
		// Extrair da Refeição a hora máxima para solicitação da pretensão.
		Refeicao refeicao = diaRefeicao.getRefeicao();
		
		// Dia da semana para lançar a pretensão.
		int diaPretensao = diaRefeicao.getDia().getId();
		
		//TODO: Ajustar cálculo. Diferença de dias entre solicitação e pretensão.
		int diferenca = DateUtil.getTodayDaysDiff(diaPretensao);
		
		// Quantidade máxima de diferença entre o dia da solicitação e o pretendido.
		if (diferenca == refeicao.getDiaPrevistoPretensao()) {	
			
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
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						new ConfirmaPretensaoDia();
				
				// Atribuição das datas de pretensão e solicitação.
				confirmaPretensaoDia.setDataPretensao(dataPretensao);
				confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);
				
				pretensaoRefeicao = new PretensaoRefeicao();
				pretensaoRefeicao.setConfirmaPretensaoDia(
						confirmaPretensaoDia);
				pretensaoRefeicao.setDataSolicitacao(dataSolicitacao);
			}				
		}
		
		return pretensaoRefeicao;
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
				
				// Dia de refeição
				RefeicaoRealizada refeicaoRealizada = new RefeicaoRealizada();
				refeicaoRealizada.setConfirmaRefeicaoDia(confirmaRefeicaoDia);
				
				// Confirmação da refeição realizada através da chave de acesso.
				int idRefeicaoRealizada = RefeicaoRealizadaDAO.getInstance()
						.insert(refeicaoRealizada);
				
				if (idRefeicaoRealizada != BancoUtil.ID_VAZIO) {
					
					//int status = openDoorPostRequest();
                    //logger.info("Catraca: " + status);
                    
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
	
	public int openDoorPostRequest() {
		
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://192.168.2.110:8080/IFOpenDoors_SERVICE/room/open")
				.request().post(Entity.json("{\"person\":{\"id\":1}, \"room\":{\"id\":1}}"));
		
		return response.getStatus(); 
	}
	
	@PermitAll
	@GET
	@Path("/quantificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getQuantidadeHojePretensaoRefeicoes() {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.VALIDATE_OK; //TODO: Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				Date hoje = new Date();
				Dia dia = DateUtil.getDayOfWeek(hoje);
				
				Long quantidadeDia = RefeicaoRealizadaDAO.getInstance()
						.getQuantidadeDiaRefeicaoRealizada(hoje);
				
				MapaRefeicaoRealizada mapaRefeicaoRealizada = new MapaRefeicaoRealizada();
				mapaRefeicaoRealizada.setQuantidade(
						Integer.valueOf(quantidadeDia.toString()));
				mapaRefeicaoRealizada.setDataInicio(hoje);
				mapaRefeicaoRealizada.setDataFim(hoje);
				mapaRefeicaoRealizada.setDia(dia);			
				
				builder.status(Response.Status.OK).entity(
						mapaRefeicaoRealizada);
			
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
	@Path("/mapa/consultar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getMapaPretensaoRefeicao(
			MapaPretensaoRefeicao mapaPretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.VALIDATE_OK; //TODO: Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				List<PretensaoRefeicao> pretensoesRefeicoes = PretensaoRefeicaoDAO
						.getInstance().getMapaPretensaoRefeicao(
								mapaPretensaoRefeicao);
				
				mapaPretensaoRefeicao.setPretensoesRefeicoes(pretensoesRefeicoes);
				
				builder.status(Response.Status.OK).entity(
						mapaPretensaoRefeicao);
				
			
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
