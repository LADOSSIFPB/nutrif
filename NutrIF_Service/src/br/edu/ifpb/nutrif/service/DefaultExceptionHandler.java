package br.edu.ifpb.nutrif.service;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.ErrorDAO;
import br.edu.ifpb.nutrif.exception.RestServiceExceptionNutrIF;
import br.edu.ladoss.entity.Error;

/**
 * Tratar lançamento de exceções para clientes.
 * 
 * @author Rhavy Maia Guedes.
 * 
 */
@Provider
public class DefaultExceptionHandler implements ExceptionMapper<RestServiceExceptionNutrIF> {

	private static Logger logger = LogManager.getLogger(DefaultExceptionHandler.class);
	
    @Override
    public Response toResponse(RestServiceExceptionNutrIF exception) {    	
		
    	// Data e hora atual.
    	Date agora = new Date();
    	
    	// Mensagem de erro e detalhe.
    	logger.info("Processando a exceção.");
    	Error error = exception.getError();
    	error.setRegistro(agora);
    	logger.info("Error: " + error.getDetalhe());

    	// Inserir erro na base de dados.
    	ErrorDAO.getInstance().insert(error); 
    	
    	// Resposta com erro.
    	ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		builder.expires(agora);
		builder.type(MediaType.APPLICATION_JSON);
    	
    	builder.status(Response.Status.INTERNAL_SERVER_ERROR);
		builder.entity(error);
    	
    	return builder.build();
    }
}