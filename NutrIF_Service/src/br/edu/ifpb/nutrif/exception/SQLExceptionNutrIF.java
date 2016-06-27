package br.edu.ifpb.nutrif.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;

import br.edu.ladoss.entity.Error;

public class SQLExceptionNutrIF extends RuntimeException 
	implements NutrIFException {

	private static final long serialVersionUID = 6315776920468858333L;

	private Logger logger = LogManager.getLogger(SQLExceptionNutrIF.class);
	
	private int errorCode;

	private static final int HIBERNATE_ERRO = -1;
	
	private static final Map<Integer, String> erros = new HashMap<Integer, String>();
	static {
		erros.put(100, "Registro não existe no sistema.");
		erros.put(101, "Senha inválida!");
		erros.put(666, "Falha conversão da data.");
		erros.put(1062, "Entidade submetida já existente.");
		erros.put(1052, "Consulta com coluna ambígua.");
		erros.put(1054, "Coluna desconhecida.");
		erros.put(1136,
				"Contagem de colunas não confere com a contagem de valores.");
		erros.put(1146, "Entidade não existe no Banco de Dados.");
		erros.put(1406, "Dado muito longo para o campo. "
				+ "Favor entre em contato com a equipe de desenvolvimento.");
		erros.put(1451, "Não é possível excluir ou atualizar uma "
				+ "linha pai: uma restrição de chave estrangeira falhou.");
		erros.put(1452, "A restrição de chave estrangeira falhou.");
		erros.put(1364, "Verifique se todos os campos estão preenchidos adequadamente.");
		erros.put(1048, "Registro duplicado já inserido anteriormente.");
		erros.put(0, "Problema na comunicação com o banco de dados. "
				+ "Favor entre em contato com a equipe de desenvolvimento.");		
		erros.put(-1, "Problema na base de dados. "
				+ "Favor entre em contato com a equipe de desenvolvimento.");
	}
	
	public SQLExceptionNutrIF(HibernateException hibernateException) {
		
		super(hibernateException);
		
		if(hibernateException instanceof ConstraintViolationException) {
			
			ConstraintViolationException constraintViolationException = 
					(ConstraintViolationException) hibernateException;
			
			this.errorCode = constraintViolationException.getErrorCode();
			
		} else {
			
			this.errorCode = HIBERNATE_ERRO;
		}
		
		logger.error("ErrorCode: " + this.errorCode + ", Message: " + hibernateException.getMessage());
		logger.error(hibernateException.getStackTrace());
	}

	public SQLExceptionNutrIF(JDBCException jdbcException) {
		
		super(jdbcException);
		
		this.errorCode = jdbcException.getErrorCode();

		logger.error("ErrorCode: " + this.errorCode + ", Message: " 
				+ jdbcException.getMessage());
		logger.error(jdbcException.getStackTrace());
	}
	
	public SQLExceptionNutrIF(int errorCode) {

		super(erros.get(errorCode));

		this.errorCode = errorCode;

		logger.error("ErrorCode: " + errorCode + ", Localized message: " 
				+ erros.get(errorCode));		
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public Error getError() {
		
		return new Error(errorCode, erros.get(errorCode));		
	}
}