package br.edu.ifpb.nutrif.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Funcionario;

public class FuncionarioDAO extends GenericDao<Integer, Funcionario> {

	private static Logger logger = LogManager.getLogger(FuncionarioDAO.class);
	
	private static FuncionarioDAO instance;
	
	public static FuncionarioDAO getInstance() {		
		instance = new FuncionarioDAO();		
		return instance;
	}
	
	
	public List<Funcionario> listByNome(String nome) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Funcionario> funcionarios = null;
		
		try {
			
			String hql = "from Funcionario as f"
					+ " where f.nome like :nome"
					+ " and f.ativo = :ativo";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			query.setParameter("ativo", BancoUtil.ATIVO);
			
			funcionarios = (List<Funcionario>) query.getResultList();
	        
		} catch (HibernateException hibernateException) {
			
			session.getTransaction().rollback();
			
			throw new SQLExceptionNutrIF(hibernateException);
			
		} finally {
		
			session.close();
		}
		
		return funcionarios;
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
