package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Dia;

public class DiaDAO extends AbstractDAO<Integer, Dia>{
	
private static Logger logger = LogManager.getLogger(DiaDAO.class);
	
	private static DiaDAO instance;
	
	public static DiaDAO getInstance() {
		instance = new DiaDAO();
		return instance;
	}
	
	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		//Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<Dia> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Dia.getAll");
	}

	@Override
	public Dia getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Dia dia = null;
		
		try {
		
			session.beginTransaction();
			dia = (Dia) session.get(Dia.class, pk);
	        Hibernate.initialize(dia);
	        session.getTransaction().commit();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return dia;
	}
}
