package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.PretensaoRefeicao;

public class PretensaoRefeicaoDAO extends GenericDao<Integer, PretensaoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(PretensaoRefeicaoDAO.class);
	
	private static PretensaoRefeicaoDAO instance;
	
	public static PretensaoRefeicaoDAO getInstance() {
		instance = new PretensaoRefeicaoDAO();
		return instance;
	}

	@Override
	public List<PretensaoRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("PretensaoRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return PretensaoRefeicao.class;
	}

	@Override
	public PretensaoRefeicao find(PretensaoRefeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
