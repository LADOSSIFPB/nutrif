package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Turma;

public class TurmaDAO extends GenericDao<Integer, Turma>{
	
	private static Logger logger = LogManager.getLogger(TurmaDAO.class);
	
	private static TurmaDAO instance;
	
	public TurmaDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static TurmaDAO getInstance() {
		instance = new TurmaDAO();
		return instance;
	}

	@Override
	public List<Turma> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Turma.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Turma.class;
	}

	@Override
	public Turma find(Turma entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
