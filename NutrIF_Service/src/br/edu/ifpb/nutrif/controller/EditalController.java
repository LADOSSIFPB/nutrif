package br.edu.ifpb.nutrif.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.edu.ifpb.nutrif.dao.CampusDAO;
import br.edu.ifpb.nutrif.dao.DiaRefeicaoDAO;
import br.edu.ifpb.nutrif.dao.EditalDAO;
import br.edu.ifpb.nutrif.dao.EventoDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Campus;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Evento;
import br.edu.ladoss.entity.Funcionario;
import br.edu.ladoss.enumeration.TipoRole;

@Path("edital")
public class EditalController {

	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Edital edital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.inserirEdital(edital);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				// Evento
				int idEvento = edital.getEvento().getId(); 
				Evento evento = EventoDAO.getInstance().getById(idEvento);
				edital.setEvento(evento);
				
				// Responsável pelos atos do Edital.
				int idResponsavel = edital.getResponsavel().getId();
				Funcionario responsavel = FuncionarioDAO.getInstance()
						.getById(idResponsavel);
				edital.setFuncionario(responsavel);
				
				// Funcionário que cadastrou o Edital.
				int idFuncionario = edital.getFuncionario().getId();
				Funcionario funcionario = FuncionarioDAO.getInstance()
						.getById(idFuncionario);
				edital.setFuncionario(funcionario);
				
				// Campus
				int idCampus = edital.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				edital.setCampus(campus);
				
				// Período de validade com a hora inicial e final do dia.
				Date dataInicial = edital.getDataInicial();
				dataInicial = DateUtil.setTimeInDate(dataInicial, 
						DateUtil.INICIO_DIA);
				edital.setDataInicial(dataInicial);
				
				Date dataFinal = edital.getDataFinal();
				dataFinal = DateUtil.setTimeInDate(dataFinal, 
						DateUtil.FIM_DIA);
				edital.setDataFinal(dataFinal);
				
				// Data de inserção do registro
				Date agora = new Date();
				edital.setDataInsercao(agora);
				
