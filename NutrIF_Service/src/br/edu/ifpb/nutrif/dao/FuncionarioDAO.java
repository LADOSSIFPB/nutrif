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
import br.edu.ladoss.entity.Funcionario;

public class FuncionarioDAO extends GenericDao<Integer, Funcionario> {

	private static Logger logger = LogManager.getLogger(FuncionarioDAO.class);
	
	private static FuncionarioDAO instance;
	
	public static FuncionarioDAO getInstance() {		
		instance = new FuncionarioDAO();		
		return instance;
	}

	/**
	 * Login do Funcionário através do e-mail e senha.
	 * 
	 * @param email
	 * @param senhaPlana
	 * @return funcionario
	 * @throws UnsupportedEncodingException
	 */
	public Funcionario login(String email, String senhaPlana) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Funcionario funcionario = null;
		
		try {
			
			String senhaCriptografada = StringUtil.criptografarBase64(senhaPlana);
			
			String hql = "from Funcionario as f"
					+ " where f.email = :email"
					+ " and f.senha = :senha"
					+ " and f.ativo = :ativo";
			
			Query query = session.createQuery(hql);			
			query.setParameter("email", email);
			query.setParameter("senha", senhaCriptografada);
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			funcionario = (Funcionario) query.uniqueResult();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return funcionario;
	}
	
	@Override
	public List<Funcionario> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Funcionario.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Funcionario.class;
	}

	@Override
	public Funcionario find(Funcionario entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
