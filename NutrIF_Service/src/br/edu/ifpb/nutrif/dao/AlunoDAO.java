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
import br.edu.ladoss.entity.Aluno;

public class AlunoDAO extends AbstractDAO<Integer, Aluno> {

	private static Logger logger = LogManager.getLogger(AlunoDAO.class);
	
	private static AlunoDAO instance;
	
	public static AlunoDAO getInstance() {
		instance = new AlunoDAO();
		return instance;
	}
	
	@Override
	public int delete(Integer pk) throws SQLExceptionNutrIF {
		//Não é necessário no momento @gustavo
		return 0;
	}

	@Override
	public List<Aluno> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Person.getAll");
	}

	@Override
	public Aluno getById(Integer pk) throws SQLExceptionNutrIF {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Aluno aluno = null;
		
		try {
		
			session.beginTransaction();
			aluno = (Aluno) session.get(Aluno.class, pk);
	        Hibernate.initialize(aluno);
	        session.getTransaction().commit();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}

	public Aluno getByNameAndRegistration(String nome, String matricula) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Aluno aluno = null;
		List<Aluno> alunos;
		Query query;
		
		try {
		
			session.beginTransaction();
			
			query = session.createQuery("from Aluno where nome like :nome and matricula = :matricula");
			query.setParameter("nome", nome);
			query.setParameter("matricula", matricula);

			alunos = (List<Aluno>)query.list();
			
			if (alunos.isEmpty()) {
				alunos = null;
			} else {
				aluno = alunos.get(0);
			}
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}
}
