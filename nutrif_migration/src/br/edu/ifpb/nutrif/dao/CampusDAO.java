package br.edu.ifpb.nutrif.dao;

import java.util.List;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Campus;

public class CampusDAO extends GenericDao<Integer, Campus>{
	
	private static CampusDAO instance;
	
	public CampusDAO() {
		super(HibernateUtil.getSessionFactoryOld());
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
