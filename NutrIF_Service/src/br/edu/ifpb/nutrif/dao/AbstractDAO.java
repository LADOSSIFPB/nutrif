package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;

public abstract class AbstractDAO<PK, T> implements GenericDAO<PK, T> {

	private static Logger logger = LogManager.getLogger(AbstractDAO.class);
	
	@Override
	public int insert(T entity) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract Insert to: " + entity.getClass());
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		Integer id;
		
		try {
			
			session.beginTransaction();
			id = (Integer) session.save(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			throw e;
			
		} finally {
			
			session.close();
		}
		
		return id;
	}

	@Override
	public void update(T entity) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract Update to: " + entity.getClass());
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			session.beginTransaction();
			session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {

			logger.error(e.getMessage());
			session.getTransaction().rollback();

		} finally {

			session.close();
		}		
	}

	@Override
	public List<T> getAll(String namedQuery) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract GetAll to: " + namedQuery);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> roons = null;

		try {
			
			session.beginTransaction();
			Query query = session.getNamedQuery(namedQuery);
			roons = query.list();
			session.getTransaction().commit();
			
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
			
			session.close();
		}

		return roons;
	}

	@Override
	public List<T> find(T entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}	
}
