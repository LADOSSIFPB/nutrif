package br.edu.ifpb.nutrif.controller;

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

import br.edu.ifpb.nutrif.dao.EditalDAO;
import br.edu.ifpb.nutrif.dao.FuncionarioDAO;
import br.edu.ifpb.nutrif.exception.ErrorFactory;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.validation.Validate;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Error;
import br.edu.ladoss.entity.Funcionario;

@Path("edital")
public class EditalController {

	@PermitAll
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insert(Edital edital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());
		
		// Validação dos dados de entrada.
		int validacao = Validate.edital(edital);
		
		if (validacao == Validate.VALIDATE_OK) {
			
			try {			
				
				// Funcionário
				int idFuncionario = edital.getFuncionario().getId();
				Funcionario funcionario = FuncionarioDAO.getInstance()
						.getById(idFuncionario);
				edital.setFuncionario(funcionario);
				
				Date agora = new Date();
				edital.setDataInsercao(agora);
				
				if (idFuncionario != BancoUtil.ID_VAZIO) {
				
					//Inserir o Aluno.
					Integer idEdital = EditalDAO.getInstance().insert(edital);
					
					if (idEdital != BancoUtil.ID_VAZIO) {
	
						// Operação realizada com sucesso.
						builder.status(Response.Status.OK).entity(edital);
					}
				} else {
					
					//TODO: Mensagem de erro para Funcionário não encontrado.
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
	@GET
	@Path("/listar")
	@Produces("application/json")
	public List<Edital> getAll() {
		
		List<Edital> edital = new ArrayList<Edital>();
		
		edital = EditalDAO.getInstance().getAll();
		
		return edital;
	}
	
	@PermitAll
	@GET
	@Path("/id/{id}")
	@Produces("application/json")
	public Response getCursoById(@PathParam("id") int idEdital) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(new Date());

		try {

			Edital edital = EditalDAO.getInstance().getById(idEdital); 
			
			builder.status(Response.Status.OK).entity(edital);

		} catch (SQLExceptionNutrIF qme) {

			Error erro = new Error();
			erro.setCodigo(qme.getErrorCode());
			erro.setMensagem(qme.getMessage());

			builder.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erro);
		}

		return builder.build();
	}
}
