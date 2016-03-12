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
import br.edu.ladoss.entity.Usuario;

public class UsuarioDAO extends GenericDao<Integer, Usuario> {

	private static Logger logger = LogManager.getLogger(UsuarioDAO.class);
	
	private static UsuarioDAO instance;
	
	public static UsuarioDAO getInstance() {		
		instance = new UsuarioDAO();		
		return instance;
	}

	public Usuario login(String nome, String senha) 
			throws UnsupportedEncodingException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Usuario usuario = null;
		
		try {
			
			String hql = "from Usuario as u"
					+ " where u.nome = :nome"
					+ " and u.senha = :senha";
			
			Query query = session.createQuery(hql);			
			query.setParameter("nome", nome);
			query.setParameter("senha", StringUtil.criptografarBase64(senha));
			
			usuario = (Usuario) query.uniqueResult();
	        
		} catch (HibernateException e) {
			
			logger.error(e.getMessage());
			session.getTransaction().rollback();
			throw e;
			
		} finally {
		
			session.close();
		}
		
		return usuario;
	}
	
	@Override
	public List<Usuario> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Usuario.getAll");
	}

	@Override
	public Class<?> getEntityClass() {		
		return Usuario.class;
	}
}
