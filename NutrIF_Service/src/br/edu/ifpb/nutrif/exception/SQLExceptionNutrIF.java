package br.edu.ifpb.nutrif.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import br.edu.ladoss.entity.Erro;

public class SQLExceptionNutrIF extends HibernateException {

	private static final long serialVersionUID = 6315776920468858333L;

	private Logger logger = LogManager.getLogger(SQLExceptionNutrIF.class);
	
	private int errorCode;
	
	private static final Map<Integer, String> erros = new HashMap<Integer, String>();
	static {
		erros.put(100, "Usu�rio n�o existe no sistema.");
		erros.put(101, "Senha inv�lida!");
		erros.put(102, "Or�amento insuficiente!");
		erros.put(666, "Falha convers�o da data.");
		erros.put(1062, "Entidade submetida j� existente.");
		erros.put(1052, "Consulta com coluna amb�gua.");
		erros.put(1054, "Coluna desconhecida.");
		erros.put(1136,
				"Contagem de colunas n�o confere com a contagem de valores.");
		erros.put(1146, "Entidade n�o existe no Banco de Dados.");
		erros.put(1406, "Dado muito longo para o campo. "
				+ "Favor entre em contato com a equipe de desenvolvimento.");
		erros.put(1451, "N�o � poss�vel excluir ou atualizar uma "
				+ "linha pai: uma restri��o de chave estrangeira falhou.");
		erros.put(1452, "A restri��o de chave estrangeira falhou.");
		erros.put(1364, "Verifique se todos os campos est�o preenchidos adequadamente.");
		erros.put(0, "Problema na comunica��o com o banco de dados. "
				+ "Favor entre em contato com a equipe de desenvolvimento.");
	}

	public SQLExceptionNutrIF(SQLException sqlException) {
		
		super(sqlException);
		
		this.errorCode = sqlException.getErrorCode();

		logger.error(this.errorCode + ": " + sqlException.getLocalizedMessage());
		logger.error(sqlException.getStackTrace());
	}
	
	public SQLExceptionNutrIF(int errorCode, String localizedMessage) {

		super(erros.get(errorCode));

		this.errorCode = errorCode;

		logger.error(errorCode + ": " + localizedMessage);		
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public Erro getErro() {
		
		return new Erro(errorCode, erros.get(errorCode));		
	}
}