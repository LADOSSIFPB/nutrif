package br.edu.ifpb.nutrif.dao;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Refeicao;

public class RefeicaoDAO extends AbstractDAO<Integer, Refeicao>{
	
private static Logger logger = LogManager.getLogger(RefeicaoDAO.class);
	
	private static RefeicaoDAO instance;
	
	public static RefeicaoDAO getInstance() {
		instance = new RefeicaoDAO();
		return instance;
	}
	
	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		//Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<Refeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Refeicao.getAll");
	}

	@Override
	public Refeicao getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Refeicao refeicao = null;
		
		try {
		
			session.beginTransaction();
			refeicao = (Refeicao) session.get(Refeicao.class, pk);
	        Hibernate.initialize(refeicao);
	        session.getTransaction().commit();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return refeicao;
	}
}
