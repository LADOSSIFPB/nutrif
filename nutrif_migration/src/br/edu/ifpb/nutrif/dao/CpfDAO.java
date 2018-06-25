package br.edu.ifpb.nutrif.dao;

import java.util.List;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.migration.Cpf;

public class CpfDAO extends GenericDao<Integer, Cpf>{
	
	private static CpfDAO instance;
	
	private CpfDAO() {
		super(HibernateUtil.getSessionFactoryMigration());
	}
	
	public static CpfDAO getInstance() {
		instance = new CpfDAO();
		return instance;
	}

	@Override
	public List<Cpf> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Cpf.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Cpf.class;
	}

	@Override
	public Cpf find(Cpf entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
