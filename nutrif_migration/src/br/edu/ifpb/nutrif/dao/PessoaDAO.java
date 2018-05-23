package br.edu.ifpb.nutrif.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Pessoa;

public class PessoaDAO extends GenericDao<Integer, Pessoa> {

	private static Logger logger = LogManager.getLogger(PessoaDAO.class);
	
	private static PessoaDAO instance;
	
	public PessoaDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static PessoaDAO getInstance() {		
		instance = new PessoaDAO();		
		return instance;
	}

	@Override
	public List<Pessoa> getAll() throws SQLExceptionNutrIF {
		
		return super.getAll("Pessoa.getAll");
	}
	
	public Pessoa getByKeyAuth(String keyAuth) throws SQLExceptionNutrIF {

		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		Pessoa pessoa = null;
		
		try {
			
			String hql = "from Pessoa as p"
					+ " where p.keyAuth = :keyAuth"
					+ " and p.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("keyAuth", keyAuth);
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
	 * Login de Pessoa através do e-mail e senha.
	 * 
	 * @param email
	 * @param senhaPlana
	 * @return funcionario
	 * @throws UnsupportedEncodingException
	 */
	public Pessoa login(String email, String senhaPlana) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactoryOld().openSession();
		
		Pessoa pessoa = null;
		
		try {
			
			String senhaCriptografada = StringUtil.criptografarBase64(
					senhaPlana);
			
			logger.info("Verificando o login: " + email);
			
			String hql = "from Pessoa as p"
					+ " where p.email = :email"
					+ " and p.senha = :senha"
					+ " and p.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("email", email);
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
	
	@Override
	public Class<?> getEntityClass() {
		// TODO Auto-generated method stub
		return Pessoa.class;
	}

	@Override
	public Pessoa find(Pessoa entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
