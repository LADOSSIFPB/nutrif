package br.edu.ifpb.nutrif.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.hibernate.QueryUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Matricula;

public class MatriculaDAO extends GenericDao<Integer, Matricula> {

	private static Logger logger = LogManager.getLogger(MatriculaDAO.class);
	
	private static MatriculaDAO instance;
	
	public static MatriculaDAO getInstance() {		
		instance = new MatriculaDAO();		
		return instance;
	}

	@Override
	public List<Matricula> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Matricula.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Aluno.class;
	}

	@Override
	public Matricula find(Matricula entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Recuperar a Matricula através do número.
	 * 
	 * @param numero
	 * @return
	 */
	public Matricula getByNumero(String numero) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Matricula matricula = null;
		
		try {
			
			String hql = "from Matricula as m"
					+ " where m.numero = :numero";
			
			Query query = session.createQuery(hql);
			query.setParameter("numero", numero);
			
			QueryUtil<Matricula> queryUtil = new QueryUtil<Matricula>();
			matricula = queryUtil.getUniqueResult(query);
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return matricula;
	}

	public List<Matricula> getByAlunoId(Integer id) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Matricula> matriculas = null;
		
		try {
			
			String hql = "from Matricula as m"
					+ " where m.aluno.id = :idAluno";
			
			Query query = session.createQuery(hql, Matricula.class);
			query.setParameter("idAluno", id);
			
			matriculas = (List<Matricula>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return matriculas;
	}
}
