package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.hibernate.HibernateUtil;

public abstract class GenericDao {
	
	private Session session;
	
	public abstract Query getGetAllManagedQuery();
	
	public abstract Class<?> getEntityClass();
	
	public abstract void closeSession();
	
	public int insert(DataEntity entity){
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return entity.getId();
	}

	public void update(DataEntity entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			hexp.printStackTrace();
			
		} finally {
			session.close();
		}
	}

	public void delete(DataEntity entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}
	}

	public List<?> getAll(){
		return getGetAllManagedQuery().list();
	}

	public DataEntity  getById(Integer pk){
		//TODO add an appropriate cast.
		DataEntity entity = (DataEntity) session.get(getEntityClass(), pk);
		return entity;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	

}