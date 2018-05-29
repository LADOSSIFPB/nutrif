package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Periodo;

public class PeriodoDAO extends GenericDao<Integer, Periodo>{
	
	private static Logger logger = LogManager.getLogger(PeriodoDAO.class);
	
	private static PeriodoDAO instance;
	
	public PeriodoDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static PeriodoDAO getInstance() {
		instance = new PeriodoDAO();
		return instance;
	}

	@Override
	public List<Periodo> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Periodo.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Periodo.class;
	}

	@Override
	public Periodo find(Periodo entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
