package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.PretencaoRefeicao;

public class PretencaoRefeicaoDAO extends GenericDao<Integer, PretencaoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(PretencaoRefeicaoDAO.class);
	
	private static PretencaoRefeicaoDAO instance;
	
	public static PretencaoRefeicaoDAO getInstance() {
		instance = new PretencaoRefeicaoDAO();
		return instance;
	}

	@Override
	public List<PretencaoRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("PretencaoRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return PretencaoRefeicao.class;
	}
}
