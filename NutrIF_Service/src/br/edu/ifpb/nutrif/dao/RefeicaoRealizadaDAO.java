package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.RefeicaoRealizada;

public class RefeicaoRealizadaDAO extends GenericDao<Integer, RefeicaoRealizada> {
	
	private static Logger logger = LogManager.getLogger(RefeicaoRealizadaDAO.class);

	private static RefeicaoRealizadaDAO instance;

	public static RefeicaoRealizadaDAO getInstance() {
		instance = new RefeicaoRealizadaDAO();
		return instance;
	}

	@Override
	public List<RefeicaoRealizada> getAll() throws SQLExceptionNutrIF {
		return super.getAll("RefeicaoRealizada.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return RefeicaoRealizada.class;
	}
}
