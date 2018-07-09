package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.ExtratoRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.PretensaoRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.ConfirmaRefeicaoDia;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.ExtratoRefeicao;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.MapaRefeicao;
import br.edu.ladoss.entity.PeriodoRefeicaoRealizada;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.enumeration.TipoRole;

@Path("refeicaorealizada")
public class RefeicaoRealizadaController {

	private static Logger logger = LogManager.getLogger(RefeicaoRealizadaController.class);
	
	/**
	 * 
	 * @param refeicaoRealizada
	 * @return
	 */	
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(RefeicaoRealizada refeicaoRealizada) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.refeicaoRealizada(refeicaoRealizada);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar o Dia da Refeição.
				int idDiaRefeicao = refeicaoRealizada.getConfirmaRefeicaoDia()
						.getDiaRefeicao().getId();
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO
						.getInstance().getById(idDiaRefeicao);
				
				if (diaRefeicao != null) {
					
					// Data e hora atual.
					Date agora = new Date();
					
					// Pretensão
					PretensaoRefeicao pretensaoRefeicao = PretensaoRefeicaoDAO
							.getInstance().getPretensaoRefeicaoByDiaRefeicao(
									idDiaRefeicao, agora);
					
					// Análise para lançamento obrigatório da prentesão.
					if ((!diaRefeicao.getEdital().isPrevistoPretensao() 
							&& pretensaoRefeicao == null) ||
							(!diaRefeicao.getEdital().isPrevistoPretensao() 
									&& pretensaoRefeicao != null) ||
							(diaRefeicao.getEdital().isPrevistoPretensao() 
								&& pretensaoRefeicao != null)) {
						
						// Confirmação da refeição
						ConfirmaRefeicaoDia confirmaRefeicaoDia = 
								new ConfirmaRefeicaoDia();
						confirmaRefeicaoDia.setDiaRefeicao(diaRefeicao);
						confirmaRefeicaoDia.setDataRefeicao(agora);
						refeicaoRealizada.setConfirmaRefeicaoDia(
								confirmaRefeicaoDia);
						
						// Hora da refeição
						refeicaoRealizada.setHoraRefeicao(agora);
						
						// Funcionário responsável registro da refeição realizada.
						Funcionario inspetor = FuncionarioDAO.getInstance().getById(
								refeicaoRealizada.getInspetor().getId());
						refeicaoRealizada.setInspetor(inspetor);
						
						//Inserir a Refeição Realizada.
						boolean success = RefeicaoRealizadaDAO.getInstance()
								.insertOrUpdate(refeicaoRealizada);					
						
						if (success) {
							
							// Operação realizada com sucesso.
							builder.status(Response.Status.OK);
						}
					
					} else {
						
						// Mensagem de pretensão obrigatória.
						builder.status(Response.Status.NOT_ACCEPTABLE).entity(
								ErrorFactory.getErrorFromIndex(
										ErrorFactory.PRETENSAO_REFEICAO_NAO_ENCONTRADA));
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
	
	@DenyAll
	@GET
	@Produces("application/json")
	public List<RefeicaoRealizada> getAll() {
		
		List<RefeicaoRealizada> refeicaoRealizada = 
				new ArrayList<RefeicaoRealizada>();
		
		refeicaoRealizada = RefeicaoRealizadaDAO.getInstance().getAll();
		
		return refeicaoRealizada;
	}
	
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getRefeicaoRealizadaById(
			@PathParam("id") int idRefeicaoRealizada) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			RefeicaoRealizada refeicaoRealizada = RefeicaoRealizadaDAO
					.getInstance().getById(idRefeicaoRealizada); 
			
			if (refeicaoRealizada != null) {
				
				// Refeição Realizada encontrada.
				builder.status(Response.Status.OK);
				builder.entity(refeicaoRealizada);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.REFEICAO_REALIZADA_NAO_ENCONTRADA));
			}		

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());			
		}

		return builder.build();
	}
	
	/**
	 * Consultar refeição realizada pelo dia da refeição. É necessário informar a 
	 * Refeição e o Dia para a consulta. O Dia será transformado em data referente a
	 * semana vigente. 
	 * 
	 * @param diaRefeicao
	 * @return
	 */
	@PermitAll
	@POST
	@Path("/quantificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getQuantidadeRefeicoesRealizadas(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.quantidadeRefeicaoRealizada(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Recurar dados da Refeição.
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						diaRefeicao.getRefeicao().getId());
				diaRefeicao.setRefeicao(refeicao);
				
				// Id do Dia da Refeicão Realizada.
				int idDia = diaRefeicao.getDia().getId();
				
				// Recuperar Dia.
				Dia dia = DiaDAO.getInstance().getById(idDia);
				
				// Dia da semana da(s) Refeicão(ões) Realizada(s).
				Date dataRefeicaoRealizada = DateUtil.getDateOfDayWeek(idDia);
						
				if (refeicao != null && dia != null) {
					
					// Consultar Refeicão(ões) Realizada(s).
					Long quantidadeDia = RefeicaoRealizadaDAO.getInstance()
							.getQuantidadeDiaRefeicaoRealizada(refeicao,
									dataRefeicaoRealizada);	
					
					// Mapa quantitativo da(s) Refeicão(ões) Realizada(s).
					MapaRefeicao<RefeicaoRealizada> mapaRefeicaoRealizada = 
							new MapaRefeicao<RefeicaoRealizada>();
					mapaRefeicaoRealizada.setQuantidade(
							Integer.valueOf(quantidadeDia.toString()));
					mapaRefeicaoRealizada.setData(dataRefeicaoRealizada);
					mapaRefeicaoRealizada.setDia(dia);			
					
					builder.status(Response.Status.OK).entity(
							mapaRefeicaoRealizada);
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
	@GET
	@Path("/extratorefeicao/{idExtratoRefeicao}")
	@Produces("application/json")
	public Response listByExtratoRefeicao(@PathParam("idExtratoRefeicao") int idExtratoRefeicao) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			ExtratoRefeicao extratoRefeicao = ExtratoRefeicaoDAO.getInstance().getById(idExtratoRefeicao);
			
			if (extratoRefeicao != null) {
				// Refeição
				Refeicao refeicao = extratoRefeicao.getRefeicao();
				
				// Data do extrato
				Date dataExtrato = extratoRefeicao.getDataExtrato();
				
				// Listar os dias das refeições realizadas.
				List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO
						.getInstance().listRefeicoesRealizadas(
								refeicao, dataExtrato);
				
				builder.status(Response.Status.OK).entity(
						refeicoesRealizadas);
			} else {
				
				builder.status(Response.Status.NOT_FOUND).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.ID_EXTRATO_REFEICAO_INVALIDO));
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/extratorefeicao/{idExtratoRefeicao}/aluno/nome/{nome}")
	@Produces("application/json")
	public Response listByExtratoRefeicaoAluno(
			@PathParam("idExtratoRefeicao") int idExtratoRefeicao,
			@PathParam("nome") String nome) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			ExtratoRefeicao extratoRefeicao = ExtratoRefeicaoDAO.getInstance().getById(idExtratoRefeicao);
			
			if (extratoRefeicao != null) {
				// Refeição
				Refeicao refeicao = extratoRefeicao.getRefeicao();
				
				// Data do extrato
				Date dataExtrato = extratoRefeicao.getDataExtrato();
				
				// Listar os dias das refeições realizadas.
				List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO
						.getInstance().listRefeicoesRealizadasAluno(
								refeicao, dataExtrato, nome);
				
				builder.status(Response.Status.OK).entity(
						refeicoesRealizadas);
			} else {
				
				builder.status(Response.Status.NOT_FOUND).entity(
						ErrorFactory.getErrorFromIndex(
								ErrorFactory.ID_EXTRATO_REFEICAO_INVALIDO));
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Consultar refeição(ões) realizada por período.
	 * 
	 * @param periodoRefeicaoRealizada
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/consultar/mapa")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getMapaRefeicoesRealizadas(
			PeriodoRefeicaoRealizada periodoRefeicaoRealizada) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.periodoRefeicaoRealizada(periodoRefeicaoRealizada);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				List<MapaRefeicao<RefeicaoRealizada>> mapasRefeicoesRealizadas = 
						new ArrayList<MapaRefeicao<RefeicaoRealizada>>();
				
				// Data entre o intervalo de dataInicio e dataFim.
				List<Date> datas = DateUtil.getDaysBetweenDates(
						periodoRefeicaoRealizada.getDataInicio(), 
						periodoRefeicaoRealizada.getDataFim());
				
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						periodoRefeicaoRealizada.getRefeicao().getId());				
				
				if (refeicao != null) {	
					
					for (Date data: datas) {
						
						// Consulta dos dias das refeições.
						List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO
								.getInstance().listRefeicoesRealizadas(
										refeicao, data);
						
						// Inicializa o mapa para consulta dos dias das refeições.
						MapaRefeicao<RefeicaoRealizada> mapaRefeicaoRealizada = 
								new MapaRefeicao<RefeicaoRealizada>();				
						mapaRefeicaoRealizada.setRefeicao(
								periodoRefeicaoRealizada.getRefeicao());
						mapaRefeicaoRealizada.setData(data);						
						mapaRefeicaoRealizada.setLista(refeicoesRealizadas);
						mapaRefeicaoRealizada.setQuantidade(
								refeicoesRealizadas.size());
						
						mapasRefeicoesRealizadas.add(mapaRefeicaoRealizada);
					}				
					
					builder.status(Response.Status.OK).entity(
							mapasRefeicoesRealizadas);
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
	
	/**
	 * Listar Refeições Realizadas por Dia e Refeição para todos os Editais vigentes.
	 * 
	 * @param idDia
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/consultar/mapa/dia/{idDia}/refeicao/{idRefeicao}")
	@Produces("application/json")
	public Response getMapaRefeicaoRealizadaByDiaRefeicao(@PathParam("idDia") Integer idDia, 
			@PathParam("idRefeicao") Integer idRefeicao) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());		
		
		// Validação dos dados de entrada.
		int validacao = Validate.listarRefeicaoRealizadaByDiaRefeicao(idDia, idRefeicao);

		if (validacao == Validate.VALIDATE_OK) {
			try {
				
				Dia dia = DiaDAO.getInstance().getById(idDia);
				
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao);
				
				if (dia != null && refeicao != null) {
					
					// Recuperar o data do Dia.
					Date dataRefeicao = DateUtil.getDateOfDayWeek(dia.getId());
					
					// Listar Refeições Realizadas por Data e Refeição.
					List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO.getInstance()
							.listDiaRefeicaoByDataRefeicao(dataRefeicao, idDia, idRefeicao);
					
					// Mapa para contabilização e listagem.
					MapaRefeicao<RefeicaoRealizada> mapa = new MapaRefeicao<RefeicaoRealizada>();						
					mapa.setDia(dia);
					mapa.setRefeicao(refeicao);
					mapa.setData(dataRefeicao);
					mapa.setQuantidade(refeicoesRealizadas.size());
					mapa.setLista(refeicoesRealizadas);
					
					builder.status(Response.Status.OK);
					builder.entity(mapa);
				}				

			} catch (SQLExceptionNutrIF exception) {

				// Erro na manipulação dos dados
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
			}

		} else {

			// Solicitação fora do período de uma refeição.
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}	
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/detalhar/edital/{idEdital}/aluno/{matricula}")
	@Produces("application/json")
	public Response listRefeicaoRealizadaByEditalAluno(@PathParam("idEdital") Integer idEdital, 
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.listarRefeicaoRealizadaByEditalAluno(idEdital, matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
					List<MapaRefeicao<RefeicaoRealizada>> mapas = new ArrayList<MapaRefeicao<RefeicaoRealizada>>();
				
					// Recuperar todo os Dias.
					List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO.getInstance().getAllVigentesByEditalAluno(
							idEdital, matricula);
					
					if (diasRefeicao != null && !diasRefeicao.isEmpty()) {
						
						for (DiaRefeicao diaRefeicao: diasRefeicao) {
							
							Dia dia = diaRefeicao.getDia();
							Integer idDia = dia.getId();
							
							List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO.getInstance()
									.detalharRefeicoesRealizadasByEditalAluno(idEdital, matricula, idDia);
							
							MapaRefeicao<RefeicaoRealizada> mapa = new MapaRefeicao<RefeicaoRealizada>();						
							mapa.setDia(dia);
							mapa.setQuantidade(refeicoesRealizadas.size());
							mapa.setLista(refeicoesRealizadas);	
							
							mapas.add(mapa);
						}

						builder.status(Response.Status.OK);
						builder.entity(mapas);
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
	@GET
	@Path("/falta/{matricula}")
	@Produces("application/json")
	public Response listFalta(@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		int tresSemanas = 3;
		
		// Validação dos dados de entrada.
		int validacao = Validate.VALIDATE_OK;
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
					List<MapaRefeicao<RefeicaoRealizada>> mapasRefeicoesRealizadas = 
						new ArrayList<MapaRefeicao<RefeicaoRealizada>>();
				
					// Recuperar todos os dias de refeição ativos
					List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO.getInstance()
							.getVigentesByMatricula(matricula);
					
					for (DiaRefeicao diaRefeicao: diasRefeicao) {
						// Calcular intervalo.
						List<Date> datas = DateUtil.getAllDatesPastOfDayWeek(tresSemanas, diaRefeicao.getDia().getId());

						// Consultas as reeições realizadas nas últimas 3 semanas
						List<RefeicaoRealizada> refeicoesRealizadas = RefeicaoRealizadaDAO.getInstance()
								.listByDiaRefeicaoInData(diaRefeicao.getId(), datas);
						
						MapaRefeicao<RefeicaoRealizada> mapa = new MapaRefeicao<RefeicaoRealizada>();
						mapa.setDia(diaRefeicao.getDia());
						mapa.setEdital(diaRefeicao.getEdital());
						//TODO: mapa.setAluno(diaRefeicao.getAluno());						
						mapa.setLista(refeicoesRealizadas);
						mapa.setQuantidade(refeicoesRealizadas.size());	
						
						mapasRefeicoesRealizadas.add(mapa);
					}
										
					builder.status(Response.Status.OK).entity(
							mapasRefeicoesRealizadas);
					
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
}
