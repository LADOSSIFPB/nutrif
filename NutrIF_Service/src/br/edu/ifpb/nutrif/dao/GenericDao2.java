package br.edu.ifpb.nutrif.dao;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;

public abstract class GenericDao2<PK, T> {
	
	private static Logger logger = LogManager.getLogger(AbstractDAO.class);
	
	public abstract String getNamedQueryValue();
	
	public abstract Class<?> getEntityClass();
	
	public int insert(T entity)throws SQLExceptionNutrIF {
		
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

	public void update(T entity) throws SQLExceptionNutrIF{
		
		logger.info("Init abstract Update to: " + entity.getClass());
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			
			session.beginTransaction();
			session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			
			session.getTransaction().rollback();
			
			throw e;
			
		} finally {
			
			session.close();
		}
	}

	public void delete(T entity) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			
			session.getTransaction().rollback();

			throw e;
			
		} finally {
			
			session.close();
		}
	}

	public List<?> getAll(){
		
		logger.info("Init abstract GetAll to: " + getNamedQueryValue());
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.getNamedQuery(getNamedQueryValue());	
		
		return query.list();
	}

	public T getById(Integer pk){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		T entity = (T) session.get(getEntityClass(), pk);
		
		return entity;
	}
}