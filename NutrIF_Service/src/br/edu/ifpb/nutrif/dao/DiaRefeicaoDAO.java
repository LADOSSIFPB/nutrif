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
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.DateUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Dia;
import br.edu.ladoss.entity.DiaRefeicao;
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
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.aluno.id = dr.aluno.id"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ "			>= CURRENT_TIME()"
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
					+ "		where rr.confirmaRefeicaoDia.diaRefeicao.aluno.id = dr.aluno.id"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.dia.id = :dia"
					+ "		and rr.confirmaRefeicaoDia.dataRefeicao = CURRENT_DATE()"
					+ "		and rr.confirmaRefeicaoDia.diaRefeicao.refeicao.horaFinal"
					+ "			>= CURRENT_TIME()"
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
	
	public boolean isDiaRefeicaoAtivo(DiaRefeicao diaRefeicao){
		
		boolean isAtivo = false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			
			String hql = "from DiaRefeicao as dr"
					+ " where dr.aluno.id = :idAluno"
					+ " and dr.dia.id = :idDia"
					+ " and dr.refeicao.id = :idRefeicao"
					+ " and dr.ativo = :ativo"
					+ " order by dr.dataInsercao DESC";
			
			Aluno aluno = diaRefeicao.getAluno();
			Dia dia = diaRefeicao.getDia();
			Refeicao refeicao = diaRefeicao.getRefeicao();
			
			Query query = session.createQuery(hql);			
			query.setParameter("idAluno", aluno.getId());
			query.setParameter("idDia", dia.getId());
			query.setParameter("idRefeicao", refeicao.getId());
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			diaRefeicao = (DiaRefeicao) query.uniqueResult();
			
			if (diaRefeicao != null) {
				
				isAtivo = diaRefeicao.isAtivo();
			}
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return isAtivo;		
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
	
	@Override
	public Class<?> getEntityClass() {
		return DiaRefeicao.class;
	}
}
