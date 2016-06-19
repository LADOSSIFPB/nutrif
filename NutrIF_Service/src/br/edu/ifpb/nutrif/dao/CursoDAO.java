package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Curso;

public class CursoDAO extends GenericDao<Integer, Curso>{
	
	private static Logger logger = LogManager.getLogger(CursoDAO.class);
	
	private static CursoDAO instance;
	
	public static CursoDAO getInstance() {
		instance = new CursoDAO();
		return instance;
	}

	@Override
	public List<Curso> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Curso.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Curso.class;
	}

	@Override
	public Curso find(Curso entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
