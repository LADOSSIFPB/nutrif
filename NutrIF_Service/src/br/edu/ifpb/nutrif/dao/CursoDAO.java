package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Curso;

public class CursoDAO extends AbstractDAO<Integer, Curso>{
	
	private static Logger logger = LogManager.getLogger(CursoDAO.class);
	
	private static CursoDAO instance;
	
	public static CursoDAO getInstance() {
		instance = new CursoDAO();
		return instance;
	}
	
	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		//Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<Curso> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Curso.getAll");
	}

	@Override
	public Curso getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Curso curso = null;
		
		try {
		
			session.beginTransaction();
			curso = (Curso) session.get(Curso.class, pk);
	        Hibernate.initialize(curso);
	        session.getTransaction().commit();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return curso;
	}
}
