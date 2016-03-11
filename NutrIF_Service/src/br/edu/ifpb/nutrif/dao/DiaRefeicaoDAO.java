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
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Dia;

public class DiaRefeicaoDAO extends GenericDao<Integer, DiaRefeicao> {
	
	private static Logger logger = LogManager.getLogger(DiaRefeicaoDAO.class);

	private static DiaRefeicaoDAO instance;

	public static DiaRefeicaoDAO getInstance() {
		instance = new DiaRefeicaoDAO();
		return instance;
	}

	public List<DiaRefeicao> getCronogramaRefeicaoByAluno(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> cronogramasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from DiaRefeicao"
					+ " where aluno.nome like :nome"
					+ " and dia.id = :dia"
					+ " and refeicao.id not in ("
					+ " 	from RefeicaoRealizada ..."
					+ ")";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("dia", dia.getId());
			
			cronogramasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			
		} finally {
		
			session.close();
		}
		
		return cronogramasRefeicao;		
	}
	
	@Override
	public List<DiaRefeicao> getAll() throws SQLExceptionNutrIF {
		return super.getAll("DiaRefeicao.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return DiaRefeicao.class;
	}
}
