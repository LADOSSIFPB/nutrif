package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Curso;

public class CursoDAO extends GenericDao<Integer, Curso>{
	
	private static Logger logger = LogManager.getLogger(CursoDAO.class);
	
	private static CursoDAO instance;
	
	public CursoDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static CursoDAO getInstance() {
		instance = new CursoDAO();
		return instance;
	}

	public List<Curso> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		List<Curso> cursos = null;
		
		try {
			
			String hql = "from Curso as c"
					+ " where c.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			cursos = (List<Curso>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return cursos;
	}

	@Override
	public List<Curso> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Curso.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Curso.class;
	}

	@Override
	public Curso find(Curso entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
