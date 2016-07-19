package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Curso;
import br.edu.ladoss.entity.Edital;

public class EditalDAO extends GenericDao<Integer, Edital>{
	
	private static Logger logger = LogManager.getLogger(EditalDAO.class);
	
	private static EditalDAO instance;
	
	public static EditalDAO getInstance() {
		instance = new EditalDAO();
		return instance;
	}

	@Override
	public List<Edital> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Edital.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Curso.class;
	}

	@Override
	public Edital find(Edital entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
