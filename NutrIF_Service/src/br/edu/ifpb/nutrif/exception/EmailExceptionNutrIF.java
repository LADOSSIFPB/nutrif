package br.edu.ifpb.nutrif.exception;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ladoss.entity.Erro;

public class EmailExceptionNutrIF extends RuntimeException 
	implements NutrIFException {

	private static final long serialVersionUID = -4829326986819734549L;

	private Logger logger = LogManager.getLogger(EmailExceptionNutrIF.class);
	
	private int errorCode;
	
	private static final Map<Integer, String> erros = new HashMap<Integer, String>();
	static {
		erros.put(1, "Não foi possível enviar o e-mail.");
	}
	
	public EmailExceptionNutrIF(MessagingException messagingException) {
		
		super(messagingException);
		
		logger.error(messagingException.getMessage());
		
		this.errorCode = 1;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public Erro getError() {
		
		return new Erro(errorCode, erros.get(errorCode));		
	}
}
