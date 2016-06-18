package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Aluno;
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
		
		List<Funcionario> funcionario = null;
		
		try {
			
			String hql = "from Funcionario as f"
					+ " where f.nome like :nome";
			
			Query query = session.createQuery(hql);
			query.setParameter("nome", "%" + nome + "%");
			
			funcionario = (List<Funcionario>) query.list();
	        
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
