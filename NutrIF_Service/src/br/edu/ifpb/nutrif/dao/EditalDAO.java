package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Edital;

public class EditalDAO extends GenericDao<Integer, Edital>{
	
	private static Logger logger = LogManager.getLogger(EditalDAO.class);
	
	private static EditalDAO instance;
	
	public static EditalDAO getInstance() {
		instance = new EditalDAO();
		return instance;
	}

	public List<Edital> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Edital> editais = null;
		
		try {
			
			String hql = "from Edital as e"
					+ " where e.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			editais = (List<Edital>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return editais;
	}

	public List<Edital> listVigentes() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Edital> editais = null;
		
		try {
			
			String hql = "from Edital as e"
					+ " where CURRENT_TIMESTAMP() between e.dataInicial and e.dataFinal"
					+ " and e.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			editais = (List<Edital>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return editais;
	}
	
	@Override
	public List<Edital> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Edital.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Edital.class;
	}

	@Override
	public Edital find(Edital entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
