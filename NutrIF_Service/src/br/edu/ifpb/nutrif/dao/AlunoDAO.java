package br.edu.ifpb.nutrif.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.hibernate.QueryUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.Pessoa;

public class AlunoDAO extends GenericDao<Integer, Aluno> {

	private static Logger logger = LogManager.getLogger(AlunoDAO.class);
	
	private static AlunoDAO instance;
	
	public static AlunoDAO getInstance() {		
		instance = new AlunoDAO();		
		return instance;
	}

	public List<Aluno> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Aluno> alunos = null;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.nome like :nome";
			
			Query query = session.createQuery(hql, Aluno.class);
			query.setParameter("nome", "%" + nome + "%");
			
			alunos = (List<Aluno>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return alunos;
	}
	
	/**
	 * Login do Aluno através do número da Matrícula e senha.
	 * 
	 * @param matricula
	 * @param senhaPlana
	 * @return funcionario
	 * @throws UnsupportedEncodingException
	 */
	public Pessoa login(String matricula, String senhaPlana) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Pessoa pessoa = null;
		
		try {
			
			String senhaCriptografada = StringUtil.criptografarBase64(
					senhaPlana);
			
			logger.info("Verificando o login pela matrícula do aluno: " 
					+ matricula);			
			
			String hql = "from Aluno as al"
					+ " where al.senha = :senha"
					+ " and al.id in (" 
					+ " 	select mat.aluno.id"
					+ " 	from Matricula as mat"
					+ " 	where mat.numero = :matricula"
					+ " )";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("senha", senhaCriptografada);
			
			QueryUtil<Pessoa> queryUtil = new QueryUtil<Pessoa>();
			pessoa = queryUtil.getUniqueResult(query);
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return pessoa;
	}
	
	/**
	 * Recuperar o Aluno através do número da Matrícula.
	 * 
	 * @param matricula
	 * @return aluno
	 */
	public Aluno getByMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Aluno aluno = null;
		
		try {
			
			String hql = "from Aluno as al"
					+ " where al.id in (" 
					+ " 	select mat.aluno.id"
					+ " 	from Matricula as mat"
					+ " 	where mat.numero = :matricula"
					+ " )";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			
			QueryUtil<Aluno> queryUtil = new QueryUtil<Aluno>();
			aluno = queryUtil.getUniqueResult(query);
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}
	
	public boolean isKeyConfirmation(String matricula, String keyConfirmation) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		boolean isKeyConfirmatio = false;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula"
					+ " and a.keyConfirmation = :keyConfirmation";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			query.setParameter("keyConfirmation", keyConfirmation);			
			
			Aluno aluno = (Aluno) query.getSingleResult();
			
			if (aluno != null 
					&& aluno.getKeyConfirmation().equals(keyConfirmation)) {
				isKeyConfirmatio = true;
			}			
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return isKeyConfirmatio;
	}
	
	/**
	 * Listar Alunos Contemplados num determinado Edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	public List<Aluno> listAlunoByEdital(Integer idEdital) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Aluno> alunos = new ArrayList<Aluno>();

		try {
			
			String hql = "from Aluno as al"
					+ " where al.id in (" 
					+ " 	select distinct dr.aluno.id"
					+ " 	from DiaRefeicao as dr"
					+ " 	where dr.edital.id = :idEdital"
					+ " 	and dr.ativo = :ativo"
					+ " )"
					+ " order by al.nome ASC";

			Query query = session.createQuery(hql, Aluno.class);
			query.setParameter("idEdital", idEdital);
			query.setParameter("ativo", BancoUtil.ATIVO);

			alunos = query.getResultList();

		} catch (HibernateException hibernateException) {

			session.getTransaction().rollback();

			throw new SQLExceptionNutrIF(hibernateException);

		} finally {

			session.close();
		}

		return alunos;
	}
	
	/**
	 * Listar Alunos Contemplados num determinado Edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	public List<Aluno> listAlunoByNomeEdital(String nome, Integer idEdital) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Aluno> alunos = new ArrayList<Aluno>();

		try {
			
			String hql = "from Aluno as al"
					+ " where al.nome like :nome"
					+ " and al.id in (" 
					+ " 	select distinct dr.aluno.id"
					+ " 	from DiaRefeicao as dr"
					+ " 	where dr.edital.id = :idEdital"
					+ " 	and dr.ativo = :ativo"
					+ " )"
					+ " order by al.nome ASC";			
			
			Query query = session.createQuery(hql, Aluno.class);
			query.setParameter("idEdital", idEdital);
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("ativo", BancoUtil.ATIVO);

			alunos = (List<Aluno>) query.getResultList();

		} catch (HibernateException hibernateException) {

			session.getTransaction().rollback();

			throw new SQLExceptionNutrIF(hibernateException);

		} finally {

			session.close();
		}

		return alunos;
	}

	@Override
	public List<Aluno> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Aluno.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Aluno.class;
	}

	@Override
	public Aluno find(Aluno entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
