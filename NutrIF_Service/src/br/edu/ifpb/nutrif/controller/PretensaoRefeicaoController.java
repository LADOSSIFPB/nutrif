package br.edu.ifpb.nutrif.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.PretensaoRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
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
import br.edu.ladoss.entity.MapaRefeicao;
import br.edu.ladoss.entity.PeriodoPretensaoRefeicao;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.enumeration.TipoRole;

@Path("pretensaorefeicao")
public class PretensaoRefeicaoController {

	private static Logger logger = LogManager.getLogger(
			PretensaoRefeicaoController.class);
	
	@RolesAllowed({TipoRole.ADMIN, TipoRole.COMENSAL})
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
					
					pretensaoRefeicao = verificarPretensao(diaRefeicao);
					
					if (pretensaoRefeicao != null) {
						
						// Chave de acesso ao Refeit�rio atrav�s da pretens�o lan�ada.
						Date agora = new Date();
						pretensaoRefeicao.setKeyAccess(
								StringUtil.criptografarSha256(agora.toString()));
						
						//Inserir a Pretens�o.
						Integer idPretensaoRefeicao = PretensaoRefeicaoDAO.getInstance()
								.insert(pretensaoRefeicao);
						
						if (idPretensaoRefeicao != BancoUtil.ID_VAZIO) {

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
	@RolesAllowed({TipoRole.ADMIN, TipoRole.COMENSAL})
	@POST
	@Path("/diarefeicao/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verificarDiaRefeicao(PretensaoRefeicao pretensaoRefeicaoCalculada) {
		
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
									
					pretensaoRefeicaoCalculada = verificarPretensao(diaRefeicao);
					
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
	private PretensaoRefeicao verificarPretensao(DiaRefeicao diaRefeicao) {

		logger.info("An�lise da Pretens�o de Refei��o: " + diaRefeicao);

		PretensaoRefeicao pretensaoRefeicao = null;

		// Refei��o.
		Refeicao refeicao = diaRefeicao.getRefeicao();

		// Dia da semana para lan�ar a Pretens�o.
		int diaPretensao = diaRefeicao.getDia().getId();

		// Data Atual
		Date dataHoraSolicitacao = new Date();

		// Data da pretens�o.
		Date dataPretensao = DateUtil.getDateOfDayWeek(diaPretensao);

		Date dataHoraPretensao = DateUtil.setTimeInDate(dataPretensao, refeicao.getHoraInicio());

		// Verifica��es de per�odo de solicita��o da pretens�o.
		long diferencaMinutos = DateUtil.getMinutesBetweenDate(dataHoraSolicitacao, dataHoraPretensao);
		logger.info("Diferen�a em minutos: " + diferencaMinutos);
		
		long minutosPrevisaoPretensao = TimeUnit.MILLISECONDS.toMinutes(refeicao.getHoraPrevisaoPretensao().getTime());
		logger.info("Hora da Previsao para a Pretensao em minutos: " + minutosPrevisaoPretensao);
				
		// Verificar se solicita��o est� sendo lan�ada dentro do prazo
		if (diferencaMinutos >= minutosPrevisaoPretensao) {

			ConfirmaPretensaoDia confirmaPretensaoDia = new ConfirmaPretensaoDia();

			// Atribui��o das datas de pretens�o e solicita��o.
			confirmaPretensaoDia.setDataPretensao(dataPretensao);
			confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);

			pretensaoRefeicao = new PretensaoRefeicao();
			pretensaoRefeicao.setConfirmaPretensaoDia(confirmaPretensaoDia);
			pretensaoRefeicao.setDataSolicitacao(dataHoraSolicitacao);
		}

		return pretensaoRefeicao;
	}
	
	private PretensaoRefeicao calcularPretensao(DiaRefeicao diaRefeicao) {
		
		// Calcular data para o dia da refei��o.
		logger.info("Calcular a Pretens�o de Refei��o: " + diaRefeicao);
		
		PretensaoRefeicao pretensaoRefeicao = null;
		
		// Dia da semana para lan�ar a pretens�o.
		int diaPretensao = diaRefeicao.getDia().getId();
		Date dataPretensao = DateUtil.getDateOfDayWeek(diaPretensao);
				
		ConfirmaPretensaoDia confirmaPretensaoDia = 
				new ConfirmaPretensaoDia();		
		
		// Atribui��o das datas de pretens�o e solicita��o.
		confirmaPretensaoDia.setDataPretensao(dataPretensao);
		confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);		
		
		// Pretens�o
		pretensaoRefeicao = new PretensaoRefeicao();
		pretensaoRefeicao.setConfirmaPretensaoDia(
				confirmaPretensaoDia);		
		
		return pretensaoRefeicao;		
	}
	
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@POST
	@Path("/verificar/chaveacesso")
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
	
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/quantificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getQuantidadePretensaoRefeicao(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.quantidadePretensaoRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Recurar dados da refei��o.
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						diaRefeicao.getRefeicao().getId());
				diaRefeicao.setRefeicao(refeicao);
				
				// Dia proposto para a pretens�o e data da solicita��o.
				int idDia = diaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				
				// Verificar pretens�o baseado no dia e refei��o.				
				PretensaoRefeicao pretensaoRefeicao = calcularPretensao(diaRefeicao);
				
				if (refeicao != null && dia != null) {
					
					// C�lculo da quantidade de pretens�es lan�adas para o pr�ximo dia de refei��o.
					Long quantidadeDia = PretensaoRefeicaoDAO.getInstance()
							.getQuantidadeDiaPretensaoRefeicao(pretensaoRefeicao);
					
					Date dataSolicitacaoPretensao = pretensaoRefeicao
							.getConfirmaPretensaoDia().getDataPretensao();
					
					// Mapa com os dados quantificados.
					MapaPretensaoRefeicao mapaPretensaoRefeicao = new MapaPretensaoRefeicao();
					mapaPretensaoRefeicao.setQuantidade(
							Integer.valueOf(quantidadeDia.toString()));
					mapaPretensaoRefeicao.setData(dataSolicitacaoPretensao);
					mapaPretensaoRefeicao.setDia(dia);
					
					builder.status(Response.Status.OK).entity(
							mapaPretensaoRefeicao);
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
	
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/consultar/mapa")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getMapaPretensaoRefeicao(
			PeriodoPretensaoRefeicao periodoPretensaoRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.periodoPretensaoRefeicao(periodoPretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				List<MapaRefeicao> mapasPretensoesRefeicoes = 
						new ArrayList<MapaRefeicao>();
				
				// Data entre o intervalo de dataInicio e dataFim.
				List<Date> datas = DateUtil.getDaysBetweenDates(
						periodoPretensaoRefeicao.getDataInicio(), 
						periodoPretensaoRefeicao.getDataFim());
				
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						periodoPretensaoRefeicao.getRefeicao().getId());				
				
				if (refeicao != null) {					
				
					for (Date data: datas) {					
											
						// Consulta dos dias das refei��es.
						List<PretensaoRefeicao> refeicoesRealizadas = PretensaoRefeicaoDAO
								.getInstance().getMapaPretensaoRefeicao(
										refeicao, data);
						
						// Mapa para resultado com as pretens�es das refei��es.
						MapaRefeicao<PretensaoRefeicao> mapaPretensaoRefeicao = 
								new MapaRefeicao<PretensaoRefeicao>();				
						mapaPretensaoRefeicao.setRefeicao(refeicao);
						mapaPretensaoRefeicao.setData(data);
						mapaPretensaoRefeicao.setLista(refeicoesRealizadas);
						mapaPretensaoRefeicao.setQuantidade(
								refeicoesRealizadas.size());
						
						mapasPretensoesRefeicoes.add(mapaPretensaoRefeicao);
					}
					
					builder.status(Response.Status.OK).entity(
							mapasPretensoesRefeicoes);
					
				} else {
					
					builder.status(Response.Status.NOT_FOUND).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.ID_REFEICAO_INVALIDA));					
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
	
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/vigente/diarefeicao/id/{id}")
	@Produces("application/json")
	public Response getPretencaoVigenteByDiaRefeicao(
			@PathParam("id") int idDiaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			// Valida��o dos dados de entrada.
			int validacao = Validate.diaRefeicao(idDiaRefeicao);
			
			if (validacao == Validate.VALIDATE_OK) {
				
				// Data e hora atual.
				Date agora = new Date();
				
				PretensaoRefeicao pretensaoRefeicao = PretensaoRefeicaoDAO
						.getInstance().getPretensaoRefeicaoByDiaRefeicao(
								idDiaRefeicao, agora); 
				
				if (pretensaoRefeicao != null) {
					
					builder.status(Response.Status.OK);
					builder.entity(pretensaoRefeicao);
					
				} else {
					
					// Pretens�o n�o encontrada.					
					Error erro = ErrorFactory.getErrorFromIndex(
							ErrorFactory.PRETENSAO_REFEICAO_NAO_ENCONTRADA);
					builder.status(Response.Status.NOT_FOUND).entity(erro);
				}
				
			} else {
				
				// Problema na valida��o.
				Error erro = ErrorFactory.getErrorFromIndex(validacao);
				builder.status(Response.Status.NOT_ACCEPTABLE).entity(erro);
			}

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
}