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
import br.edu.ifpb.nutrif.util.StringUtil;
import br.edu.ladoss.entity.Funcionario;

public class FuncionarioDAO extends GenericDao<Integer, Funcionario> {

	private static Logger logger = LogManager.getLogger(FuncionarioDAO.class);
	
	private static FuncionarioDAO instance;
	
	public static FuncionarioDAO getInstance() {		
		instance = new FuncionarioDAO();		
		return instance;
	}

	public Funcionario login(String nome, String senha) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Funcionario funcionario = null;
		
		try {
			
			String hql = "from Funcionario as f"
					+ " where f.nome = :nome"
					+ " and f.senha = :senha";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", nome);
			query.setParameter("senha", StringUtil.criptografarBase64(senha));
			
			funcionario = (Funcionario) query.uniqueResult();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			throw e;
			
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
}