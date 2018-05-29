package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Turno;

public class TurnoDAO extends GenericDao<Integer, Turno>{
	
	private static Logger logger = LogManager.getLogger(TurnoDAO.class);
	
	private static TurnoDAO instance;
	
	public TurnoDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static TurnoDAO getInstance() {
		instance = new TurnoDAO();
		return instance;
	}

	@Override
	public List<Turno> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Turno.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Turno.class;
	}

	@Override
	public Turno find(Turno entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
