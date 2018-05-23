package br.edu.ifpb.nutrif.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Aluno;
import br.edu.ladoss.entity.DiaRefeicao;
import br.edu.ladoss.entity.Pessoa;

public class AlunoDAO extends GenericDao<Integer, Aluno> {

	private static Logger logger = LogManager.getLogger(AlunoDAO.class);
	
	private static AlunoDAO instance;
	
	public AlunoDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static AlunoDAO getInstance() {		
		instance = new AlunoDAO();		
		return instance;
	}

	public List<Aluno> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		List<Aluno> alunos = null;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			alunos = (List<Aluno>) query.list();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return alunos;
	}
	
	public Aluno getByMatricula(String matricula) {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		Aluno aluno = null;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			
			aluno = (Aluno) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return aluno;
	}
	
	public boolean isKeyConfirmation(String matricula, String keyConfirmation) {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		boolean isKeyConfirmatio = false;
		
		try {
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula"
					+ " and a.keyConfirmation = :keyConfirmation";
			
			Query query = session.createQuery(hql);
			query.setParameter("matricula", matricula);
			query.setParameter("keyConfirmation", keyConfirmation);			
			
			Aluno aluno = (Aluno) query.uniqueResult();
			
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
	 * Login do Aluno através da matrícula e senha.
	 * 
	 * @param matricula
	 * @param senhaPlana
	 * @return funcionario
	 * @throws UnsupportedEncodingException
	 */
	public Pessoa login(String matricula, String senhaPlana) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		Pessoa pessoa = null;
		
		try {
			
			String senhaCriptografada = StringUtil.criptografarBase64(
					senhaPlana);
			
			logger.info("Verificando o login: " + matricula);
			
			String hql = "from Aluno as a"
					+ " where a.matricula = :matricula"
					+ " and a.senha = :senha"
					+ " and a.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("matricula", matricula);
			query.setParameter("senha", senhaCriptografada);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			pessoa = (Pessoa) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return pessoa;
	}
	
	/**
	 * Listar Alunos Contemplados num determinado Edital.
	 * 
	 * @param idEdital
	 * @return
	 */
	public List<Aluno> listAlunoByEdital(Integer idEdital) {

		Session session = HibernateUtil.getSessionFactoryOld().openSession();

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

			Query query = session.createQuery(hql);
			query.setParameter("idEdital", idEdital);
			query.setParameter("ativo", BancoUtil.ATIVO);

			alunos = (List<Aluno>) query.list();

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

		Session session = HibernateUtil.getSessionFactoryOld().openSession();

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
			
			Query query = session.createQuery(hql);
			query.setParameter("idEdital", idEdital);
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("ativo", BancoUtil.ATIVO);

			alunos = (List<Aluno>) query.list();

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
