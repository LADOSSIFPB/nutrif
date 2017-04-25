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
					
					pretensaoRefeicao = verificarPretensao(diaRefeicao);
					
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
	@RolesAllowed({TipoRole.ADMIN, TipoRole.COMENSAL})
	@POST
	@Path("/diarefeicao/verificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response verificarDiaRefeicao(PretensaoRefeicao pretensaoRefeicaoCalculada) {
		
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
									
					pretensaoRefeicaoCalculada = verificarPretensao(diaRefeicao);
					
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
	private PretensaoRefeicao verificarPretensao(DiaRefeicao diaRefeicao) {

		logger.info("Análise da Pretensão de Refeição: " + diaRefeicao);

		PretensaoRefeicao pretensaoRefeicao = null;

		// Refeição.
		Refeicao refeicao = diaRefeicao.getRefeicao();

		// Dia da semana para lançar a Pretensão.
		int diaPretensao = diaRefeicao.getDia().getId();

		// Data Atual
		Date dataHoraSolicitacao = new Date();

		// Data da pretensão.
		Date dataPretensao = DateUtil.getDateOfDayWeek(diaPretensao);

		Date dataHoraPretensao = DateUtil.setTimeInDate(dataPretensao, refeicao.getHoraInicio());

		// Verificações de período de solicitação da pretensão.
		long diferencaMinutos = DateUtil.getMinutesBetweenDate(dataHoraSolicitacao, dataHoraPretensao);
		logger.info("Diferença em minutos: " + diferencaMinutos);
		
		long minutosPrevisaoPretensao = TimeUnit.MILLISECONDS.toMinutes(refeicao.getHoraPrevisaoPretensao().getTime());
		logger.info("Hora da Previsao para a Pretensao em minutos: " + minutosPrevisaoPretensao);
				
		// Verificar se solicitação está sendo lançada dentro do prazo
		if (diferencaMinutos >= minutosPrevisaoPretensao) {

			ConfirmaPretensaoDia confirmaPretensaoDia = new ConfirmaPretensaoDia();

			// Atribuição das datas de pretensão e solicitação.
			confirmaPretensaoDia.setDataPretensao(dataPretensao);
			confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);

			pretensaoRefeicao = new PretensaoRefeicao();
			pretensaoRefeicao.setConfirmaPretensaoDia(confirmaPretensaoDia);
			pretensaoRefeicao.setDataSolicitacao(dataHoraSolicitacao);
		}

		return pretensaoRefeicao;
	}
	
	private PretensaoRefeicao calcularPretensao(DiaRefeicao diaRefeicao) {
		
		// Calcular data para o dia da refeição.
		logger.info("Calcular a Pretensão de Refeição: " + diaRefeicao);
		
		PretensaoRefeicao pretensaoRefeicao = null;
		
		// Dia da semana para lançar a pretensão.
		int diaPretensao = diaRefeicao.getDia().getId();
		Date dataPretensao = DateUtil.getDateOfDayWeek(diaPretensao);
				
		ConfirmaPretensaoDia confirmaPretensaoDia = 
				new ConfirmaPretensaoDia();		
		
		// Atribuição das datas de pretensão e solicitação.
		confirmaPretensaoDia.setDataPretensao(dataPretensao);
		confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);		
		
		// Pretensão
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
	
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/quantificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getQuantidadePretensaoRefeicao(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.quantidadePretensaoRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Recurar dados da refeição.
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						diaRefeicao.getRefeicao().getId());
				diaRefeicao.setRefeicao(refeicao);
				
				// Dia proposto para a pretensão e data da solicitação.
				int idDia = diaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				
				// Verificar pretensão baseado no dia e refeição.				
				PretensaoRefeicao pretensaoRefeicao = calcularPretensao(diaRefeicao);
				
				if (refeicao != null && dia != null) {
					
					// Cálculo da quantidade de pretensões lançadas para o próximo dia de refeição.
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
		
		// Validação dos dados de entrada.
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
											
						// Consulta dos dias das refeições.
						List<PretensaoRefeicao> refeicoesRealizadas = PretensaoRefeicaoDAO
								.getInstance().getMapaPretensaoRefeicao(
										refeicao, data);
						
						// Mapa para resultado com as pretensões das refeições.
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

			// Validação dos dados de entrada.
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
					
					// Pretensão não encontrada.					
					Error erro = ErrorFactory.getErrorFromIndex(
							ErrorFactory.PRETENSAO_REFEICAO_NAO_ENCONTRADA);
					builder.status(Response.Status.NOT_FOUND).entity(erro);
				}
				
			} else {
				
				// Problema na validação.
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