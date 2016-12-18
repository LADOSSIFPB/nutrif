package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Login;

public class LoginDAO extends GenericDao<Integer, Login> {

	private static Logger logger = LogManager.getLogger(LoginDAO.class);
	
	private static LoginDAO instance;
	
	public static LoginDAO getInstance() {		
		instance = new LoginDAO();		
		return instance;
	}
	
	public Login getLoginByKeyAuth(String authorization) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Login login = null;
		
		try {
			
			String hql = "from Login as l"
					+ " where l.keyAuth = :keyAuth"
					+ " and l.loged = :loged";
			
			Query query = session.createQuery(hql);
			query.setParameter("keyAuth", authorization);
			query.setParameter("loged", BancoUtil.ATIVO);
			
			login = (Login) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return login;
	}
	
	@Override
	public List<Login> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Login.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Login.class;
	}

	@Override
	public Login find(Login entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
