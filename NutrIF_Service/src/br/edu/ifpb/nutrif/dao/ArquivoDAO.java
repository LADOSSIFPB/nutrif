package br.edu.ifpb.nutrif.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;
import br.edu.ladoss.entity.Arquivo;

public class ArquivoDAO extends GenericDao<Integer, Arquivo> {

	private static Logger logger = LogManager.getLogger(ArquivoDAO.class);
	
	private static ArquivoDAO instance;
	
	public static ArquivoDAO getInstance() {		
		instance = new ArquivoDAO();		
		return instance;
	}
	
	@Override
	public List<Arquivo> getAll() throws SQLExceptionNutrIF {
		return super.getAll("Arquivo.getAll");
	}

	@Override
	public Class<?> getEntityClass() {
		return Arquivo.class;
	}

	@Override
	public Arquivo find(Arquivo entity) throws SQLExceptionNutrIF {
		// TODO Auto-generated method stub
		return null;
	}
}
