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

	public List<DiaRefeicao> getDiaRefeicaoByAlunoNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> cronogramasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.nome like :nome"
					+ " and dr.dia.id = :dia"
					+ "	and dr.refeicao.horaInicio <= CURRENT_TIME()"
					+ "	and dr.refeicao.horaFinal >= CURRENT_TIME()"
					+ " and dr.refeicao.id not in ("
					+ "		select rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id"
					+ " 	from RefeicaoRealizada as rr"
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ "			>= CURRENT_TIME()"
					+ ")";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("dia", dia.getId());
			
			cronogramasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException exception) {
			
			logger.error(exception.getMessage());
			session.getTransaction().rollback();
			
			throw exception;
			
		} finally {
		
			session.close();
		}
		
		return cronogramasRefeicao;		
	}
	
	public List<DiaRefeicao> getDiaRefeicaoByAlunoMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> cronogramasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula"
					+ " and dr.dia.id = :dia"
					+ "	and dr.refeicao.horaInicio <= CURRENT_TIME()"
					+ "	and dr.refeicao.horaFinal >= CURRENT_TIME()"
					+ " and dr.refeicao.id not in ("
					+ "		select rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id"
					+ " 	from RefeicaoRealizada as rr"
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ "			>= CURRENT_TIME()"
					+ ")";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("dia", dia.getId());
			
			cronogramasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException exception) {
			
			logger.error(exception.getMessage());
			session.getTransaction().rollback();
			
			throw exception;
			
		} finally {
		
			session.close();
		}
		
		return cronogramasRefeicao;		
	}
	
	public List<DiaRefeicao> getAllByAlunoMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException exception) {
			
			logger.error(exception.getMessage());
			session.getTransaction().rollback();
			
			throw exception;
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
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