				if (campus != null
						&& responsavel != null
						&& evento != null
						&& funcionario != null) {
				
					//Inserir o Aluno.
					Integer idEdital = EditalDAO.getInstance().insert(edital);
					
					if (idEdital != BancoUtil.ID_VAZIO) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK).entity(edital);
					} else {
						
						builder.status(Response.Status.NOT_MODIFIED);
					}
					
				} else {
					
					//TODO: Mensagem de erro para Funcionário ou Campus não encontrados.
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
	@Path("/remover/id/{id}")
	@Produces("application/json")
	public Response remover(@PathParam("id") int idEdital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.removerEdital(idEdital);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Recuperar Dia da Refeição.
				Edital edital = EditalDAO.getInstance().getById(idEdital);
				
				if (edital != null && edital.getId() != BancoUtil.ID_VAZIO) {
					
					// Desabilitar.
					edital.setAtivo(BancoUtil.INATIVO);
					
					// Atualizar.
					edital = EditalDAO.getInstance().update(edital);
					
					// Desativação dos dias de refeição associados ao Edital.
					List<DiaRefeicao> diasRefeicao = DiaRefeicaoDAO.getInstance().getAllAtivoByEdital(idEdital);
					
					for(DiaRefeicao diaRefeicao: diasRefeicao) {
						
						diaRefeicao.setAtivo(BancoUtil.INATIVO);
						DiaRefeicaoDAO.getInstance().update(diaRefeicao);
					}
						
					// Operação realizada com sucesso.
					builder.status(Response.Status.OK);
				
				} else {
					
					// Edital não encontrado.
					Error erro = ErrorFactory.getErrorFromIndex(ErrorFactory.ID_EDITAL_INVALIDO);
					builder.status(Response.Status.NOT_FOUND).entity(erro);
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
	public List<Edital> getAll() {
		
		List<Edital> editais = new ArrayList<Edital>();
		
		editais = EditalDAO.getInstance().getAll();
		
		return editais;
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/listar/vigentes")
	@Produces("application/json")
	public List<Edital> getVigentes() {
		
		List<Edital> editais = new ArrayList<Edital>();
		
		editais = EditalDAO.getInstance().listVigentes();
		
		return editais;
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getEditalById(@PathParam("id") int idEdital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Edital edital = EditalDAO.getInstance().getById(idEdital); 
			
			if (edital != null) {
				
				int quantidadeBeneficiadosReal = DiaRefeicaoDAO
						.getInstance().getQuantidadeDiaRefeicaoEdital(
								idEdital);
				edital.setQuantidadeBeneficiadosReal(
						quantidadeBeneficiadosReal);
				
				builder.status(Response.Status.OK).entity(edital);
			}
			

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();
	}
	
	@RolesAllowed({TipoRole.ADMIN})
	@GET
	@Path("/listar/nome/{nome}")
	@Produces("application/json")
	public Response getByNome(@PathParam("nome") String nome) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		List<Edital> editais = new ArrayList<Edital>();
		
		try {

			editais = EditalDAO.getInstance().listByNome(nome);
			
			for (Edital edital: editais) {
				
				int idEdital = edital.getId();
				
				int quantidadeBeneficiadosReal = DiaRefeicaoDAO
						.getInstance().getQuantidadeDiaRefeicaoEdital(
								idEdital);
				edital.setQuantidadeBeneficiadosReal(
						quantidadeBeneficiadosReal);				
			}
			
			builder.status(Response.Status.OK).entity(editais);

		} catch (SQLExceptionNutrIF exception) {

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					exception.getError());
		}

		return builder.build();		
	}
	
	
	/**
	 * Atualizar dados da Refeição.
	 * 
	 * @param refeicao
	 * @return
	 */
	@RolesAllowed({TipoRole.ADMIN})
	@POST
	@Path("/atualizar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(Edital edital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.atualizarEdital(edital);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				// Evento
				int idEvento = edital.getEvento().getId(); 
				Evento evento = EventoDAO.getInstance().getById(idEvento);
				edital.setEvento(evento);
				
				// Responsável pelos atos do Edital.
				int idResponsavel = edital.getResponsavel().getId();
				Funcionario responsavel = FuncionarioDAO.getInstance()
						.getById(idResponsavel);
				edital.setFuncionario(responsavel);
				
				// Funcionário que cadastrou o Edital.
				int idFuncionario = edital.getFuncionario().getId();
				Funcionario funcionario = FuncionarioDAO.getInstance()
						.getById(idFuncionario);
				edital.setFuncionario(funcionario);
				
				// Campus
				int idCampus = edital.getCampus().getId();
				Campus campus = CampusDAO.getInstance().getById(idCampus);
				edital.setCampus(campus);
				
				// Período de validade com a hora inicial e final do dia.
				Date dataInicial = edital.getDataInicial();
				dataInicial = DateUtil.setTimeInDate(dataInicial, 
						DateUtil.INICIO_DIA);
				edital.setDataInicial(dataInicial);
				
				Date dataFinal = edital.getDataFinal();
				dataFinal = DateUtil.setTimeInDate(dataFinal, 
						DateUtil.FIM_DIA);
				edital.setDataFinal(dataFinal);
				
				// Data de inserção do registro
				Date agora = new Date();
				edital.setDataInsercao(agora);
				
				if (campus != null
						&& responsavel != null
						&& evento != null
						&& funcionario != null) {
				
					//Inserir o Aluno.
					edital = EditalDAO.getInstance().update(edital);
					
					if (edital != null) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK).entity(edital);
						
					} else {
						
						builder.status(Response.Status.NOT_MODIFIED);
					}
					
				} else {
					
					//TODO: Mensagem de erro para Funcionário ou Campus não encontrados.
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
}
