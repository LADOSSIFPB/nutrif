package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Dia;

public class DiaDAO extends GenericDao<Integer, Dia>{
	
	private static Logger logger = LogManager.getLogger(DiaDAO.class);
	
	private static DiaDAO instance;
	
	public DiaDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static DiaDAO getInstance() {
		instance = new DiaDAO();
		return instance;
	}

	@Override
	public List<Dia> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Dia.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Dia.class;
	}

	@Override
	public Dia find(Dia entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
