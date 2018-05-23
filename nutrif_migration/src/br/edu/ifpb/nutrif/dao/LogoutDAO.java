package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ifpb.nutrif.hibernate.HibernateUtil;
import br.edu.ladoss.entity.Login;
import br.edu.ladoss.entity.Logout;

public class LogoutDAO extends GenericDao<Integer, Logout> {

	private static Logger logger = LogManager.getLogger(LogoutDAO.class);
	
	private static LogoutDAO instance;
	
	public LogoutDAO() {
		super(HibernateUtil.getSessionFactoryOld());
	}
	
	public static LogoutDAO getInstance() {		
		instance = new LogoutDAO();		
		return instance;
	}
	
	@Override
	public List<Logout> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Logout.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Login.class;
	}

	@Override
	public Logout find(Logout entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
