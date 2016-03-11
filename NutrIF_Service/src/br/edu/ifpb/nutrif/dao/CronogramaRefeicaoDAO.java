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
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ladoss.entity.CronogramaRefeicao;
import br.edu.ladoss.entity.Dia;

public class CronogramaRefeicaoDAO extends GenericDao<Integer, CronogramaRefeicao> {
	
	private static Logger logger = LogManager.getLogger(CronogramaRefeicaoDAO.class);

	private static CronogramaRefeicaoDAO instance;

	public static CronogramaRefeicaoDAO getInstance() {
		instance = new CronogramaRefeicaoDAO();
		return instance;
	}

	public List<CronogramaRefeicao> getCronogramaRefeicaoByAluno(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<CronogramaRefeicao> cronogramasRefeicao = new ArrayList<CronogramaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from CronogramaRefeicao"
					+ " where aluno.nome like :nome"
					+ " and dia.id = :dia";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("dia", dia.getId());
			
			cronogramasRefeicao = (List<CronogramaRefeicao>) query.list();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return cronogramasRefeicao;		
	}
	
	@Override
	public List<CronogramaRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("CronogramaRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return CronogramaRefeicao.class;
	}
}
