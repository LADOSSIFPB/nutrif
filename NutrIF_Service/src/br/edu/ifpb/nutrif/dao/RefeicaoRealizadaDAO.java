package br.edu.ifpb.nutrif.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.Refeicao;
import br.edu.ladoss.entity.RefeicaoRealizada;

public class RefeicaoRealizadaDAO extends GenericDao<Integer, RefeicaoRealizada> {
	
	private static Logger logger = LogManager.getLogger(RefeicaoRealizadaDAO.class);

	private static RefeicaoRealizadaDAO instance;

	public static RefeicaoRealizadaDAO getInstance() {
		instance = new RefeicaoRealizadaDAO();
		return instance;
	}

	@Override
	public int insert(RefeicaoRealizada refeicaoRealizada) throws SQLExceptionNutrIF {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Integer id = null;
		
		try {
			
			session.beginTransaction();
			
			String sql = "INSERT INTO tb_refeicao_realizada (dt_refeicao, hr_refeicao, fk_id_dia_refeicao)"
					+ " VALUES(CURRENT_DATE(), CURRENT_TIME(), :diaRefeicao)";
			
			Query query = session.createSQLQuery(sql);
			query.setParameter("diaRefeicao", refeicaoRealizada
					.getConfirmaRefeicaoDia().getDiaRefeicao().getId());
			
			id = query.executeUpdate();	
			
			session.getTransaction().commit();
			
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return id;
	}
	
	public List<RefeicaoRealizada> getMapaRefeicoesRealizadas(
			Refeicao refeicao, Date dataRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<RefeicaoRealizada> refeicoesRealizadas = new ArrayList<RefeicaoRealizada>();
		
		try {
			
			String hql = "from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id = :refeicao"
					+ " and rr.confirmaRefeicaoDia.dataRefeicao = :dataRefeicao";
			
			Query query = session.createQuery(hql);
			query.setParameter("refeicao", refeicao.getId());
			query.setParameter("dataRefeicao", dataRefeicao);
			
			refeicoesRealizadas = (List<RefeicaoRealizada>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;		
	}
	
	public RefeicaoRealizada getRefeicaoRealizadaCorrente(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		RefeicaoRealizada refeicaoRealizada = null;
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from RefeicaoRealizada as rr"
					+ "	where rr.confirmaRefeicaoDia.diaRefeicao.aluno.matricula = :matricula"
					+ "	and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "	and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "	and CURRENT_TIME() between rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaInicio"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			query.setParameter("dia", dia.getId());
			
			refeicaoRealizada = (RefeicaoRealizada) query.getSingleResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicaoRealizada;		
	}
	
	/**
	 * Quantificar as refeições realizadas para uma data definida.
	 * 
	 * @param data
	 * @return
	 */
	public Long getQuantidadeDiaRefeicaoRealizada(Refeicao refeicao, Date data) {
		
		Long quantidadeDia = Long.valueOf(BancoUtil.QUANTIDADE_ZERO);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {	
			
			String hql = "select count(rr.id)"
					+ " from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.dataRefeicao = :data"
					+ " and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id = :idRefeicao";
			
			Query query = session.createQuery(hql);
			query.setParameter("data", data);
			query.setParameter("idRefeicao", refeicao.getId());
			
			quantidadeDia = (Long) query.getSingleResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return quantidadeDia;
	}
	
	public List<RefeicaoRealizada> detalharRefeicoesRealizadasByEditalAluno(Integer idEdital, String matricula,
			Integer idDia) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<RefeicaoRealizada> refeicoesRealizadas = new ArrayList<RefeicaoRealizada>();
		
		try {
			
			String hql = "from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.diaRefeicao.edital.id = :idEdital"
					+ " and rr.confirmaRefeicaoDia.diaRefeicao.aluno.matricula = :matricula"
					+ " and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :idDia"
					+ " order by rr.confirmaRefeicaoDia.dataRefeicao desc";
			
			Query query = session.createQuery(hql);
			query.setParameter("idEdital", idEdital);
			query.setParameter("matricula", matricula);
			query.setParameter("idDia", idDia);
			
			refeicoesRealizadas = (List<RefeicaoRealizada>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;		
	}
	
	public List<RefeicaoRealizada> listDiaRefeicaoByDataRefeicao(Date dataRefeicao, 
			Integer idDia, Integer idRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<RefeicaoRealizada> refeicoesRealizadas = new ArrayList<RefeicaoRealizada>();
		
		try {
				
			String hql = " from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :idDia"
					+ " and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id = :idRefeicao"
					+ " and rr.confirmaRefeicaoDia.dataRefeicao = :dataRefeicao";
		
			Query query = session.createQuery(hql);
			query.setParameter("idDia", idDia);
			query.setParameter("idRefeicao", idRefeicao);
			query.setParameter("dataRefeicao", dataRefeicao);
		
			refeicoesRealizadas = (List<RefeicaoRealizada>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;
	}
	
	public List<RefeicaoRealizada> listByDiaRefeicaoInData(Integer idDiaRefeicao, List<Date> datasRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<RefeicaoRealizada> refeicoesRealizadas = new ArrayList<RefeicaoRealizada>();
		
		try {
					
			String hql = "from RefeicaoRealizada as rr"
					+ "	where rr.confirmaRefeicaoDia.diaRefeicao.id = :idDiaRefeicao"
					+ " and rr.confirmaRefeicaoDia.dataRefeicao in ("
					+ " 	:datasRefeicao"
					+ " )";
		
			Query query = session.createQuery(hql);
			query.setParameter("idDiaRefeicao", idDiaRefeicao);
			query.setParameter("datasRefeicao", datasRefeicao); //TODO: Verificar consulta.
		
			refeicoesRealizadas = (List<RefeicaoRealizada>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;
	}
	
	public boolean isRefeicaoRealizada(int idDiaRefeicao, Date dataRefeicao) {
		
		boolean refecaoRealizada = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			
			String hql = "select case when (count(rr) > 0) then true else false end " 
					+ " from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.diaRefeicao.id = :idDiaRefeicao"
					+ " and rr.confirmaRefeicaoDia.dataRefeicao = :dataRefeicao";
			
			Query query = session.createQuery(hql, Boolean.class);
			query.setParameter("idDiaRefeicao", idDiaRefeicao);
			query.setParameter("dataRefeicao", dataRefeicao);
			
			refecaoRealizada = (Boolean) query.getSingleResult();;
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refecaoRealizada;
	}
	
	@Override
	public List<RefeicaoRealizada> getAll() throws SQLExceptionNutrIF {
		return super.getAll("RefeicaoRealizada.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return RefeicaoRealizada.class;
	}

	@Override
	public RefeicaoRealizada find(RefeicaoRealizada entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
