package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Campus;

public class CampusDAO extends GenericDao<Integer, Campus>{
	
	private static CampusDAO instance;
	
	private CampusDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static CampusDAO getInstance() {
		instance = new CampusDAO();
		return instance;
	}

	@Override
	public List<Campus> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Campus.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Campus.class;
	}

	@Override
	public Campus find(Campus entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
