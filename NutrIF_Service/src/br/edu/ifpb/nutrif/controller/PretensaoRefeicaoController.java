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
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.MapaPretensaoRefeicao;
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
		
		logger.info("Inser��o da Pretens�o para a Refei��o: " 
				+ pretensaoRefeicao);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicao.getConfirmaPretensaoDia();
				
				// Verifica dia da refei��o.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				logger.info("Dia da Refei��o: " + diaRefeicao);
				
				if (diaRefeicao != null) {					
					
					pretensaoRefeicao = verifyPretensao(diaRefeicao);
					
					if (pretensaoRefeicao != null) {
						
						// Chave de acesso ao Refeit�rio atrav�s da pretens�o lan�ada.
						Date agora = new Date();
						pretensaoRefeicao.setKeyAccess(
								StringUtil.criptografarSha256(agora.toString()));
						
						//Inserir a Pretens�o.
						Integer idPretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
								.insert(pretensaoRefeicao);
						
						if (idPretensaoRefeicao != BancoUtil.IDVAZIO) {

							// Opera��o realizada com sucesso.
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
	 * - Data da refei��o baseado no dia.
	 * - Analisar a diferen�a de tempo entre a data da solicita��o e data da 
	 * refei��o menor ou igual a 24hs ou 48hs.
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
		
		logger.info("Verifica��o da Pretens�o para a Refei��o: " 
				+ pretensaoRefeicaoCalculada);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicaoCalculada);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicaoCalculada.getConfirmaPretensaoDia();
				
				// Verifica dia da refei��o.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				logger.info("Dia da Refei��o: " + diaRefeicao);
				
				if (diaRefeicao != null) {
									
					pretensaoRefeicaoCalculada = verifyPretensao(diaRefeicao);
					
					if (pretensaoRefeicaoCalculada != null) {
						
						Date dataPretensao = pretensaoRefeicaoCalculada
								.getConfirmaPretensaoDia()
								.getDataPretensao();
						int idDiaRefeicao = diaRefeicao.getId();
						
						// Recuperar pretens�o j� lan�ada para a refei��o e data.
						PretensaoRefeicao pretencaoRefeicaoLancada = 
								PretensaoRefeicaoDAO.getInstance()
									.getPretensaoRefeicaoByDiaRefeicao(
											idDiaRefeicao, dataPretensao);
						
						if (pretencaoRefeicaoLancada != null) {
							
							// Pretens�o j� lan�ada.
							builder.status(Response.Status.OK).entity(
									pretencaoRefeicaoLancada);
							
						} else {
							
							// Pretens�o calculada.
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
	 * Analisar pretens�o lan�ada baseada na data da solicita��o e no dia da
	 * refei��o.
	 * 
	 * @param diaRefeicao
	 * @return pretensaoRefeicao
	 */
	private PretensaoRefeicao verifyPretensao(DiaRefeicao diaRefeicao) {
		
		logger.info("Analise da Pretens�o de Refei��o: " + diaRefeicao);
		
		PretensaoRefeicao pretensaoRefeicao = null;
		
		// Extrair da Refei��o a hora m�xima para solicita��o da pretens�o.
		Refeicao refeicao = diaRefeicao.getRefeicao();
		
		// Dia da semana para lan�ar a pretens�o.
		int diaPretensao = diaRefeicao.getDia().getId();
		
		//TODO: Ajustar c�lculo. Diferen�a de dias entre solicita��o e pretens�o.
		int diferenca = DateUtil.getTodayDaysDiff(diaPretensao);
		
		// Quantidade m�xima de diferen�a entre o dia da solicita��o e o pretendido.
		if (diferenca == refeicao.getDiaPrevistoPretensao()) {	
			
			// Data Atual
			Date dataSolicitacao = new Date();
			
			// Data da pretens�o.
			Date dataPretensao = DateUtil.addDays(dataSolicitacao, 
					diferenca);
			dataPretensao = DateUtil.setTimeInDate(
					dataPretensao, refeicao.getHoraPretensao());
			
			// Verifica��es de per�odo de solicita��o da pretens�o.					
			int diferencaMinutos = DateUtil.getMinutesBetweenDate(
					dataSolicitacao, 
					dataPretensao);
			
			logger.info("Diferen�a em minutos: " + diferencaMinutos);
			
			// Verificar se solicita��o est� sendo lan�ada dentro do prazo					
			if (diferencaMinutos <= DateUtil.UM_DIA_MINUTOS) {
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						new ConfirmaPretensaoDia();
				
				// Atribui��o das datas de pretens�o e solicita��o.
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
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.pretensaoRefeicaoKeyAccess(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			pretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
					.getPretensaoRefeicaoByKeyAccess(pretensaoRefeicao
							.getKeyAccess());
			
			if (pretensaoRefeicao != null) {
				
				// Realiza��o da refei��o				
				ConfirmaRefeicaoDia confirmaRefeicaoDia = new ConfirmaRefeicaoDia();
				confirmaRefeicaoDia.setDiaRefeicao(
						pretensaoRefeicao.getConfirmaPretensaoDia().getDiaRefeicao());
				
				// Dia de refei��o
				RefeicaoRealizada refeicaoRealizada = new RefeicaoRealizada();
				refeicaoRealizada.setConfirmaRefeicaoDia(confirmaRefeicaoDia);
				
				// Confirma��o da refei��o realizada atrav�s da chave de acesso.
				int idRefeicaoRealizada = RefeicaoRealizadaDAO.getInstance()
						.insert(refeicaoRealizada);
				
				if (idRefeicaoRealizada != BancoUtil.IDVAZIO) {
					
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
	@POST
	@Path("/mapa/consultar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getMapaPretensaoRefeicao(
			MapaPretensaoRefeicao mapaPretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
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
