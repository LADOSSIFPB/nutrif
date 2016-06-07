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
import br.edu.ladoss.entity.MapaRefeicaoRealizada;
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
	
	public List<RefeicaoRealizada> getMapaRefeicoesRaelizadas(
			MapaRefeicaoRealizada mapaRefeicoesRealizadas) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<RefeicaoRealizada> refeicoesRealizadas = new ArrayList<RefeicaoRealizada>();
		
		try {
			
			Dia dia = mapaRefeicoesRealizadas.getDia();
			Refeicao refeicao = mapaRefeicoesRealizadas.getRefeicao();
			Date dataRefeicao = mapaRefeicoesRealizadas.getDataRefeicao();
			
			String hql = "from RefeicaoRealizada as rr"
					+ " where rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ " and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id = :refeicao"
					+ " and rr.confirmaRefeicaoDia.dataRefeicao = :dataRefeicao";
			
			Query query = session.createQuery(hql);
			query.setParameter("dia", dia.getId());
			query.setParameter("refeicao", refeicao.getId());
			query.setParameter("dataRefeicao", dataRefeicao);
			
			refeicoesRealizadas = (List<RefeicaoRealizada>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return refeicoesRealizadas;		
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
