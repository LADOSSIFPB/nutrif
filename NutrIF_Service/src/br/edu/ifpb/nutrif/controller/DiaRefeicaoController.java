package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import br.edu.ifpb.nutrif.dao.AlunoDAO;
import br.edu.ifpb.nutrif.dao.DiaDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.EditalDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoDAO;
import br.edu.ifpb.nutrif.dao.RefeicaoRealizadaDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.entity.MapaRefeicao;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;
import br.edu.ladoss.enumeration.TipoRole;

@Path("diarefeicao")
public class DiaRefeicaoController {

	private static Logger logger = LogManager.getLogger(
			DiaRefeicaoController.class);
	/**
	 * Cadastro do dia de refei��o do Aluno.
	 * 
	 * Entrada: JSON
	 * {
	 * 	"aluno":{"id":"[0-9]"},
	 * 	"dia":{"id":"[0-9]"},
	 * 	"refeicao":{"id":"[0-9]"}
	 * 	"edital": {"id": "[0-9]"}
	 * }
	 * 
	 * @param diaRefeicao
	 * @return builder
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.diaRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Aluno.
				int idAluno = diaRefeicao.getAluno().getId();
				Aluno aluno = AlunoDAO.getInstance().getById(idAluno);
				diaRefeicao.setAluno(aluno);
				
				// Recuperar Edital.
				int idEdital = diaRefeicao.getEdital().getId();
				Edital edital = EditalDAO.getInstance().getById(idEdital);
				diaRefeicao.setEdital(edital);
				
				// Recuperar Dia.
				int idDia = diaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				diaRefeicao.setDia(dia);
				
				// Recuperar Refei��o.
				int idRefeicao = diaRefeicao.getRefeicao().getId();
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(idRefeicao);
				diaRefeicao.setRefeicao(refeicao);
				
				// Recuperar Funcion�rio.
				int idFuncionario = diaRefeicao.getFuncionario().getId();
				Funcionario funcionario = FuncionarioDAO.getInstance()
						.getById(idFuncionario);
				diaRefeicao.setFuncionario(funcionario);
				
				if (aluno != null
						&& edital != null
						&& dia != null 
						&& refeicao != null
						&& funcionario != null) {
					
					// Validar Edital: vig�ncia e quantidade de contemplados.
					int quantidadeBeneficiadosReal = DiaRefeicaoDAO
							.getInstance().getBeneficiadosByEdital(
									idEdital);
					
					boolean isContemplado = DiaRefeicaoDAO.getInstance()
							.isAlunoContemplado(idEdital, aluno.getMatricula());
					
					int quantidadeBeneficiadosPrevista = edital.getQuantidadeBeneficiadosPrevista();
					int novaQuantidadeBeneficiados = quantidadeBeneficiadosReal + 1;
					
					// Verificar se o aluno j� � contemplado ou se a quantidade real de benefici�rios est� prevista 
					// com a adi��o do novo dia de refei��o.
					if (isContemplado ||  novaQuantidadeBeneficiados <= quantidadeBeneficiadosPrevista) {
						
						// Verificar se existe dia de refei��o ativo para a mesma refei��o, dia e
						// edital (intervalos de vig�ncia semelhantes).
						List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO.getInstance()
								.getDiaRefeicaoByPeriodoEdital(diaRefeicao);
						
						boolean isDiaRefeicaoAtivo = isDiaRefeicaoVigente(diasRefeicao);								
						
						if (!isDiaRefeicaoAtivo) {
							
							// Data e hora atual.
							Date agora = new Date();
							diaRefeicao.setDataInsercao(agora);
							
							// Ativo
							diaRefeicao.setAtivo(BancoUtil.ATIVO);
							
							//Inserir o Dia da Refeicao.
							Integer idDiaRefeicao = DiaRefeicaoDAO.getInstance()
									.insert(diaRefeicao);
							
							if (idDiaRefeicao != BancoUtil.ID_VAZIO) {
			
								// Opera��o realizada com sucesso.
								builder.status(Response.Status.OK);
								builder.entity(diaRefeicao);
								
							} else {
								
								builder.status(Response.Status.NOT_MODIFIED);
							}
						
						} else {
							
							builder.status(Response.Status.CONFLICT).entity(
									ErrorFactory.getErrorFromIndex(
											ErrorFactory.DIA_REFEICAO_DUPLICADO));
						}
						
					} else {
						
						// Novo dia de refei��o estrapola a quantidade prevista para o Edital.
						builder.status(Response.Status.FORBIDDEN).entity(
								ErrorFactory.getErrorFromIndex(
										ErrorFactory.QUANTIDADE_BENEFICIARIOS_EXCEDENTE));
					}
					
				} else {
					
					//TODO: Informa��es do edital n�o encontrada.
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
	
	private boolean isDiaRefeicaoVigente(List<DiaRefeicao> diasRefeicao) {
		
		boolean isDiaRefeicaoVigente = BancoUtil.INATIVO;
		
		// Caso haja registro o dia de refei��o j� � oferecido ao benefici�rio por meio de edital v�lido 
		// e no per�odo de vigencia.
		if (diasRefeicao != null && !diasRefeicao.isEmpty()) {
			
			isDiaRefeicaoVigente = BancoUtil.ATIVO;
			logger.info("DiaRefei��o ativo: " + isDiaRefeicaoVigente);
		}
		
		return isDiaRefeicaoVigente;
	}

	/**
	 * Remover o dia da refei��o atrav�s do id. A remo��o desativa o registro
	 * pela mudan�a de estado da vari�vel <b>ativo<b> para o valor false.
	 * 
	 * Entrada: JSON
	 * {
	 * 	"id":"[0-9]"
	 * }
	 * 
	 * @param diaRefeicao
	 * @return builder
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/remover")
	@Consumes("application/json")
	@Produces("application/json")
	public Response remover(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.diaRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Dia da Refei��o.
				diaRefeicao = DiaRefeicaoDAO.getInstance().getById(
						diaRefeicao.getId());
				
				// Desabilitar.
				diaRefeicao.setAtivo(false);
				
				// Atualizar.
				diaRefeicao = DiaRefeicaoDAO.getInstance().update(diaRefeicao);
				
				if (diaRefeicao != null) {
					
					// Opera��o realizada com sucesso.
					builder.status(Response.Status.OK);
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
	@Path("/listar")
	@Produces("application/json")
	public List<DiaRefeicao> getAll() {
		
		List<DiaRefeicao> diasRefeicao = 
				new ArrayList<DiaRefeicao>();
		
		diasRefeicao = DiaRefeicaoDAO.getInstance().getAll();
		
		return diasRefeicao;
	}
	
	/**
	 * Recupera o dia da refei��o (ativa ou inativa) atrav�s do id.
	 * 
	 * @param idCronogramaRefeicao
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getDiaRefeicaoById(
			@PathParam("id") int idCronogramaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			DiaRefeicao diaRefeicao = DiaRefeicaoDAO
					.getInstance().getById(idCronogramaRefeicao); 
			
			if (diaRefeicao != null) {
				
				builder.status(Response.Status.OK);
				builder.entity(diaRefeicao);
				
			} else {
				
				// Dia de refei��o n�o existente.
				builder.status(Response.Status.NOT_FOUND)
						.entity(ErrorFactory.getErrorFromIndex(ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO));
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}	
	
	/**
	 * Buscar os dias de refei��o de um Aluno atrav�s do seu Nome. Somente ser�o
	 * retornados os registros que n�o estejam como refei��o realizada.
	 *  
	 * @param nome
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/buscar/aluno/nome/{nome}")
	@Produces("application/json")
	public Response getDiaRefeicaoRealizacaoByAlunoNome(
			@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.nomeAlunoBusca(nome);
		
		if (validacao == Validate.VALIDATE_OK) {
			try {
				
				if (RefeicaoDAO.getInstance().isPeriodoRefeicao()) {
					
					List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
							.getInstance().getDiaRefeicaoRealizadaByAlunoNome(nome);
					
					if (diasRefeicao.size() > BancoUtil.QUANTIDADE_ZERO) {
						
						builder.status(Response.Status.OK);
						builder.entity(diasRefeicao);
						
					} else {
						
						// Dia de refei��o n�o existente.
						// Dia de refei��o n�o existente.
						builder.status(Response.Status.NOT_FOUND)
								.entity(ErrorFactory.getErrorFromIndex(
										ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO_ALUNO));
					}			
				
				} else {
					
					// Solicita��o fora do per�odo de uma refei��o.
					builder.status(Response.Status.FORBIDDEN).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.PERIODO_REFEICAO_INVALIDO));
				}

			} catch (SQLExceptionNutrIF exception) {

				// Erro na manipula��o dos dados
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
			}
		
		} else {
			
			// Solicita��o fora do per�odo de uma refei��o.
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(
					ErrorFactory.getErrorFromIndex(validacao));
		}
		
		return builder.build();		
	}	
	
	/**
	 * Buscar os dias de refei��o de um Aluno atrav�s do sua Matr�cula. 
	 * Somente ser�o retornados os registros que n�o estejam como refei��o
	 * realizada.
	 *   
	 * @param matricula
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/buscar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getDiaRefeicaoRealizacaoByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.matricula(matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {

				if (RefeicaoDAO.getInstance().isPeriodoRefeicao()) {
					
					List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
							.getInstance().getDiaRefeicaoRealizadaByAlunoMatricula(
									matricula);
					
					if (diasRefeicao.size() > BancoUtil.QUANTIDADE_ZERO) {
						
						// Dia de refei��o encontrado
						builder.status(Response.Status.OK);
						builder.entity(diasRefeicao);
					
					} else {
						
						// Verificar dia de refei��o realizado.
						RefeicaoRealizada refeicaoRealizada = 
								RefeicaoRealizadaDAO.getInstance()
									.getRefeicaoRealizadaCorrente(matricula);
						
						if (refeicaoRealizada != null) {
							
							Error error = ErrorFactory.getErrorFromIndex(
									ErrorFactory.REFEICAO_JA_REALIZADA);
							
							String nome = refeicaoRealizada.getConfirmaRefeicaoDia()
									.getDiaRefeicao().getAluno().getNome();
							String mensagem = nome + ". " + error.getMensagem();
							
							error.setMensagem(mensagem);
							
							// Solicita��o fora do per�odo de uma refei��o.
							// Dia de refei��o n�o existente.
							builder.status(Response.Status.NOT_FOUND)
									.entity(error);
						} else {
							
							// Dia de refei��o n�o existente.
							builder.status(Response.Status.NOT_FOUND)
									.entity(ErrorFactory.getErrorFromIndex(
											ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO_ALUNO));
						}
					}					
				
				} else {
					
					// Solicita��o fora do per�odo de uma refei��o.
					builder.status(Response.Status.FORBIDDEN).entity(
							ErrorFactory.getErrorFromIndex(
									ErrorFactory.PERIODO_REFEICAO_INVALIDO));
				}
				
			} catch (SQLExceptionNutrIF exception) {

				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
						exception.getError());
			}
		}				
		
		return builder.build();		
	}
	
	/**
	 * Listar todos os dias de refei��es <b>ativos<b> de um Aluno pela Matr�cula.
	 * 
	 * @param matricula
	 * @return diasRefeicao
	 */
	@PermitAll
	@GET
	@Path("/listar/vigentes/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getAllVigentesByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		logger.info("Consulta do Dia da Refei��o pela Matr�cula"
				+ " para Edital Vigente: " + matricula);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.matricula(matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {

				List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
						.getInstance().getAllVigentesByAlunoMatricula(matricula);
				logger.info("Quantidade de Dias das Refei��es de Editais Vigentes: " 
						+ diasRefeicao.size());
				
				builder.status(Response.Status.OK);
				builder.entity(diasRefeicao);

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
	 * Consultar hist�rico dos dias das defei��es do Aluno pela Matr�cula.
	 * 
	 * @param matricula
	 * @return
	 */
	@PermitAll
	@GET
	@Path("/listar/aluno/matricula/{matricula}")
	@Produces("application/json")
	public Response getAllByAlunoMatricula(
			@PathParam("matricula") String matricula) {
		
		logger.info("Consultar hist�rico dos Dias das Refei��es do Aluno pela Matr�cula: " + matricula);
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.matricula(matricula);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {

				List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
						.getInstance().getAllByAlunoMatricula(matricula);
				logger.info("Quantidade dos Dias das Refei��es: " + diasRefeicao.size());
				
				builder.status(Response.Status.OK);
				builder.entity(diasRefeicao);

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
	@GET
	@Path("/quantificar/edital/{id}")
	@Produces("application/json")
	public Response getQuantidadeDiaRefeicaoByEdital(
			@PathParam("id") int idEdital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		try {

			Edital edital = EditalDAO.getInstance().getById(idEdital);
			List<Dia> dias = DiaDAO.getInstance().getAll();
			List<Refeicao> refeicoes = RefeicaoDAO.getInstance().getAll();
			
			if (edital != null 
					&& (dias!=null && !dias.isEmpty())
					&& (refeicoes!=null && !refeicoes.isEmpty())) {
				
				List<MapaRefeicao<DiaRefeicao>> mapas = new ArrayList<MapaRefeicao<DiaRefeicao>>();
				
				for (Dia dia: dias) {
					
					for (Refeicao refeicao: refeicoes) {
						
						int idDia = dia.getId();
						int idRefeicao = refeicao.getId();
						
						int quantidadeBeneficiados = DiaRefeicaoDAO.getInstance()
								.getBeneficiadosByEdital(idEdital, idDia, idRefeicao);
						
						MapaRefeicao<DiaRefeicao> mapa = new MapaRefeicao<DiaRefeicao>();
						mapa.setQuantidade(quantidadeBeneficiados);
						mapa.setDia(dia);
						mapa.setEdital(edital);
						mapa.setRefeicao(refeicao);
						
						mapas.add(mapa);
					}					
				}
				
				builder.status(Response.Status.OK);
				builder.entity(mapas);
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}		
		
		return builder.build();	
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/quantificar/dia/{id}")
	@Produces("application/json")
	public Response getQuantidadeDiaRefeicaoByDia(
			@PathParam("id") int idEdital) {
		return null;
	}
	
	/**
	 * Quantificar os dias de refei��o v�lidos para um dia com data v�lida do per�odo do Edital.
	 * 
	 * @param diaRefeicao
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/quantificar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getQuantidadeDiaRefeicao(DiaRefeicao diaRefeicao) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.quantidadeDiaRefeicao(diaRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {
				
				// Recuperar dados da refei��o.
				Refeicao refeicao = RefeicaoDAO.getInstance().getById(
						diaRefeicao.getRefeicao().getId());
				diaRefeicao.setRefeicao(refeicao);
				
				// Dia proposto para a pretens�o e data da solicita��o.
				int idDia = diaRefeicao.getDia().getId();
				Dia dia = DiaDAO.getInstance().getById(idDia);
				
				// Verificar pretens�o baseado no dia e refei��o.				
				Date dataDiaRefeicao = calcularDataDiaRefeicao(diaRefeicao);
				
				if (refeicao != null && dia != null) {
					
					// C�lculo da quantidade de pretens�es lan�adas para o pr�ximo dia de refei��o.
					int quantidadeDia = DiaRefeicaoDAO.getInstance()
							.getQuantidadeDiaRefeicao(diaRefeicao, dataDiaRefeicao);
					
					// Mapa com os dados quantificados.
					MapaRefeicao<DiaRefeicao> mapaRefeicao = 
							new MapaRefeicao<DiaRefeicao>();
					mapaRefeicao.setQuantidade(quantidadeDia);
					mapaRefeicao.setData(dataDiaRefeicao);
					mapaRefeicao.setDia(dia);
					mapaRefeicao.setRefeicao(refeicao);
					
					builder.status(Response.Status.OK).entity(
							mapaRefeicao);
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
	
	private Date calcularDataDiaRefeicao(DiaRefeicao diaRefeicao) {
		
		// Calcular data para o dia da refei��o.
		logger.info("Calcular a data do dia da Refei��o: " + diaRefeicao);
		
		// Dia da semana para lan�ar a pretens�o.
		int diaPretensao = diaRefeicao.getDia().getId();
		Date dataDiaRefeicao = DateUtil.getDateOfDayWeek(diaPretensao);
		
		return dataDiaRefeicao;		
	}
	
	/**
	 * Listar todos os dias de refei��es do aluno com base em um dia da semana
	 * @param idDia
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN, TipoRole.INSPETOR})
	@GET
	@Path("/dia/{id}")
	@Produces("application/json")
	public Response getDiaRefeicaoByDia(
			@PathParam("id") Integer idDia) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoByDia(idDia);
			
			if (diasRefeicao != null) {
				
				builder.status(Response.Status.OK);
				builder.entity(diasRefeicao);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND).entity(
						ErrorFactory.getErrorFromIndex(ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO));
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}	
	
	
	/**
	 * Migrar os dias de refei��o dos alunos para um edital.
	 * O edital que vier com identifica��o ser� recuperado e associado aos dias de refei��o,
	 * caso contr�rio ser� castrado.
	 * 
	 * @param idDiaOrigem
	 * @param edital
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/migrar/dia/origem/{idOrigem}/destino/{idDestino}")
	@Produces("application/json")
	public Response migrarDiaRefeicao(
			@PathParam("idOrigem") Integer idDiaOrigem, 
			@PathParam("idDestino") Integer idDiaDestino,
			Edital edital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		//TODO: Valida��o. Verificar se o idDia Origem e Destino s�o v�lidos como inteiro.
		
		try {
			
			//Obter todos os dias de refei��o para o dia escolhido.
			List<DiaRefeicao> diasRefeicaoOrigem = DiaRefeicaoDAO
					.getInstance().getDiaRefeicaoByDia(idDiaOrigem);			
			
			// Inserir ou obter Edital.
			Integer idEdital = edital.getId();
			
			if(idEdital == BancoUtil.ID_VAZIO){
				
				Date agora = new Date();
				edital.setDataInsercao(agora);
				
				// Inserir novo Edital.
				idEdital = EditalDAO.getInstance().insert(edital);
				
			} else {
				
				// Recuperar Edital completo.
				edital = EditalDAO.getInstance().getById(idEdital);
			}
				
			if (diasRefeicaoOrigem != null && !diasRefeicaoOrigem.isEmpty()
					&& edital != null && edital.getId() != BancoUtil.ID_VAZIO) {
				
				// Dia destino
				Dia diaDestino = DiaDAO.getInstance().getById(idDiaDestino);
				
				// Novos Dias de Refei��o.
				for(DiaRefeicao diaRefeicaoOrigem : diasRefeicaoOrigem){
					
					DiaRefeicao diaRefeicaoDestino = new DiaRefeicao();
					
					// Aluno
					diaRefeicaoDestino.setAluno(diaRefeicaoOrigem.getAluno());
					
					// Dia
					diaRefeicaoDestino.setDia(diaDestino);	
					
					// Refei��o
					diaRefeicaoDestino.setRefeicao(diaRefeicaoOrigem.getRefeicao());
					
					// Edital
					diaRefeicaoDestino.setEdital(edital);
		
					// Migra��o
					diaRefeicaoDestino.setMigracao(BancoUtil.ATIVO);
					
					// Ativo
					diaRefeicaoDestino.setAtivo(BancoUtil.ATIVO);
					
					// Funcion�rio
					diaRefeicaoDestino.setFuncionario(edital.getFuncionario());
					
					// Data e hora de inser��o.
					Date agora = new Date();
					diaRefeicaoDestino.setDataInsercao(agora);
					
					DiaRefeicaoDAO.getInstance().insert(diaRefeicaoDestino);
				}
				
				builder.status(Response.Status.OK);
				
			} else {
				
				builder.status(Response.Status.NOT_FOUND);
			}			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	/**
	 * Listar os Dias de Refei��o associados a um Edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/listar/edital/{id}")
	@Produces("application/json")
	public Response listDiaRefeicaoByEdital(@PathParam("id") Integer idEdital) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Valida��o dos dados de entrada.
		int validacao = Validate.listarContempladosEdital(idEdital);

		if (validacao == Validate.VALIDATE_OK) {
			try {

				List<Aluno> alunos = AlunoDAO.getInstance().listAlunoByEdital(idEdital);

				if (alunos!= null && !alunos.isEmpty()) {

					builder.status(Response.Status.OK);
					builder.entity(alunos);

				} else {

					// Dia de refei��o n�o existente.
					builder.status(Response.Status.NOT_FOUND)
							.entity(ErrorFactory.getErrorFromIndex(ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO_EDITAL));
				}

			} catch (SQLExceptionNutrIF exception) {

				// Erro na manipula��o dos dados
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			// Solicita��o fora do per�odo de uma refei��o.
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/listar/edital/{id}/aluno/nome/{nome}")
	@Produces("application/json")
	public Response listDiaRefeicaoByAlunoEdital(@PathParam("id") Integer idEdital,
			@PathParam("nome") String nome) {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		// Valida��o dos dados de entrada.
		int validacao = Validate.listarAlunoEdital(idEdital, nome);

		if (validacao == Validate.VALIDATE_OK) {
			
			try {

				List<Aluno> alunos = AlunoDAO.getInstance().listAlunoByNomeEdital(nome, idEdital);

				if (alunos!= null && !alunos.isEmpty()) {

					builder.status(Response.Status.OK);
					builder.entity(alunos);

				} else {

					// Dia de refei��o n�o existente.
					builder.status(Response.Status.NOT_FOUND)
							.entity(ErrorFactory.getErrorFromIndex(ErrorFactory.DIA_REFEICAO_NAO_DEFINIDO_EDITAL));
				}

			} catch (SQLExceptionNutrIF exception) {

				// Erro na manipula��o dos dados
				builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getError());
			}

		} else {

			// Solicita��o fora do per�odo de uma refei��o.
			builder.status(Response.Status.NOT_ACCEPTABLE).entity(ErrorFactory.getErrorFromIndex(validacao));
		}

		return builder.build();
	}
}