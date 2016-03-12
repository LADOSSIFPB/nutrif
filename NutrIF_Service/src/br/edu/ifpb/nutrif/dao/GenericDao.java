package br.edu.ifpb.nutrif.dao;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;

public abstract class GenericDao<PK, T> {
	
	private static Logger logger = LogManager.getLogger(GenericDao.class);
	
	public abstract List<T> getAll() throws SQLExceptionNutrIF;
	
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

	public T update(T entity) throws SQLExceptionNutrIF{
		
		logger.info("Init abstract Update to: " + entity.getClass());
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			
			session.beginTransaction();
			entity = (T) session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			
			session.getTransaction().rollback();
			
			throw e;
			
		} finally {
			
			session.close();
		}
		
		return entity;
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
	
	public List<T> getAll(String namedQuery) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract GetAll to: " + namedQuery);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> list = null;

		try {
			
			session.beginTransaction();
			Query query = session.getNamedQuery(namedQuery);
			list = (List<T>) query.list();
			session.getTransaction().commit();
			
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
			
			session.close();
		}

		return list;
	}

	public T getById(Integer pk){		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		T entity = null;
		
		try {
		
			session.beginTransaction();
			entity = (T) session.get(getEntityClass(), pk);
	        Hibernate.initialize(entity);
	        session.getTransaction().commit();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return entity;
	}
	
	public List<T> find(T entity) throws SQLExceptionNutrIF {
		return null;		
	}
}