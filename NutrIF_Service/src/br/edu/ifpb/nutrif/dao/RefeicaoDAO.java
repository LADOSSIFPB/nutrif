package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Refeicao;

public class RefeicaoDAO extends GenericDao<Integer, Refeicao>{
	
	private static Logger logger = LogManager.getLogger(RefeicaoDAO.class);
	
	private static RefeicaoDAO instance;
	
	public static RefeicaoDAO getInstance() {
		instance = new RefeicaoDAO();
		return instance;
	}

	@Override
	public List<Refeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Refeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Refeicao.class;
	}

	@Override
	public Refeicao find(Refeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
