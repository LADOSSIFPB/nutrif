package br.edu.ifpb.nutrif.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.MapaPretensaoRefeicao;
import br.edu.ladoss.entity.PretensaoRefeicao;
import br.edu.ladoss.entity.Refeicao;

public class PretensaoRefeicaoDAO extends GenericDao<Integer, PretensaoRefeicao>{
	
	private static Logger logger = LogManager.getLogger(PretensaoRefeicaoDAO.class);
	
	private static PretensaoRefeicaoDAO instance;
	
	public static PretensaoRefeicaoDAO getInstance() {
		instance = new PretensaoRefeicaoDAO();
		return instance;
	}
	
	@Override
	public int insert(PretensaoRefeicao pretensaoRefeicao) throws SQLExceptionNutrIF {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Integer idPretensaoRefeicao = null;
		
		try {
			
			session.beginTransaction();
			
			String sql = "INSERT INTO tb_pretensao_refeicao(dt_pretensao,"
					+ " dt_solicitacao, fk_id_dia_refeicao, nm_keyaccess)"
					+ " VALUES(:dataPretensao, CURRENT_TIMESTAMP(), :diaRefeicao, :keyAccess)";
			
			Query query = session.createSQLQuery(sql);
			query.setParameter("dataPretensao", pretensaoRefeicao
					.getConfirmaPretensaoDia().getDataPretensao());
			query.setParameter("diaRefeicao", pretensaoRefeicao
					.getConfirmaPretensaoDia().getDiaRefeicao().getId());
			query.setParameter("keyAccess", pretensaoRefeicao.getKeyAccess());
			
			idPretensaoRefeicao = query.executeUpdate();	
			
			session.getTransaction().commit();
			
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return idPretensaoRefeicao;
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
	
	/**
	 * Consultar pretensão da refeição através do dia da refeição e da data da
	 * intensão da refeição.
	 * 
	 * @param idDiaRefeicao
	 * @return
	 */
	public PretensaoRefeicao getPretensaoRefeicaoByDiaRefeicao(int idDiaRefeicao,
			Date dataPretensao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		PretensaoRefeicao pretensaoRefeicao = null;
		
		try {
			
			String hql = "from PretensaoRefeicao as pr"
					+ " where pr.confirmaPretensaoDia.diaRefeicao.id = :idDiaRefeicao"
					+ " and pr.confirmaPretensaoDia.dataPretensao = :dataPretensao";
			
			Query query = session.createQuery(hql);
			query.setParameter("idDiaRefeicao", idDiaRefeicao);
			query.setParameter("dataPretensao", dataPretensao);
			
			pretensaoRefeicao = (PretensaoRefeicao) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return pretensaoRefeicao;		
	}
	
	public List<PretensaoRefeicao> getMapaPretensaoRefeicao(
			MapaPretensaoRefeicao mapaPretensaoRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<PretensaoRefeicao> refeicoesRealizadas = new ArrayList<PretensaoRefeicao>();
		
		try {
			
			Dia dia = mapaPretensaoRefeicao.getDia();
			Refeicao refeicao = mapaPretensaoRefeicao.getRefeicao();
			Date dataPretensao = mapaPretensaoRefeicao.getDataPretensao();
			
			String hql = "from PretensaoRefeicao as pr"
					+ " where pr.confirmaPretensaoDia.diaRefeicao.dia.id = :dia"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.refeicao.id = :refeicao"
					+ " and pr.confirmaPretensaoDia.dataPretensao = :dataPretensao";
			
			Query query = session.createQuery(hql);
			query.setParameter("dia", dia.getId());
			query.setParameter("refeicao", refeicao.getId());
			query.setParameter("dataPretensao", dataPretensao);
			
			refeicoesRealizadas = (List<PretensaoRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;		
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
