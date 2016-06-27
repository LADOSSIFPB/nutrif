package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Login;

public class LoginDAO extends GenericDao<Integer, Login> {

	private static Logger logger = LogManager.getLogger(LoginDAO.class);
	
	private static LoginDAO instance;
	
	public static LoginDAO getInstance() {		
		instance = new LoginDAO();		
		return instance;
	}
	
	@Override
	public List<Login> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Login.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Login.class;
	}

	@Override
	public Login find(Login entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
