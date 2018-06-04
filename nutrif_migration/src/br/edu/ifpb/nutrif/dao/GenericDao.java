package br.edu.ifpb.nutrif.dao;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Pessoa;

public abstract class GenericDao<PK, T> {
	
	private static Logger logger = LogManager.getLogger(GenericDao.class);
	
	public abstract List<T> getAll() throws SQLExceptionNutrIF;
	
	public abstract Class<?> getEntityClass();
	
	private SessionFactory sessionFactory;
	
	public GenericDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int insert(T entity) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract Insert to: " + entity.getClass());
		
		Session session = this.sessionFactory.openSession();

		Integer id;
		
		try {
			
			session.beginTransaction();
			id = (Integer) session.save(entity);
			session.getTransaction().commit();

		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
			
			session.close();
		}
		
		return id;
	}
	
	public boolean insertOrUpdate(T entity) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract Insert to: " + entity.getClass());
		
		Session session = this.sessionFactory.openSession();
		
		boolean success = false;
		
		try {
			
			session.beginTransaction();
			session.saveOrUpdate(entity);
			session.getTransaction().commit();
			
			success = true;

		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
			
			session.close();
		}
		
		return success;
	}

	public T update(T entity) throws SQLExceptionNutrIF{
		
		logger.info("Init abstract Update to: " + entity.getClass());
		
		Session session = this.sessionFactory.openSession();

		try {
			
			session.beginTransaction();
			entity = (T) session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
			
			session.close();
		}
		
		return entity;
	}

	public void delete(T entity) {
		
		Session session = this.sessionFactory.openSession();

		try {
			
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();

		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
			
			session.close();
		}
	}
	
	public List<T> getAll(String namedQuery) throws SQLExceptionNutrIF {
		
		logger.info("Init abstract GetAll to: " + namedQuery);
		
		Session session = this.sessionFactory.openSession();
		List<T> list = null;

		try {
			
			session.beginTransaction();
			
	        Query query = session.createNamedQuery(namedQuery);
	        
			list = (List<T>) query.list();
			session.getTransaction().commit();
			
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
			
			session.close();
		}

		return list;
	}
	
	public List<T> getAll(int pageNumber, int pageSize) {
        
		Session session = this.sessionFactory.getCurrentSession();
		List<T> list = null;
		
		try {
			
			session.beginTransaction();

			String nameClass = getEntityClass().getSimpleName();
			String namedQuery = nameClass + ".getAll";
			
	        Query query = session.createNamedQuery(namedQuery);
	        
	        query.setFirstResult((pageNumber - 1) * pageSize);
	        query.setMaxResults(pageSize);

	        list = (List<T>) query.list();
	        session.getTransaction().commit();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
		}		
        
        return list;
    }

	public T getById(Integer pk) throws SQLExceptionNutrIF {		
		
		Session session = this.sessionFactory.openSession();
		
		T entity = null;
		
		try {
		
			session.beginTransaction();
			entity = (T) session.get(getEntityClass(), pk);
	        Hibernate.initialize(entity);
	        session.getTransaction().commit();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return entity;
	}
	
	public T getByIdMigracao(Integer idMigracao) throws SQLExceptionNutrIF {		
		
		Session session = this.sessionFactory.openSession();
		
		T entity = null;
		
		try {
		
			String nameClass = getEntityClass().getSimpleName();
			String hql = "from "+ nameClass +" as ent"
					+ " where ent.idMigracao = :idMigracao";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idMigracao", idMigracao);
			
			entity = (T) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return entity;
	}
	
	public abstract T find(T entity) throws SQLExceptionNutrIF;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}