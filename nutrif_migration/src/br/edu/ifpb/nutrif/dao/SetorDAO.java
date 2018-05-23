package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.Setor;

public class SetorDAO extends GenericDao<Integer, Setor>{
	
	private static Logger logger = LogManager.getLogger(SetorDAO.class);
	
	private static SetorDAO instance;
	
	public SetorDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static SetorDAO getInstance() {
		instance = new SetorDAO();
		return instance;
	}

	@Override
	public List<Setor> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Setor.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Refeicao.class;
	}

	@Override
	public Setor find(Setor entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
