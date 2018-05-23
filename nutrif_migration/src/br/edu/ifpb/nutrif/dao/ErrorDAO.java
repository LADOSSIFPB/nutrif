package br.edu.ifpb.nutrif.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ifpb.nutrif.util.BancoUtil;
import br.edu.ladoss.entity.Role;
import br.edu.ladoss.entity.Error;

public class ErrorDAO extends GenericDao<Integer, Error>{
	
	private static Logger logger = LogManager.getLogger(ErrorDAO.class);
	
	private static ErrorDAO instance;
	
	public ErrorDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static ErrorDAO getInstance() {
		instance = new ErrorDAO();
		return instance;
	}

	@Override
	public int insert(Error entity) throws SQLExceptionNutrIF {

		int id = BancoUtil.ID_VAZIO;
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactoryOld();

		try {
			
			if (sessionFactory != null && sessionFactory.isOpen()) {
				super.insert(entity);
			}
			
		} catch (SQLExceptionNutrIF hibernateException) {
			
			logger.error(hibernateException.getMessage());
		}
		
		return id;
	}
	
	@Override
	public List<Error> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Error.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Role.class;
	}

	@Override
	public Error find(Error entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
