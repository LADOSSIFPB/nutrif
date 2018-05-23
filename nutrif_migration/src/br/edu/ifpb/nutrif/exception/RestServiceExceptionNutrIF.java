package br.edu.ifpb.nutrif.exception;

import java.io.Serializable;

import br.edu.ladoss.entity.Error;

public class RestServiceExceptionNutrIF extends Exception implements NutrIFException, Serializable {

	private static final long serialVersionUID = 6924999521317843314L;

	public RestServiceExceptionNutrIF() {
		super();
	}

	public RestServiceExceptionNutrIF(String msg) {
		super(msg);
	}

	public RestServiceExceptionNutrIF(String msg, Exception e) {
		super(msg, e);
	}

	@Override
	public Error getError() {
		
		Error error = ErrorFactory.getErrorFromIndex(ErrorFactory.ERRO_INTERNO_SERVICO);
		error.setDetalhe(getMessage());
		
		return error;
	}
}