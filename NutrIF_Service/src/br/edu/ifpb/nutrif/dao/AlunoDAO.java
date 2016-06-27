package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Aluno;

public class AlunoDAO extends GenericDao<Integer, Aluno> {

	private static Logger logger = LogManager.getLogger(AlunoDAO.class);
	
	private static AlunoDAO instance;
	
	public static AlunoDAO getInstance() {		
		instance = new AlunoDAO();		
		return instance;
	}

	public List<Aluno> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Aluno> aluno = null;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			aluno = (List<Aluno>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}
	
	public Aluno getByMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Aluno aluno = null;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			
			aluno = (Aluno) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}
	
	public boolean isKeyConfirmation(String matricula, String keyConfirmation) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		boolean isKeyConfirmatio = false;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula"
					+ " and a.keyConfirmation = :keyConfirmation";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			query.setParameter("keyConfirmation", keyConfirmation);			
			
			Aluno aluno = (Aluno) query.uniqueResult();
			
			if (aluno != null 
					&& aluno.getKeyConfirmation().equals(keyConfirmation)) {
				isKeyConfirmatio = true;
			}			
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return isKeyConfirmatio;
	}

	@Override
	public List<Aluno> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Aluno.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Aluno.class;
	}

	@Override
	public Aluno find(Aluno entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
