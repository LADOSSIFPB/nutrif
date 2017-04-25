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
import br.edu.ifpb.nutrif.util.BancoUtil;
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
					+ " where pr.keyAccess = :keyAccess"
					+ " and pr.confirmaPretensaoDia.dataPretensao = CURRENT_DATE()"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.refeicao.id not in ("
					+ "		select rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id"
					+ " 	from RefeicaoRealizada as rr"
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.aluno.id"
					+ "			 = pr.confirmaPretensaoDia.diaRefeicao.aluno.id"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.dia.id"
					+ "			 = pr.confirmaPretensaoDia.diaRefeicao.dia.id"
					+ "		and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ "			>= CURRENT_TIME()"
					+ ")";
			
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
		//TODO: Analisar código repetido.
		Session session = HibernateUtil.getSessionFactory().openSession();

		PretensaoRefeicao pretensaoRefeicao = null;
		
		try {
			
			String hql = "from PretensaoRefeicao as pr"
					+ " where pr.confirmaPretensaoDia.diaRefeicao.id = :idDiaRefeicao"
					+ " and pr.confirmaPretensaoDia.dataPretensao = :dataPretensao"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.ativo = :ativo"
					+ " and pr.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("idDiaRefeicao", idDiaRefeicao);
			query.setParameter("dataPretensao", dataPretensao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
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
	 * Quantificar as pretensões das refeições para uma data definida.
	 * 
	 * @param data
	 * @return
	 */
	public Long getQuantidadeDiaPretensaoRefeicao(PretensaoRefeicao pretensaoRefeicao) {
		
		Long quantidadeDia = Long.valueOf(BancoUtil.QUANTIDADE_ZERO);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			Date dataPretensao = pretensaoRefeicao.getConfirmaPretensaoDia()
					.getDataPretensao();
			int idRefeicao = pretensaoRefeicao.getConfirmaPretensaoDia()
					.getDiaRefeicao().getRefeicao().getId();
			
			String hql = "select count(pr.id)"
					+ " from PretensaoRefeicao as pr"
					+ " where pr.confirmaPretensaoDia.dataPretensao = :dataPretensao"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.refeicao.id = :idRefeicao"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.ativo = :ativo"
					+ " and pr.ativo = :ativo";
						
			Query query = session.createQuery(hql);
			query.setParameter("dataPretensao", dataPretensao);
			query.setParameter("idRefeicao", idRefeicao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			quantidadeDia = (Long) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return quantidadeDia;
	}
	
	public List<PretensaoRefeicao> getMapaPretensaoRefeicao(Refeicao refeicao, 
			Date dataPretensao) {
		//TODO: Verificar duplicidade.
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<PretensaoRefeicao> pretensoesRefeicao = new ArrayList<PretensaoRefeicao>();
		
		try {
			
			String hql = "from PretensaoRefeicao as pr"
					+ " where pr.confirmaPretensaoDia.diaRefeicao.refeicao.id = :refeicao"
					+ " and pr.confirmaPretensaoDia.dataPretensao = :dataPretensao"
					+ " and pr.confirmaPretensaoDia.diaRefeicao.ativo = :ativo"
					+ " and pr.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("refeicao", refeicao.getId());
			query.setParameter("dataPretensao", dataPretensao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			pretensoesRefeicao = (List<PretensaoRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return pretensoesRefeicao;		
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
