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
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Edital;
import br.edu.ladoss.entity.Refeicao;

public class DiaRefeicaoDAO extends GenericDao<Integer, DiaRefeicao> {
	
	private static Logger logger = LogManager.getLogger(DiaRefeicaoDAO.class);

	private static DiaRefeicaoDAO instance;

	public static DiaRefeicaoDAO getInstance() {
		instance = new DiaRefeicaoDAO();
		return instance;
	}

	public List<DiaRefeicao> getDiaRefeicaoRealizadaByAlunoNome(String nome) {
		
		logger.info("Buscar Dia de Refeição por Nome: " + nome);
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.nome like :nome"
					+ " and dr.dia.id = :dia"
					+ " and dr.ativo = :ativo"
					+ "	and CURRENT_TIME() between dr.refeicao.horaInicio and dr.refeicao.horaFinal"
					+ " and CURRENT_TIMESTAMP() between dr.edital.dataInicial and dr.edital.dataFinal"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.refeicao.id not in ("
					+ "		select rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id"
					+ " 	from RefeicaoRealizada as rr"
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.aluno.nome like :nome"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "		and CURRENT_TIME() between rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaInicio"
					+ "			and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ ")";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("dia", dia.getId());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> getDiaRefeicaoRealizadaByAlunoMatricula(
			String matricula) {
		
		logger.info("Buscar Dia de Refeição por Matrícula: " + matricula);
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			Dia dia = DateUtil.getCurrentDayOfWeek();
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula"
					+ " and dr.dia.id = :dia"
					+ " and dr.ativo = :ativo"
					+ "	and CURRENT_TIME() between dr.refeicao.horaInicio and dr.refeicao.horaFinal"
					+ " and CURRENT_TIMESTAMP() between dr.edital.dataInicial and dr.edital.dataFinal"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.refeicao.id not in ("
					+ "		select rr.confirmaRefeicaoDia.diaRefeicao.refeicao.id"
					+ " 	from RefeicaoRealizada as rr"
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.aluno.matricula = :matricula"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "		and CURRENT_TIME() between rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaInicio"
					+ "			and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ ")";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("dia", dia.getId());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> getAllByAlunoMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula"
					+ " and dr.ativo = :ativo"
					+ " and dr.edital.ativo = :ativo"
					+ " order by dr.edital.id, dr.id, dr.dia.id asc";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> getAllVigentesByAlunoMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula"
					+ " and dr.ativo = :ativo"
					+ " and CURRENT_TIMESTAMP() <= dr.edital.dataFinal"
					+ " and dr.edital.ativo = :ativo"
					+ " order by dr.dia.id asc";			
			
			Query query = session.createQuery(hql);	
			
			query.setParameter("matricula", matricula.trim());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> getAllVigentesByEditalAluno(Integer idEdital, String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.edital.id = :idEdital"
					+ " and dr.aluno.matricula = :matricula"
					+ " and dr.ativo = :ativo"
					+ " group by dr.dia.id"
					+ " order by dr.dia.id asc";			
			
			Query query = session.createQuery(hql);	
			
			query.setParameter("idEdital", idEdital);
			query.setParameter("matricula", matricula.trim());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> getDiaRefeicaoPretensaoByAlunoMatricula(
			String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.matricula = :matricula"
					+ " and dr.ativo = :ativo"
					+ " and dr.refeicao.id not in ("
					+ "		select pr.confirmaPretensaoDia.diaRefeicao.refeicao.id"
					+ " 	from PretensaoRefeicao as pr"
					+ "		where pr.confirmaPretensaoDia.diaRefeicao.aluno.id = dr.aluno.id"
					+ "		and pr.confirmaPretensaoDia.diaRefeicao.dia.id = dr.dia.id"
					+ "		and pr.confirmaPretensaoDia.dataPretensao = CURRENT_DATE()"
					+ ")"
					+ " order by dr.dia.id asc";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	/**
	 * Quantificar a quantidade de refeições servidas para um determinado edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	public int getBeneficiadosByEdital(int idEdital) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Long quantidadeDiaRefeicao = Long.valueOf(BancoUtil.QUANTIDADE_ZERO);
		
		try {
			
			String hql = "select count(distinct dr.aluno.id)"
					+ " from DiaRefeicao as dr"
					+ " where dr.edital.id = :idEdital"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idEdital", idEdital);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			quantidadeDiaRefeicao = (Long) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return quantidadeDiaRefeicao!=null ? Integer.valueOf(
				quantidadeDiaRefeicao.toString()): BancoUtil.QUANTIDADE_ZERO;		
	}
	
	public int getBeneficiadosByEdital(int idEdital, int idDia, int idRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Long quantidadeBeneficiados = Long.valueOf(BancoUtil.QUANTIDADE_ZERO);
		
		try {
			
			String hql = "select count(distinct dr.aluno.id)"
					+ " from DiaRefeicao as dr"
					+ " where dr.edital.id = :idEdital"
					+ " and dr.dia.id = :idDia"
					+ " and dr.refeicao.id = :idRefeicao"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idEdital", idEdital);
			query.setParameter("idDia", idDia);
			query.setParameter("idRefeicao", idRefeicao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			quantidadeBeneficiados = (Long) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return quantidadeBeneficiados!=null ? Integer.valueOf(
				quantidadeBeneficiados.toString()): BancoUtil.QUANTIDADE_ZERO;		
	}

	
	/**
	 * Quantificar as refeição que serão servidas num determinado dia da semana
	 * para os editais ativos e dentro do prazo de validade.
	 *  
	 * @param diaRefeicao
	 * @return
	 */
	public int getQuantidadeDiaRefeicao(DiaRefeicao diaRefeicao, Date dataDiaRefeicao) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Long quantidadeBeneficiados = Long.valueOf(BancoUtil.QUANTIDADE_ZERO);
		
		try {
			
			String hql = "select count(distinct dr.aluno.id)"
					+ " from DiaRefeicao as dr"
					+ " where dr.dia.id = :idDia"
					+ " 	and dr.refeicao.id = :idRefeicao"
					+ "		and :dataDiaRefeicao between dr.edital.dataInicial and dr.edital.dataFinal"
					+ " 	and dr.edital.ativo = :ativo"
					+ " 	and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idDia", diaRefeicao.getDia().getId());
			query.setParameter("idRefeicao", diaRefeicao.getRefeicao().getId());
			query.setParameter("dataDiaRefeicao", dataDiaRefeicao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			quantidadeBeneficiados = (Long) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return quantidadeBeneficiados!=null ? Integer.valueOf(
				quantidadeBeneficiados.toString()): BancoUtil.QUANTIDADE_ZERO;		
	}
	
	public List<DiaRefeicao> getDiaRefeicaoByPeriodoEdital(DiaRefeicao diaRefeicao){
		
		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.id = :idAluno"
					+ " and dr.dia.id = :idDia"
					+ " and dr.refeicao.id = :idRefeicao"
					+ " and dr.ativo = :ativo"
					+ " and dr.edital.id in ("
					+ "		select ed.id"
					+ " 	from Edital as ed"
					+ "		where ed.ativo = :ativo"
					+ "		and (ed.id = :idEdital"
					+ "		or (:dataInicial between ed.dataInicial and ed.dataFinal and ed.dataFinal >= CURRENT_TIMESTAMP)"
					+ "		or :dataFinal between ed.dataInicial and ed.dataFinal"
					+ "		or (ed.dataInicial between :dataInicial and :dataFinal"
					+ "				and ed.dataFinal between :dataInicial and :dataFinal"
					+ "				and ed.dataFinal >= CURRENT_TIMESTAMP)"
					+ "		)"					
					+ " )"
					+ " order by dr.dataInsercao DESC";
			
			Aluno aluno = diaRefeicao.getAluno();
			Dia dia = diaRefeicao.getDia();
			Refeicao refeicao = diaRefeicao.getRefeicao();
			Edital edital = diaRefeicao.getEdital();
			
			Query query = session.createQuery(hql);			
			query.setParameter("idAluno", aluno.getId());
			query.setParameter("idDia", dia.getId());
			query.setParameter("idRefeicao", refeicao.getId());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			// Verificar editais vigentes no período do edital que irá conceder o benefício.			
			query.setParameter("idEdital", edital.getId());
			query.setParameter("dataInicial", edital.getDataInicial());
			query.setParameter("dataFinal", edital.getDataFinal());			
			
			diasRefeicao = (List<DiaRefeicao>) query.list();			
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	/**
	 * Encontrar Dia de Refeição.
	 * 
	 */
	@Override
	public DiaRefeicao find(DiaRefeicao diaRefeicao) throws SQLExceptionNutrIF {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			Aluno aluno = diaRefeicao.getAluno();
			String matricula = StringUtil.isEmptyOrNull(aluno.getMatricula()) 
					? StringUtil.STRING_VAZIO : aluno.getMatricula();
			
			String hql = "from DiaRefeicao as dr"
					+ " where (dr.aluno.id = :idAluno"
					+ " OR dr.aluno.matricula = :matricula)"
					+ " and dr.dia.id = :idDia"
					+ " and dr.refeicao.id = :idRefeicao"
					+ " and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("idAluno", diaRefeicao.getAluno().getId());
			query.setParameter("matricula", matricula);
			query.setParameter("idDia", diaRefeicao.getDia().getId());
			query.setParameter("idRefeicao", diaRefeicao.getRefeicao().getId());
			query.setParameter("ativo", BancoUtil.ATIVO);			
			
			diaRefeicao = (DiaRefeicao) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diaRefeicao;
	}
	
	public boolean isAlunoContemplado(int idEdital, String matricula) {
		
		boolean contemplado = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {			
			
			String hql = "select case when (count(dr) > 0) then true else false end " 
					+ " from DiaRefeicao as dr"
					+ " where dr.edital.id = :idEdital"
					+ " and dr.aluno.matricula = :matricula"
					+ " and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql, Boolean.class);
			query.setParameter("idEdital", idEdital);
			query.setParameter("matricula", matricula);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			contemplado = (Boolean) query.getSingleResult();;
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return contemplado;
	}
	
	public List<DiaRefeicao> listDiaRefeicaoByDia(Integer dia) {
		
		logger.info("Buscar Dia de Refeição por dia.");
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {			
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.dia.id = :idDia"
					+ " and CURRENT_TIMESTAMP() between dr.edital.dataInicial and dr.edital.dataFinal"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idDia", dia);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	public List<DiaRefeicao> listDiaRefeicaoByDiaAndRefeicao(Integer idDia, Integer idRefeicao) {
		
		logger.info("Buscar Dia de Refeição por Dia e Refeição.");
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();
		
		try {			
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.dia.id = :idDia"
					+ " and dr.refeicao.id = :idRefeicao"
					+ " and CURRENT_TIMESTAMP() between dr.edital.dataInicial and dr.edital.dataFinal"
					+ " and dr.edital.ativo = :ativo"
					+ " and dr.ativo = :ativo"
					+ " order by dr.edital.id, dr.aluno.nome";
			
			Query query = session.createQuery(hql);			
			query.setParameter("idDia", idDia);
			query.setParameter("idRefeicao", idRefeicao);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diasRefeicao = (List<DiaRefeicao>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return diasRefeicao;		
	}
	
	/**
	 * Listar Dia de Refeições para um determinado edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	public List<DiaRefeicao> listDiaRefeicaoByEdital(Integer idEdital) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<DiaRefeicao> diasRefeicao = new ArrayList<DiaRefeicao>();

		try {

			String hql = "select distinct dr.aluno.id"
					+ " from DiaRefeicao as dr"
					+ " where dr.edital.id = :idEdital"
					+ " and dr.ativo = :ativo";

			Query query = session.createQuery(hql);
			query.setParameter("idEdital", idEdital);
			query.setParameter("ativo", BancoUtil.ATIVO);

			diasRefeicao = (List<DiaRefeicao>) query.list();

		} catch (HibernateException hibernateException) {

			session.getTransaction().rollback();

			throw new SQLExceptionNutrIF(hibernateException);

		} finally {

			session.close();
		}

		return diasRefeicao;
	}
	
	@Override
	public void delete(DiaRefeicao entity) {
		// TODO Auto-generated method stub
		super.delete(entity);
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