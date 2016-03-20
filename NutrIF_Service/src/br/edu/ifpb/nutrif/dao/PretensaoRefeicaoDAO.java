package br.edu.ifpb.nutrif.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.PretensaoRefeicao;

public class PretensaoRefeicaoDAO extends GenericDao<Integer, PretensaoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(PretensaoRefeicaoDAO.class);
	
	private static PretensaoRefeicaoDAO instance;
	
	public static PretensaoRefeicaoDAO getInstance() {
		instance = new PretensaoRefeicaoDAO();
		return instance;
	}
	
	@Override
	public List<PretensaoRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("PretensaoRefeicao.getAll");
	}
	
	public PretensaoRefeicao getPretensaoRefeicaoByKeyAccess(String keyAccess) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		PretensaoRefeicao pretensaoRefeicao = null;
		
		try {
			
			String hql = "from PretensaoRefeicao as pr"
					+ " where pr.keyAccess = :keyAccess";
			
			Query query = session.createQuery(hql);
			query.setParameter("keyAccess", keyAccess);
			
			pretensaoRefeicao = (PretensaoRefeicao) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return pretensaoRefeicao;		
	}

	@Override
	public Class<?> getEntityClass() {
		return PretensaoRefeicao.class;
	}

	@Override
	public PretensaoRefeicao find(PretensaoRefeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
