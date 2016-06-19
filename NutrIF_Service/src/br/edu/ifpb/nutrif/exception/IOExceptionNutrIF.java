package br.edu.ifpb.nutrif.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ladoss.entity.Error;

public class IOExceptionNutrIF extends IOException implements NutrIFException {

	private static final long serialVersionUID = -4575616448229359228L;

	private Logger logger = LogManager.getLogger(IOExceptionNutrIF.class);
	
	private static final Map<Integer, String> erros = new HashMap<Integer, String>();
	static {
		erros.put(1, "Problema ao manipular o arquivo.");
	}
	
	public IOExceptionNutrIF(String message) {
		super(message);
		logger.error(message);
	}

	@Override
	public Error getError() {
		// TODO Auto-generated method stub
		return null;
	}
}