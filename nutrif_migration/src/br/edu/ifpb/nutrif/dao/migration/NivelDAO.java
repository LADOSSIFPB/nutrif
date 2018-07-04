package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import br.edu.ifpb.nutrif.dao.GenericDao;
import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Nivel;

public class NivelDAO extends GenericDao<Integer, Nivel>{
	
	private static NivelDAO instance;
	
	private NivelDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static NivelDAO getInstance() {
		instance = new NivelDAO();
		return instance;
	}

	@Override
	public List<Nivel> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Nivel.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Nivel.class;
	}

	@Override
	public Nivel find(Nivel entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
