package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.CronogramaRefeicao;

public class CronogramaRefeicaoDAO extends GenericDao<Integer, CronogramaRefeicao> {
	
	private static Logger logger = LogManager.getLogger(CronogramaRefeicaoDAO.class);

	private static CronogramaRefeicaoDAO instance;

	public static CronogramaRefeicaoDAO getInstance() {
		instance = new CronogramaRefeicaoDAO();
		return instance;
	}

	@Override
	public List<CronogramaRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("CronogramaRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return CronogramaRefeicao.class;
	}
}
