package br.edu.ifpb.nutrif.dao.migration;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.dao.GenericDao;
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

	public boolean isCpfSuap(String numero) {
		
		boolean cpfSuap = false;
		
		Session session = super.getSessionFactory().openSession();
		
		try {			
			
			String hql = "select case when (count(cpf.numero) > 0) then true else false end " 
					+ " from Cpf as cpf"
					+ " where cpf.numero = :numero";
			
			Query query = session.createQuery(hql, Boolean.class);
			query.setParameter("numero", numero);
			
			cpfSuap = (Boolean) query.getSingleResult();;
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return cpfSuap;
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
