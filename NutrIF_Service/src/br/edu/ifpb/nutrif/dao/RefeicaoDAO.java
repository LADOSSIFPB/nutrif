package br.edu.ifpb.nutrif.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Refeicao;

public class RefeicaoDAO extends GenericDao<Integer, Refeicao>{
	
	private static Logger logger = LogManager.getLogger(RefeicaoDAO.class);
	
	private static RefeicaoDAO instance;
	
	public static RefeicaoDAO getInstance() {
		instance = new RefeicaoDAO();
		return instance;
	}
	
	/**
	 * Verificar a refeição de acordo com o período do dia.
	 * 
	 * @return
	 */
	public boolean isPeriodoRefeicao() {
		
		boolean isPeriodo = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			String hql = "select count(r.id)"
					+ " from Refeicao as r"
					+ " where CURRENT_TIME() between r.horaInicio and r.horaFinal"
					+ " and r.ativo = :ativo";
			
			Query query = session.createQuery(hql);	
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			Long quantidadeBeneficiados = (Long) query.getSingleResult();
			
			isPeriodo = quantidadeBeneficiados != BancoUtil.QUANTIDADE_ZERO ?
					BancoUtil.VALIDO: BancoUtil.INVALIDO;
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return isPeriodo;
	}
	
	public List<Refeicao> listByTipo(String tipo) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Refeicao> refeicoes = null;
		
		try {
			
			String hql = "from Refeicao as r"
					+ " where r.tipo like :tipo";
			
			Query query = session.createQuery(hql, Refeicao.class);
			query.setParameter("tipo", "%" + tipo + "%");			
	        
			refeicoes = (List<Refeicao>) query.getResultList();
			
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoes;
	}

	@Override
	public List<Refeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Refeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Refeicao.class;
	}

	@Override
	public Refeicao find(Refeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
