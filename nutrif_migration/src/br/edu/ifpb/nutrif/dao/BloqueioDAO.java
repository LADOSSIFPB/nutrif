package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Bloqueio;

public class BloqueioDAO extends GenericDao<Integer, Bloqueio>{
	
	private static Logger logger = LogManager.getLogger(BloqueioDAO.class);
	
	private static BloqueioDAO instance;
	
	public BloqueioDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static BloqueioDAO getInstance() {
		instance = new BloqueioDAO();
		return instance;
	}
	
	public List<Bloqueio> getByMatricula(String matricula) {
		
		return null;
	}

	@Override
	public List<Bloqueio> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Bloqueio.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Bloqueio.class;
	}

	@Override
	public Bloqueio find(Bloqueio entity) throws SQLExceptionNutrIF {
		return null;
	}	
}
