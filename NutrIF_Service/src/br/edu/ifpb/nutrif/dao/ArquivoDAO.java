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
import br.edu.ladoss.entity.Arquivo;

public class ArquivoDAO extends GenericDao<Integer, Arquivo> {

	private static Logger logger = LogManager.getLogger(ArquivoDAO.class);
	
	private static ArquivoDAO instance;
	
	public static ArquivoDAO getInstance() {		
		instance = new ArquivoDAO();		
		return instance;
	}
	
	public Arquivo getByNomeReal(String nomeRealArquivo) {
		
		Arquivo arquivo = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			String hql = "from Arquivo as a"
					+ " where a.nomeRealArquivo = :nomeRealArquivo";
			
			Query query = session.createQuery(hql);
			query.setParameter("nomeRealArquivo", nomeRealArquivo);
			
			arquivo = (Arquivo) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return arquivo;		
	}
	
	@Override
	public List<Arquivo> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Arquivo.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Arquivo.class;
	}

	@Override
	public Arquivo find(Arquivo entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
