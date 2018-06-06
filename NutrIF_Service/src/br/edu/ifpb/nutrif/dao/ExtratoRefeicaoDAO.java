package br.edu.ifpb.nutrif.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.ExtratoRefeicao;

public class ExtratoRefeicaoDAO extends GenericDao<Integer, ExtratoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(ExtratoRefeicaoDAO.class);
	
	private static ExtratoRefeicaoDAO instance;
	
	public static ExtratoRefeicaoDAO getInstance() {
		instance = new ExtratoRefeicaoDAO();
		return instance;
	}
	
	public List<ExtratoRefeicao> listByPeriodo(Date dataInicial, Date dataFinal) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<ExtratoRefeicao> extratosRefeicoes = null;
		
		try {			
			
			Criteria criteria = session.createCriteria(ExtratoRefeicao.class);
			criteria.add(Restrictions.ge("dataExtrato", dataInicial)); 
			criteria.add(Restrictions.le("dataExtrato", dataFinal));			
			
			extratosRefeicoes = (List<ExtratoRefeicao>) criteria.list();
			
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return extratosRefeicoes;
	}

	@Override
	public List<ExtratoRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("ExtratoRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return ExtratoRefeicao.class;
	}

	@Override
	public ExtratoRefeicao find(ExtratoRefeicao entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
