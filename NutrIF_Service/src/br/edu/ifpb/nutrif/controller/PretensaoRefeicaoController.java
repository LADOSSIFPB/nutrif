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
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicao.getConfirmaPretensaoDia();
				
				// Verifica dia da refei��o.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				
				if (diaRefeicao != null) {					
					
					// Atribuindo dia da refei��o com dados completos.
					confirmaPretensaoDia.setDiaRefeicao(diaRefeicao);
					
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
		
		// Valida��o dos dados de entrada.
		int validacao = Validate.pretensaoRefeicao(pretensaoRefeicao);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				ConfirmaPretensaoDia confirmaPretensaoDia = 
						pretensaoRefeicao.getConfirmaPretensaoDia();
				
				// Verifica dia da refei��o.
				DiaRefeicao diaRefeicao = DiaRefeicaoDAO.getInstance()
						.getById(confirmaPretensaoDia.getDiaRefeicao().getId());
				
				if (diaRefeicao != null) {
									
					// Extrair da Refei��o a hora m�xima para solicita��o da pretens�o.
					Refeicao refeicao = diaRefeicao.getRefeicao();
					
					// Dia da semana para lan�ar a pretens�o.
					int diaPretensao = diaRefeicao.getDia().getId();
					
					// Dia da semana que da solicita��o para a pretens�o.
					int diaSolicitacao = DateUtil.getCurrentDayOfWeek().getId();
					
					// Diferen�a de dias entre solicita��o e pretens�o.
					int diferenca = diaPretensao - diaSolicitacao;
					
					if (diaPretensao == diaSolicitacao) {
						
						diferenca = 7;						
					}
					
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
						
						// Atribui��o das datas de pretens�o e solicita��o.
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
				RefeicaoRealizada refeicaoRealizada = new RefeicaoRealizada();
				refeicaoRealizada.setConfirmaRefeicaoDia(confirmaRefeicaoDia);
				
				// Confirma��o da refei��o realizada atrav�s da chave de acesso.
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
